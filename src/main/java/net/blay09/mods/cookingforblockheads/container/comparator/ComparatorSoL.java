package net.blay09.mods.cookingforblockheads.container.comparator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import squeek.spiceoflife.ModConfig;
import squeek.spiceoflife.foodtracker.FoodEaten;
import squeek.spiceoflife.foodtracker.FoodHistory;
import squeek.spiceoflife.foodtracker.foodqueue.FoodQueue;
import squeek.spiceoflife.helpers.FoodHelper;

public class ComparatorSoL implements Comparator<ItemStack> {

    private final ComparatorName fallback = new ComparatorName();
    private final EntityPlayer entityPlayer;
    public static boolean spiceCompat = false;

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
        boolean everEatenFirstFood = false, everEatenSecondFood = false;
        if (!spiceCompat) {
            everEatenFirstFood = foodHistory.hasEverEaten(o1);
            everEatenSecondFood = foodHistory.hasEverEaten(o2);
        } else {
            // Older versions of Spice of Life don't have full food history, and therefore don't have a hasEverEaten
            // This has similar, though not identical, behavior for those older versions.
            try {
                Method method = foodHistory.getClass().getDeclaredMethod("getHistory");
                Object history = method.invoke(foodHistory);
                FoodQueue foodQueue = (FoodQueue) history;
                for (FoodEaten foodEaten : foodQueue) {
                    if (foodEaten.itemStack.isItemEqual(o1)) {
                        everEatenFirstFood = true;
                    }
                    if (foodEaten.itemStack.isItemEqual(o2)) {
                        everEatenSecondFood = true;
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                System.out.println("Spice of Life compatibility failed");
                System.out.println(ex.getMessage());
                return -1;
            }
        }
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
