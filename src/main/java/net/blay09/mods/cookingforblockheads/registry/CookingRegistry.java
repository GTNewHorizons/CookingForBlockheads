package net.blay09.mods.cookingforblockheads.registry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.blay09.mods.cookingforblockheads.api.SinkHandler;
import net.blay09.mods.cookingforblockheads.api.ToastHandler;
import net.blay09.mods.cookingforblockheads.api.event.FoodRegistryInitEvent;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenItemProvider;
import net.blay09.mods.cookingforblockheads.compat.HarvestCraftAddon;
import net.blay09.mods.cookingforblockheads.container.inventory.InventoryCraftBook;
import net.blay09.mods.cookingforblockheads.registry.food.FoodIngredient;
import net.blay09.mods.cookingforblockheads.registry.food.FoodRecipe;
import net.blay09.mods.cookingforblockheads.registry.food.recipe.ShapedCraftingFood;
import net.blay09.mods.cookingforblockheads.registry.food.recipe.ShapedOreCraftingFood;
import net.blay09.mods.cookingforblockheads.registry.food.recipe.ShapelessCraftingFood;
import net.blay09.mods.cookingforblockheads.registry.food.recipe.ShapelessOreCraftingFood;
import net.blay09.mods.cookingforblockheads.registry.food.recipe.SmeltingFood;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import squeek.applecore.api.AppleCoreAPI;

public class CookingRegistry {

    private static final List<IRecipe> recipeList = Lists.newArrayList();
    private static final ArrayListMultimap<Item, FoodRecipe> foodItems = ArrayListMultimap.create();
    private static final List<ItemStack> tools = Lists.newArrayList();
    private static final Map<ItemStack, Integer> ovenFuelItems = Maps.newHashMap();
    private static final Map<ItemStack, ItemStack> ovenRecipes = Maps.newHashMap();
    private static final Map<ItemStack, SinkHandler> sinkHandlers = Maps.newHashMap();
    private static final Map<ItemStack, ToastHandler> toastHandlers = Maps.newHashMap();

    public static void initFoodRegistry() {
        recipeList.clear();
        foodItems.clear();

        FoodRegistryInitEvent init = new FoodRegistryInitEvent();
        MinecraftForge.EVENT_BUS.post(init);

        Collection<ItemStack> nonFoodRecipes = init.getNonFoodRecipes();

        // Crafting Recipes of Food Items
        for (Object obj : CraftingManager.getInstance().getRecipeList()) {
            IRecipe recipe = (IRecipe) obj;
            ItemStack output = recipe.getRecipeOutput();
            if (output != null) {
                if (AppleCoreAPI.accessor.isFood(output)) {
                    if (HarvestCraftAddon.isWeirdBrokenRecipe(recipe)) {
                        continue;
                    }
                    addFoodRecipe(recipe);
                } else {
                    for (ItemStack itemStack : nonFoodRecipes) {
                        if (areItemStacksEqualWithWildcard(recipe.getRecipeOutput(), itemStack)) {
                            addFoodRecipe(recipe);
                            break;
                        }
                    }
                }
            }
        }

        // Smelting Recipes of Food Items
        for (Object obj : FurnaceRecipes.smelting().getSmeltingList().entrySet()) {
            Map.Entry entry = (Map.Entry) obj;
            ItemStack sourceStack = null;
            if (entry.getKey() instanceof Item) {
                sourceStack = new ItemStack((Item) entry.getKey());
            } else if (entry.getKey() instanceof Block) {
                sourceStack = new ItemStack((Block) entry.getKey());
            } else if (entry.getKey() instanceof ItemStack) {
                sourceStack = (ItemStack) entry.getKey();
            }
            ItemStack resultStack = (ItemStack) entry.getValue();
            if (AppleCoreAPI.accessor.isFood(resultStack)) {
                foodItems.put(resultStack.getItem(), new SmeltingFood(resultStack, sourceStack));
            } else {
                for (ItemStack itemStack : nonFoodRecipes) {
                    if (areItemStacksEqualWithWildcard(resultStack, itemStack)) {
                        foodItems.put(resultStack.getItem(), new SmeltingFood(resultStack, sourceStack));
                        break;
                    }
                }
            }
        }
    }

    public static void addFoodRecipe(IRecipe recipe) {
        ItemStack output = recipe.getRecipeOutput();
        if (output != null) {
            recipeList.add(recipe);
            if (recipe instanceof ShapedRecipes) {
                foodItems.put(output.getItem(), new ShapedCraftingFood((ShapedRecipes) recipe));
            } else if (recipe instanceof ShapelessRecipes) {
                foodItems.put(output.getItem(), new ShapelessCraftingFood((ShapelessRecipes) recipe));
            } else if (recipe instanceof ShapelessOreRecipe) {
                foodItems.put(output.getItem(), new ShapelessOreCraftingFood((ShapelessOreRecipe) recipe));
            } else if (recipe instanceof ShapedOreRecipe) {
                foodItems.put(output.getItem(), new ShapedOreCraftingFood((ShapedOreRecipe) recipe));
            }
        }
    }

