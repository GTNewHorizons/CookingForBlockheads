package net.blay09.mods.cookingforblockheads.tile;

import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public class TileCounterCorner extends BaseKitchenTileWithInventory implements IInventory, IKitchenStorageProvider {
    protected static final String name = "countercorner";

    public TileCounterCorner() {
        super(name);
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

    @Override
    public boolean receiveClientEvent(int id, int value) {
        return super.receiveClientEvent(id, value);
    }
}
