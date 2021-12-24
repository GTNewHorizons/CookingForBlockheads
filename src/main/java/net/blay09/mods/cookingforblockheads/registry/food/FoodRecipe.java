package net.blay09.mods.cookingforblockheads.registry.food;

import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class FoodRecipe {

    protected List<FoodIngredient> craftMatrix;
    protected ItemStack outputItem;
    protected int recipeWidth = 3;
    protected int recipeHeight = 3;

    public List<FoodIngredient> getCraftMatrix() {
        return craftMatrix;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public boolean isSmeltingRecipe() {
        return false;
    }

    public int getRecipeWidth() {
        return recipeWidth;
    }

    public int getRecipeHeight() {
        return recipeHeight;
    }

}