    public static boolean areIngredientsAvailableFor(List<FoodIngredient> craftMatrix, List<IInventory> inventories,
            List<IKitchenItemProvider> itemProviders) {
        int[][] usedStackSize = new int[inventories.size()][];
        for (int i = 0; i < usedStackSize.length; i++) {
            usedStackSize[i] = new int[inventories.get(i).getSizeInventory()];
        }
        boolean[] itemFound = new boolean[craftMatrix.size()];
        matrixLoop: for (int i = 0; i < craftMatrix.size(); i++) {
            if (craftMatrix.get(i) == null || craftMatrix.get(i).isToolItem()) {
                itemFound[i] = true;
                continue;
            }
            for (IKitchenItemProvider itemProvider : itemProviders) {
                itemProvider.clearCraftingBuffer();
                for (ItemStack providedStack : itemProvider.getProvidedItemStacks()) {
                    if (craftMatrix.get(i).isValidItem(providedStack)) {
                        if (itemProvider.addToCraftingBuffer(providedStack)) {
                            itemFound[i] = true;
                            continue matrixLoop;
                        }
                    }
                }
            }
            for (int j = 0; j < inventories.size(); j++) {
                for (int k = 0; k < inventories.get(j).getSizeInventory(); k++) {
                    ItemStack itemStack = inventories.get(j).getStackInSlot(k);
                    if (itemStack != null && craftMatrix.get(i).isValidItem(itemStack)
                            && itemStack.stackSize - usedStackSize[j][k] > 0) {
                        usedStackSize[j][k]++;
                        itemFound[i] = true;
                        continue matrixLoop;
                    }
                }
            }
        }
        for (int i = 0; i < itemFound.length; i++) {
            if (!itemFound[i]) {
                return false;
            }
        }
        return true;
    }

    public static IRecipe findMatchingFoodRecipe(InventoryCraftBook craftBook, World worldObj) {
        for (IRecipe recipe : recipeList) {
            if (recipe.matches(craftBook, worldObj)) {
                return recipe;
            }
        }
        return null;
    }

    public static Collection<FoodRecipe> getFoodRecipes() {
        return foodItems.values();
    }

    public static void addToolItem(ItemStack toolItem) {
        tools.add(toolItem);
    }

    public static boolean isToolItem(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        for (ItemStack toolItem : tools) {
            if (areItemStacksEqualWithWildcard(toolItem, itemStack)) {
                return true;
            }
        }
        return false;
    }

    public static void addOvenFuel(ItemStack itemStack, int fuelTime) {
        ovenFuelItems.put(itemStack, fuelTime);
    }

    public static int getOvenFuelTime(ItemStack itemStack) {
        for (Map.Entry<ItemStack, Integer> entry : ovenFuelItems.entrySet()) {
            if (areItemStacksEqualWithWildcard(entry.getKey(), itemStack)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public static void addSmeltingItem(ItemStack source, ItemStack result) {
        ovenRecipes.put(source, result);
    }

    public static ItemStack getSmeltingResult(ItemStack itemStack) {
        for (Map.Entry<ItemStack, ItemStack> entry : ovenRecipes.entrySet()) {
            if (areItemStacksEqualWithWildcard(entry.getKey(), itemStack)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static void addToastHandler(ItemStack itemStack, ToastHandler toastHandler) {
        toastHandlers.put(itemStack, toastHandler);
    }

    public static void addSinkHandler(ItemStack itemStack, SinkHandler sinkHandler) {
        sinkHandlers.put(itemStack, sinkHandler);
    }

    public static ItemStack getSinkOutput(ItemStack itemStack) {
        for (Map.Entry<ItemStack, SinkHandler> entry : sinkHandlers.entrySet()) {
            if (areItemStacksEqualWithWildcard(entry.getKey(), itemStack)) {
                return entry.getValue().getSinkOutput(itemStack);
            }
        }
        return null;
    }

    public static ItemStack getToastOutput(ItemStack itemStack) {
        for (Map.Entry<ItemStack, ToastHandler> entry : toastHandlers.entrySet()) {
            if (areItemStacksEqualWithWildcard(entry.getKey(), itemStack)) {
                return entry.getValue().getToasterOutput(itemStack);
            }
        }
        return null;
    }

    public static boolean areItemStacksEqualWithWildcard(ItemStack first, ItemStack second) {
        if (first == null || second == null) {
            return false;
        }
        return first.getItem() == second.getItem() && (first.getItemDamage() == second.getItemDamage()
                || first.getItemDamage() == OreDictionary.WILDCARD_VALUE
                || second.getItemDamage() == OreDictionary.WILDCARD_VALUE);
    }
}
