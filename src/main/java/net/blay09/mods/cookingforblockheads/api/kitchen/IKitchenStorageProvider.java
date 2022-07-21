package net.blay09.mods.cookingforblockheads.api.kitchen;

import net.minecraft.inventory.IInventory;

public interface IKitchenStorageProvider extends IMultiblockKitchen {

    IInventory getInventory();
}
