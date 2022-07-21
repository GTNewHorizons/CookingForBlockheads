package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.client.render.block.CabinetBlockRenderer;
import net.blay09.mods.cookingforblockheads.tile.TileCabinet;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCabinet extends BlockCounter {

    public BlockCabinet() {
        super();
        setBlockName("cookingforblockheads:cabinet");
        setStepSound(soundTypeStone);
        setHardness(5f);
        setResistance(10f);
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return Blocks.stone.getIcon(side, 0);
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
        return CabinetBlockRenderer.RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCabinet();
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        TileCabinet cabinet = (TileCabinet) world.getTileEntity(x, y, z);
        if (cabinet.getColor() == colour) {
            return false;
        }
        cabinet.setColor(colour);
        return true;
    }
}
