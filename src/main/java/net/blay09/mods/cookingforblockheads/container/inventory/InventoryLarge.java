package net.blay09.mods.cookingforblockheads.container.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryLarge implements IInventory {

    private final IInventory lowerInventory;
    private final IInventory upperInventory;

    public InventoryLarge(IInventory lowerInventory, IInventory upperInventory) {
        this.lowerInventory = lowerInventory;
        this.upperInventory = upperInventory;
    }

    @Override
    public int getSizeInventory() {
        return lowerInventory.getSizeInventory() + upperInventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (i < lowerInventory.getSizeInventory()) {
            return lowerInventory.getStackInSlot(i);
        } else {
            return upperInventory.getStackInSlot(i - lowerInventory.getSizeInventory());
        }
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
        if (i < lowerInventory.getSizeInventory()) {
            return lowerInventory.decrStackSize(i, amount);
        } else {
            return upperInventory.decrStackSize(i - lowerInventory.getSizeInventory(), amount);
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (i < lowerInventory.getSizeInventory()) {
            return lowerInventory.getStackInSlotOnClosing(i);
        } else {
            return upperInventory.getStackInSlotOnClosing(i - lowerInventory.getSizeInventory());
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        if (i < lowerInventory.getSizeInventory()) {
            lowerInventory.setInventorySlotContents(i, itemStack);
        } else {
            upperInventory.setInventorySlotContents(i - lowerInventory.getSizeInventory(), itemStack);
        }
    }

    @Override
    public String getInventoryName() {
        return lowerInventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        lowerInventory.markDirty();
        upperInventory.markDirty();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return lowerInventory.isUseableByPlayer(player) && upperInventory.isUseableByPlayer(player);
    }

    @Override
    public void openInventory() {
        lowerInventory.openInventory();
        upperInventory.openInventory();
    }

    @Override
    public void closeInventory() {
        lowerInventory.closeInventory();
        upperInventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return lowerInventory.isItemValidForSlot(i, itemStack) && upperInventory.isItemValidForSlot(i, itemStack);
    }

    public boolean containsInventory(IInventory inventory) {
        return lowerInventory == inventory || upperInventory == inventory;
    }
}
