package net.blay09.mods.cookingforblockheads.api.kitchen;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IKitchenItemProvider extends IMultiblockKitchen {

    List<ItemStack> getProvidedItemStacks();
    boolean addToCraftingBuffer(ItemStack itemStack);
    void clearCraftingBuffer();
    void craftingComplete();

}
