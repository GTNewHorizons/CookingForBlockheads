package net.blay09.mods.cookingforblockheads.registry.food.recipe;

import net.blay09.mods.cookingforblockheads.registry.food.FoodIngredient;
import net.blay09.mods.cookingforblockheads.registry.food.FoodRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ShapedOreCraftingFood extends FoodRecipe {

    public ShapedOreCraftingFood(ShapedOreRecipe recipe) {
        this.outputItem = recipe.getRecipeOutput();
        try {
            Field widthField = ShapedOreRecipe.class.getDeclaredField("width");
            widthField.setAccessible(true);
            this.recipeWidth = (int) widthField.get(recipe);
            Field heightField = ShapedOreRecipe.class.getDeclaredField("height");
            heightField.setAccessible(true);
            this.recipeHeight = (int) heightField.get(recipe);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        this.craftMatrix = new ArrayList<>();

        for(int i = 0; i < recipe.getInput().length; i++) {
            Object input = recipe.getInput()[i];
            if (input == null) {
                craftMatrix.add(null);
                continue;
            }

            if(input instanceof ItemStack) {
                craftMatrix.add(new FoodIngredient((ItemStack) input, false));
            } else if(input instanceof List) {
                craftMatrix.add(new FoodIngredient(((List<ItemStack>) input).toArray(new ItemStack[((List<ItemStack>) input).size()]), false));
            }
        }
    }

}
