package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.client.render.block.CabinetCornerBlockRenderer;
import net.blay09.mods.cookingforblockheads.tile.TileCabinetCorner;
import net.blay09.mods.cookingforblockheads.tile.TileCounterCorner;
import net.blay09.mods.cookingforblockheads.utils.DyeUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Optional;

public class BlockCabinetCorner extends BlockCounterCorner {

    public BlockCabinetCorner() {
        setBlockName("cookingforblockheads:cabinet_corner");
        setStepSound(soundTypeStone);
        setHardness(5f);
        setResistance(10f);
    }

    @Override
    public int getRenderType() {
        return CabinetCornerBlockRenderer.RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCabinetCorner();
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        TileCabinetCorner corner = (TileCabinetCorner) world.getTileEntity(x, y, z);
        if (corner.getColor() == colour) {
            return false;
        }
        corner.setColor(colour);
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
        return true;
    }

}
