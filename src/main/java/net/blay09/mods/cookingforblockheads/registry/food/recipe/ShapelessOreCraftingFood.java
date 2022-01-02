package net.blay09.mods.cookingforblockheads.registry.food.recipe;

import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.registry.food.FoodIngredient;
import net.blay09.mods.cookingforblockheads.registry.food.FoodRecipe;
import net.blay09.mods.cookingforblockheads.utils.MiscUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class ShapelessOreCraftingFood extends FoodRecipe {

    public ShapelessOreCraftingFood(ShapelessOreRecipe recipe) {
        this.outputItem = recipe.getRecipeOutput();
        this.recipeWidth = Math.min(3, recipe.getRecipeSize());
        this.recipeHeight = (int) Math.ceil((double) recipe.getRecipeSize() / 3);
        this.craftMatrix = new ArrayList<>();

        for (Object obj : recipe.getInput()) {
            if (obj == null) {
                continue;
            }

            if (obj instanceof ItemStack) {
                ItemStack itemStack = (ItemStack) obj;
                boolean isToolItem = CookingRegistry.isToolItem(itemStack);
                craftMatrix.add(new FoodIngredient(itemStack, isToolItem));
            } else if (obj instanceof ArrayList) {
                List<ItemStack> inputList = MiscUtils.castList((List<?>) obj, ItemStack.class);
                boolean toolFound = false;
                for (ItemStack itemStack : inputList) {
                    if (CookingRegistry.isToolItem(itemStack)) {
                        toolFound = true;
                    }
                }
                craftMatrix.add(new FoodIngredient(inputList.toArray(new ItemStack[0]), toolFound));
            }
        }
    }

}
