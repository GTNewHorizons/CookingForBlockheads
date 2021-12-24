package net.blay09.mods.cookingforblockheads.registry.food.recipe;

import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.registry.food.FoodIngredient;
import net.blay09.mods.cookingforblockheads.registry.food.FoodRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.ArrayList;

public class ShapedCraftingFood extends FoodRecipe {

    public ShapedCraftingFood(ShapedRecipes recipe) {
        this.outputItem = recipe.getRecipeOutput();
        this.recipeWidth = recipe.recipeWidth;
        this.recipeHeight = recipe.recipeHeight;
        this.craftMatrix = new ArrayList<>();

        for (ItemStack itemStack : recipe.recipeItems) {
            if(itemStack != null) {
                boolean isToolItem = CookingRegistry.isToolItem(itemStack);
                craftMatrix.add(new FoodIngredient(itemStack.copy(), isToolItem));
            } else {
                craftMatrix.add(null);
            }
        }
    }

}
