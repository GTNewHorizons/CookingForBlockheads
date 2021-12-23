package net.blay09.mods.cookingforblockheads.api;

import net.minecraft.item.ItemStack;

public class CookingForBlockheadsAPI {

    private static IInternalMethods internalMethods;
    private static FoodStatsProvider foodStatsProvider;

    public static void setupAPI(IInternalMethods internalMethods) {
        CookingForBlockheadsAPI.internalMethods = internalMethods;
    }

    public static void setFoodStatsProvider(FoodStatsProvider foodStatsProvider) {
        CookingForBlockheadsAPI.foodStatsProvider = foodStatsProvider;
    }

    public static FoodStatsProvider getFoodStatsProvider() {
        return foodStatsProvider;
    }

    public static void addSinkHandler(ItemStack itemStack, SinkHandler sinkHandler) {
        internalMethods.addSinkHandler(itemStack, sinkHandler);
    }

    public static void addToastHandler(ItemStack itemStack, ToastHandler toastHandler) {
        internalMethods.addToastHandler(itemStack, toastHandler);
    }

    public static void addOvenFuel(ItemStack fuelItem, int fuelTime) {
        internalMethods.addOvenFuel(fuelItem, fuelTime);
    }

    public static void addOvenRecipe(ItemStack sourceItem, ItemStack resultItem) {
        internalMethods.addOvenRecipe(sourceItem, resultItem);
    }

    public static void addToolItem(ItemStack toolItem) {
        internalMethods.addToolItem(toolItem);
    }
}
