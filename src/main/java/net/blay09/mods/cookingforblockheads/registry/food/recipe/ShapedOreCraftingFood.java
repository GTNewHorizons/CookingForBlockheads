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

        for(Object obj : recipe.getInput()) {
            if (obj == null) {
                craftMatrix.add(null);
                continue;
            }

            if (obj instanceof ItemStack) {
                craftMatrix.add(new FoodIngredient((ItemStack) obj, false));
            } else if (obj instanceof List) {
                // TODO: cast warning
                List<ItemStack> inputList = (List<ItemStack>) obj;
                craftMatrix.add(new FoodIngredient(inputList.toArray(new ItemStack[inputList.size()]), false));
            }
        }
    }

}
