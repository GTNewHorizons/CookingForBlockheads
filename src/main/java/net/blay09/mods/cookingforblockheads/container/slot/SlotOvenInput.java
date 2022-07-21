package net.blay09.mods.cookingforblockheads.container.slot;

import net.blay09.mods.cookingforblockheads.tile.TileOven;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import squeek.applecore.api.AppleCoreAPI;

public class SlotOvenInput extends Slot {

    public SlotOvenInput(IInventory inventory, int i, int x, int y) {
        super(inventory, i, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        ItemStack smeltingResult = TileOven.getSmeltingResult(stack);
        return smeltingResult != null && AppleCoreAPI.accessor.isFood(smeltingResult);
    }
}
