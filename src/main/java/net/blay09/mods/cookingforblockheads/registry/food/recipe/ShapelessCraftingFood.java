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
        for(int i = 0; i < recipe.recipeItems.size(); i++) {
            if (recipe.recipeItems.get(i) != null) {
                boolean isToolItem = CookingRegistry.isToolItem((ItemStack) recipe.recipeItems.get(i));
                craftMatrix.add(new FoodIngredient(((ItemStack) recipe.recipeItems.get(i)).copy(), isToolItem));
            }
        }
    }

}
