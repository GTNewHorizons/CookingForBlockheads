package net.blay09.mods.cookingforblockheads.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileCabinetCorner extends BaseKitchenTileWithInventory {

    protected static final String name = "cabinetcorner";

    public TileCabinetCorner() {
        super(name);
    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        color = tagCompound.getByte("Color");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setByte("Color", (byte) color);
    }
}
