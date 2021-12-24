package net.blay09.mods.cookingforblockheads.registry.food.recipe;

import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.registry.food.FoodIngredient;
import net.blay09.mods.cookingforblockheads.registry.food.FoodRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.ArrayList;

public class ShapelessCraftingFood extends FoodRecipe {

    public ShapelessCraftingFood(ShapelessRecipes recipe) {
        this.outputItem = recipe.getRecipeOutput();
        this.recipeWidth = Math.min(3, recipe.getRecipeSize());
        this.recipeHeight = (int) Math.ceil((double) recipe.getRecipeSize() / 3);
        this.craftMatrix = new ArrayList<>();

        for (Object obj : recipe.recipeItems) {
            if (obj != null) {
                ItemStack itemStack = (ItemStack) obj;
                boolean isToolItem = CookingRegistry.isToolItem(itemStack);
                craftMatrix.add(new FoodIngredient(itemStack.copy(), isToolItem));
            }
        }
    }

}
