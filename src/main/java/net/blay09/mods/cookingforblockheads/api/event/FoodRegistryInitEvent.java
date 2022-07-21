package net.blay09.mods.cookingforblockheads.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.item.ItemStack;

public class FoodRegistryInitEvent extends Event {

    private final List<ItemStack> nonFoodRecipes = new ArrayList<>();

    public void registerNonFoodRecipe(ItemStack result) {
        nonFoodRecipes.add(result);
    }

    public Collection<ItemStack> getNonFoodRecipes() {
        return nonFoodRecipes;
    }
}
