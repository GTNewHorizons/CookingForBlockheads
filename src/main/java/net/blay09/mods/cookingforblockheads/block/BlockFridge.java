package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.GuiHandler;
import net.blay09.mods.cookingforblockheads.client.render.block.FridgeBlockRenderer;
import net.blay09.mods.cookingforblockheads.item.ItemBlockFridge;
import net.blay09.mods.cookingforblockheads.tile.TileFridge;
import net.blay09.mods.cookingforblockheads.utils.DyeUtils;
import net.minecraft.block.Block;
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

import java.util.Optional;

public class BlockFridge extends BlockBaseKitchen {

    public BlockFridge() {
        super(Material.iron);

        setBlockName("cookingforblockheads:fridge");
        setStepSound(soundTypeMetal);
        setHardness(5f);
        setResistance(10f);
        setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.975f, 0.9375f);
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return Blocks.iron_block.getIcon(side, 0);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
    }

    protected void findOrientation(World world, int x, int y, int z) {
        if (!world.isRemote) {
            Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte side = 3;
            if (block.isOpaqueCube() && !block1.isOpaqueCube()) {
                side = 3;
            }
            if (block1.isOpaqueCube() && !block.isOpaqueCube()) {
                side = 2;
            }
            if (block2.isOpaqueCube() && !block3.isOpaqueCube()) {
                side = 5;
            }
            if (block3.isOpaqueCube() && !block2.isOpaqueCube()) {
                side = 4;
            }
            world.setBlockMetadataWithNotify(x, y, z, side, 2);
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType() {
        return FridgeBlockRenderer.RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileFridge();
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        TileFridge fridge = (TileFridge) world.getTileEntity(x, y, z);
        if (fridge.getColor() == colour) {
            return false;
        }
        fridge.setColor(colour);
        if(fridge.findNeighbourFridge() != null) {
            fridge.findNeighbourFridge().setColor(colour);
        }
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem();

        if(heldItem != null && DyeUtils.isDye(heldItem)) {
            Optional<Integer> dyeColor = DyeUtils.colorFromStack(heldItem);
            if (dyeColor.isPresent() && recolourBlock(world, x, y, z, ForgeDirection.UNKNOWN, dyeColor.get())) {
                player.getHeldItem().stackSize--;
                return true;
            }
        }
        if(!world.isRemote) {
            if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBlockFridge) {
                return false;
            }
            player.openGui(CookingForBlockheads.instance, GuiHandler.FRIDGE, world, x, y, z);
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        boolean below = world.getBlock(x, y - 1, z) == CookingForBlockheads.blockFridge;
        boolean above = world.getBlock(x, y + 1, z) == CookingForBlockheads.blockFridge;
        if(below && above) {
            return false;
        }
        if(below && world.getBlock(x, y - 2, z) == CookingForBlockheads.blockFridge) {
            return false;
        }
        if(above && world.getBlock(x, y + 2, z) == CookingForBlockheads.blockFridge) {
            return false;
        }
        return super.canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        findOrientation(world, x, y, z);
    }
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemStack) {
        double blockRotation = (double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D;
        boolean flipped = Math.abs(blockRotation - (int) blockRotation) < 0.5;
        int orientation = MathHelper.floor_double(blockRotation) & 3;
        if (orientation == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (orientation == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (orientation == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (orientation == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
        TileFridge tileEntity = (TileFridge) world.getTileEntity(x, y, z);
        tileEntity.setFlipped(flipped);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileFridge tileFridge = (TileFridge) world.getTileEntity(x, y, z);
        if(tileFridge != null) {
            tileFridge.breakBlock();
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        if(block == CookingForBlockheads.blockFridge) {
            ((TileFridge) world.getTileEntity(x, y, z)).updateMultiblock();
        }
    }
}
