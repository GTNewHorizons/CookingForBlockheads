package net.blay09.mods.cookingforblockheads.block;

import java.util.Optional;
import net.blay09.mods.cookingforblockheads.client.render.block.CounterCornerBlockRenderer;
import net.blay09.mods.cookingforblockheads.tile.TileCounterCorner;
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

public class BlockCounterCorner extends BlockBaseKitchen {

    public BlockCounterCorner() {
        super(Material.iron);
        setBlockName("cookingforblockheads:counter_corner");
        setStepSound(soundTypeStone);
        setHardness(5f);
        setResistance(10f);
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return Blocks.stone.getIcon(side, 0);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemStack) {
        double blockRotation = (double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D;
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
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {}

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }

    @Override
    public int getRenderType() {
        return CounterCornerBlockRenderer.RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCounterCorner();
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        TileCounterCorner corner = (TileCounterCorner) world.getTileEntity(x, y, z);
        if (corner.getColor() == colour) {
            return false;
        }
        corner.setColor(colour);
        return true;
    }

    @Override
    public boolean onBlockActivated(
            World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem();

        if (heldItem != null && DyeUtils.isDye(heldItem)) {
            Optional<Integer> dyeColor = DyeUtils.colorFromStack(heldItem);
            if (dyeColor.isPresent() && recolourBlock(world, x, y, z, ForgeDirection.UNKNOWN, dyeColor.get())) {
                player.getHeldItem().stackSize--;
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        super.breakBlock(world, x, y, z, block, metadata);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
    }
}
