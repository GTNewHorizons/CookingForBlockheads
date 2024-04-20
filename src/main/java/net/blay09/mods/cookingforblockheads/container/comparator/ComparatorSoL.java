package net.blay09.mods.cookingforblockheads.container.comparator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Comparator;
import java.util.function.BiFunction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import squeek.spiceoflife.ModConfig;
import squeek.spiceoflife.foodtracker.FoodHistory;
import squeek.spiceoflife.foodtracker.foodqueue.FoodQueue;
import squeek.spiceoflife.helpers.FoodHelper;

public class ComparatorSoL implements Comparator<ItemStack> {

    private final ComparatorName fallback = new ComparatorName();
    private final EntityPlayer entityPlayer;
    public static boolean spiceCompat = false;
    private static MethodHandle getHistoryCompat;
    private static BiFunction<ItemStack, FoodHistory, Boolean> getEverEaten;
    static {
        try {
            getHistoryCompat = MethodHandles.lookup()
                    .findVirtual(FoodHistory.class, "getHistory", MethodType.methodType(FoodQueue.class));
            getEverEaten = (itemstack, history) -> {
                try {
                    return ((FoodQueue) getHistoryCompat.invokeExact(history)).stream()
                            .anyMatch(foodEaten -> foodEaten.itemStack.isItemEqual(itemstack));
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            };
        } catch (NoSuchMethodException | SecurityException e) {
            getEverEaten = (itemstack, history) -> { return history.hasEverEaten(itemstack); };
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public ComparatorSoL(EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
    }

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        FoodHistory foodHistory = FoodHistory.get(entityPlayer);

        if (!ModConfig.FOOD_MODIFIER_ENABLED) {
            return fallback.compare(o1, o2);
        }

        int totalFoodEaten = foodHistory.totalFoodsEatenAllTime;
        if (ModConfig.FOOD_EATEN_THRESHOLD > 0 && totalFoodEaten < ModConfig.FOOD_EATEN_THRESHOLD) {
            return fallback.compare(o1, o2);
        }

        boolean isFoodFirst = FoodHelper.isValidFood(o1);
        boolean isFoodSecond = FoodHelper.isValidFood(o2);
        if (!isFoodFirst && !isFoodSecond) {
            return fallback.compare(o1, o2);
        } else if (!isFoodFirst) {
            return 1;
        } else if (!isFoodSecond) {
            return -1;
        }
        boolean everEatenFirstFood = ComparatorSoL.getEverEaten.apply(o1, foodHistory);
        boolean everEatenSecondFood = ComparatorSoL.getEverEaten.apply(o2, foodHistory);

        if (!everEatenFirstFood && !everEatenSecondFood) {
            return fallback.compare(o1, o2);
        } else if (!everEatenFirstFood) {
            return -1;
        } else if (!everEatenSecondFood) {
            return 1;
        }

        int eatenCountFirstFood = foodHistory.getFoodCountIgnoringFoodGroups(o1);
        int eatenCountSecondFood = foodHistory.getFoodCountIgnoringFoodGroups(o2);
        int diffEatenCount = eatenCountFirstFood - eatenCountSecondFood;
        if (diffEatenCount == 0) {
            return fallback.compare(o1, o2);
        }
        return diffEatenCount;
    }
}
