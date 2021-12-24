package net.blay09.mods.cookingforblockheads.registry.food.recipe;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.blay09.mods.cookingforblockheads.registry.food.FoodRecipe;
import net.blay09.mods.cookingforblockheads.registry.food.FoodIngredient;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RemoteCraftingFood extends FoodRecipe {

    private final boolean isSmeltingRecipe;

    public RemoteCraftingFood(ItemStack outputItem, int recipeWidth, int recipeHeight, List<FoodIngredient> craftMatrix, boolean isSmeltingRecipe) {
        this.outputItem = outputItem;
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.craftMatrix = craftMatrix;
        this.isSmeltingRecipe = isSmeltingRecipe;
    }

    @Override
    public boolean isSmeltingRecipe() {
        return isSmeltingRecipe;
    }

    public static RemoteCraftingFood read(ByteBuf buf) {
        ItemStack outputItem = ByteBufUtils.readItemStack(buf);
        int recipeWidth = buf.readByte();
        int recipeHeight = buf.readByte();
        int ingredientCount = buf.readShort();
        List<FoodIngredient> craftMatrix = new ArrayList<>(ingredientCount);
        for(int k = 0; k < ingredientCount; k++) {
            int stackCount = buf.readShort();
            if (stackCount > 0) {
                ItemStack[] itemStacks = new ItemStack[stackCount];
                for (int l = 0; l < stackCount; l++) {
                    itemStacks[l] = ByteBufUtils.readItemStack(buf);
                }
                boolean isOptional = buf.readBoolean();
                craftMatrix.add(new FoodIngredient(itemStacks, isOptional));
            } else {
                craftMatrix.add(null);
            }
        }
        boolean isSmeltingRecipe = buf.readBoolean();
        return new RemoteCraftingFood(outputItem, recipeWidth, recipeHeight, craftMatrix, isSmeltingRecipe);
    }

    public static void write(ByteBuf buf, FoodRecipe recipe) {
        ByteBufUtils.writeItemStack(buf, recipe.getOutputItem());
        buf.writeByte(recipe.getRecipeWidth());
        buf.writeByte(recipe.getRecipeHeight());
        buf.writeShort(recipe.getCraftMatrix().size());
        for(FoodIngredient ingredient : recipe.getCraftMatrix()) {
            if (ingredient != null) {
                buf.writeShort(ingredient.getItemStacks().length);
                for (ItemStack ingredientStack : ingredient.getItemStacks()) {
                    ByteBufUtils.writeItemStack(buf, ingredientStack);
                }
                buf.writeBoolean(ingredient.isToolItem());
            } else {
                buf.writeShort(0);
            }
        }
        buf.writeBoolean(recipe.isSmeltingRecipe());
    }
}
