package net.blay09.mods.cookingforblockheads.tile;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.container.inventory.InventoryLarge;
import net.minecraft.util.AxisAlignedBB;

public class TileFridge extends BaseKitchenTileWithInventory {

    public TileFridge() {
        super("fridge");
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();

        if (tickCounter == 1) {
            updateMultiblock();
        }
    }


    @Override
    public boolean receiveClientEvent(int id, int value) {
        return super.receiveClientEvent(id, value);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
    }

    public TileFridge findNeighbourFridge() {
        if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) == CookingForBlockheads.blockFridge) {
            return (TileFridge) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        } else if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == CookingForBlockheads.blockFridge) {
            return (TileFridge) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        }
        return null;
    }

    public void updateMultiblock() {
        TileFridge bottomFridge;
        TileFridge upperFridge;
        if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) == CookingForBlockheads.blockFridge) {
            bottomFridge = this;
            upperFridge = (TileFridge) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
            this.setColor(upperFridge.getColor());
        } else if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == CookingForBlockheads.blockFridge) {
            bottomFridge = (TileFridge) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            upperFridge = this;
            this.setColor(bottomFridge.getColor());
        } else {
            sharedInventory = internalInventory;
            return;
        }
        sharedInventory = new InventoryLarge(bottomFridge.getInternalInventory(), upperFridge.getInternalInventory());
    }

}