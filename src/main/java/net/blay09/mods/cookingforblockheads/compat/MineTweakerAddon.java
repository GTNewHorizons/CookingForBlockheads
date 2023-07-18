package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;

import cpw.mods.fml.common.Optional;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.util.IEventHandler;

@Optional.Interface(modid = "MineTweaker3", iface = "minetweaker.util.IEventHandler", striprefs = true)
public class MineTweakerAddon implements IEventHandler<MineTweakerImplementationAPI.ReloadEvent> {

    public MineTweakerAddon() {
        MineTweakerImplementationAPI.onPostReload(this);
    }

    @Override
    public void handle(MineTweakerImplementationAPI.ReloadEvent reloadEvent) {
        CookingRegistry.initFoodRegistry();
    }
}
