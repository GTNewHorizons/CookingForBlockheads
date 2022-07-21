package net.blay09.mods.cookingforblockheads.block;

import java.util.Optional;
import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.GuiHandler;
import net.blay09.mods.cookingforblockheads.client.render.block.CookingTableBlockRenderer;
import net.blay09.mods.cookingforblockheads.tile.TileCookingTable;
import net.blay09.mods.cookingforblockheads.utils.DyeUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCookingTable extends BlockBaseKitchen {

    public BlockCookingTable() {
        super(Material.wood);

        setBlockName("cookingforblockheads:cookingtable");
        setBlockTextureName("cookingforblockheads:cooking_table_side");
        setStepSound(soundTypeWood);
        setHardness(2.5f);
        setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.975f, 0.9375f);
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        findOrientation(worldIn, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemStack) {
        int l = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return CookingTableBlockRenderer.RENDER_ID;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {}

    @Override
    public IIcon getIcon(int side, int metadata) {
        return Blocks.log.getIcon(side, 1);
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        TileCookingTable table = (TileCookingTable) world.getTileEntity(x, y, z);
        if (table.getColor() == colour) {
            return false;
        }
        table.setColor(colour);
        return true;
    }

    @Override
    public boolean onBlockActivated(
            World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        TileCookingTable table = (TileCookingTable) world.getTileEntity(x, y, z);

        if (heldItem != null && DyeUtils.isDye(heldItem)) {
            Optional<Integer> dyeColor = DyeUtils.colorFromStack(heldItem);
            if (dyeColor.isPresent() && recolourBlock(world, x, y, z, ForgeDirection.UNKNOWN, dyeColor.get())) {
                player.getHeldItem().stackSize--;
                return true;
            }
        }

        if (heldItem != null) {
            if (!table.hasNoFilterBook()
                    && heldItem.getItem() == CookingForBlockheads.itemRecipeBook
                    && heldItem.getItemDamage() == 3) {
                table.setNoFilterBook(heldItem.splitStack(1));
                return true;
            }
        } else if (player.isSneaking()) {
            ItemStack noFilterBook = table.getNoFilterBook();
            if (noFilterBook != null) {
                if (!player.inventory.addItemStackToInventory(noFilterBook)) {
                    player.dropPlayerItemWithRandomChoice(noFilterBook, false);
                }
                table.setNoFilterBook(null);
                return true;
            }
        }
        if (!world.isRemote) {
            player.openGui(CookingForBlockheads.instance, GuiHandler.COOKING_TABLE, world, x, y, z);
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCookingTable();
    }
}
