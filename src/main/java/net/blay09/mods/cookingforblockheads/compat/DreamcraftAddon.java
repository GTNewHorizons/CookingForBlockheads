package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.api.event.FoodRegistryInitEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class DreamcraftAddon {

    private static final String[] DREAMCRAFT_ITEMS = new String[] { "EdibleSalt", "WetTofu", };

    public DreamcraftAddon() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onFoodRegistryInit(FoodRegistryInitEvent event) {
        for (String s : DREAMCRAFT_ITEMS) {
            ItemStack itemStack = GameRegistry.findItemStack("dreamcraft", s, 1);
            if (itemStack != null) {
                event.registerNonFoodRecipe(itemStack);
            }
        }
    }
}
