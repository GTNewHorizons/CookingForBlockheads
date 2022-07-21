package net.blay09.mods.cookingforblockheads.tile;

import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.minecraft.inventory.IInventory;

public class TileCabinet extends TileCounter implements IInventory, IKitchenStorageProvider {
    public static String getName() {
        return "cabinet";
    }

    public TileCabinet() {
        super(getName());
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        return super.receiveClientEvent(id, value);
    }
}
