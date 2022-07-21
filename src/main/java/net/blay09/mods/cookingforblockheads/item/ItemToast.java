package net.blay09.mods.cookingforblockheads.item;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.minecraft.item.ItemFood;

public class ItemToast extends ItemFood {

    public ItemToast() {
        super(7, 1.2f, false);
        setUnlocalizedName(CookingForBlockheads.MOD_ID + ":toast");
        setTextureName(CookingForBlockheads.MOD_ID + ":toast");
        setCreativeTab(CookingForBlockheads.creativeTab);
    }
}
