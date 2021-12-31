package net.blay09.mods.cookingforblockheads.container.comparator;

import net.blay09.mods.cookingforblockheads.api.CookingForBlockheadsAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import squeek.applecore.api.AppleCoreAPI;

import java.util.Comparator;

public class ComparatorSaturation implements Comparator<ItemStack> {

    private final ComparatorName fallback = new ComparatorName();
    private final EntityPlayer entityPlayer;

    public ComparatorSaturation(EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
    }

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        boolean isFirstFood = AppleCoreAPI.accessor.isFood(o1);
        boolean isSecondFood = AppleCoreAPI.accessor.isFood(o2);
        if(!isFirstFood && !isSecondFood) {
            return 0;
        } else if(!isFirstFood) {
            return 1;
        } else if(!isSecondFood) {
            return -1;
        }
        int result = (int) (CookingForBlockheadsAPI.getFoodStatsProvider().getSaturation(o2, entityPlayer) * 100 - CookingForBlockheadsAPI.getFoodStatsProvider().getSaturation(o1, entityPlayer) * 100);
        if(result == 0) {
            return fallback.compare(o1, o2);
        }
        return result;
    }

}
