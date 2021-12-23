package net.blay09.mods.cookingforblockheads.api.kitchen;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IKitchenMultiBlock {
    List<IKitchenItemProvider> getItemProviders(InventoryPlayer playerInventory);
    ItemStack smeltItem(ItemStack itemStack, int count);
    boolean hasSmeltingProvider();
}
