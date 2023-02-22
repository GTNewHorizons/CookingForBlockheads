package net.blay09.mods.cookingforblockheads.tile;

public class TileCounter extends BaseKitchenTileWithInventory {

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
}
