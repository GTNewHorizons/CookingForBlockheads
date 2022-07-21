package net.blay09.mods.cookingforblockheads.tile;

import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.minecraft.inventory.IInventory;

public class TileCounter extends BaseKitchenTileWithInventory implements IInventory, IKitchenStorageProvider {
    public static String getName() {
        return "counter";
    }

    public TileCounter() {
        super(getName());
    }

    public TileCounter(String name) {
        super(name);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (tickCounter == 1) {
            sharedInventory = internalInventory;
        }
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        return super.receiveClientEvent(id, value);
    }
}
