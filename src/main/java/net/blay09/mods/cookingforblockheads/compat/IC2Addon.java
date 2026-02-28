package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.CookingConfig;
import net.blay09.mods.cookingforblockheads.api.event.FoodRegistryInitEvent;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class IC2Addon {

    public IC2Addon() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onFoodRegistryInit(FoodRegistryInitEvent event) {
        // abort early since gt5u also add the ore dict for this.
        if (CookingConfig.gregtech5uLoaded) return;
        event.registerNonFoodRecipe(GameRegistry.findItemStack("IC2", "itemCofeePowder", 1));
    }
}
