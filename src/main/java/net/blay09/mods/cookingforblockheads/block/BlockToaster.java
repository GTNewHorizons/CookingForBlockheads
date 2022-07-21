package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.client.render.block.ToasterBlockRenderer;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.tile.TileToaster;
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

public class BlockToaster extends BlockBaseKitchen {

    public BlockToaster() {
        super(Material.iron);

        setBlockName(CookingForBlockheads.MOD_ID + ":toaster");
        setStepSound(soundTypeWood);
        setHardness(5f);
        setResistance(10f);
        setBlockBounds(0.275f, 0f, 0.275f, 0.725f, 0.4f, 0.725f);
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        findOrientation(worldIn, x, y, z);
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
        return ToasterBlockRenderer.RENDER_ID;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {}

    @Override
    public IIcon getIcon(int side, int metadata) {
        return Blocks.iron_block.getIcon(side, 1);
    }

    @Override
    public boolean onBlockActivated(
            World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileToaster tileEntity = (TileToaster) world.getTileEntity(x, y, z);
        ItemStack heldItem = player.getHeldItem();
        if (heldItem == null) {
            //            if(!tileEntity.isActive()) {
            tileEntity.setActive(!tileEntity.isActive());
            //            }
        } else {
            ItemStack output = CookingRegistry.getToastOutput(heldItem);
            if (output != null) {
                for (int i = 0; i < tileEntity.getSizeInventory(); i++) {
                    if (tileEntity.getStackInSlot(i) == null) {
                        tileEntity.setInventorySlotContents(i, heldItem.splitStack(1));
                        return false;
                    }
                }
                return false;
            }
        }
        return false;
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
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileToaster();
    }
}
