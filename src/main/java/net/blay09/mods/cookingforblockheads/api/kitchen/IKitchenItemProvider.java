package net.blay09.mods.cookingforblockheads.api.kitchen;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IKitchenItemProvider extends IMultiblockKitchen {

    List<ItemStack> getProvidedItemStacks();

    boolean addToCraftingBuffer(ItemStack itemStack);

    void clearCraftingBuffer();

    void craftingComplete();
}
