package net.blay09.mods.cookingforblockheads.compat;

import java.util.ArrayList;
import java.util.List;

import net.blay09.mods.cookingforblockheads.api.CookingForBlockheadsAPI;
import net.blay09.mods.cookingforblockheads.api.event.FoodRegistryInitEvent;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenItemProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.items.IDMetaTool01;

public class GregTech5UAddon {

    public abstract static class ToolWrapper implements IKitchenItemProvider {

        protected final List<ItemStack> itemStacks = new ArrayList<>();

        public ToolWrapper(Block block) {}

        @Override
        public List<ItemStack> getProvidedItemStacks() {
            return itemStacks;
        }

        @Override
        public boolean addToCraftingBuffer(ItemStack itemStack) {
            return true;
        }

        @Override
        public void clearCraftingBuffer() {}

        @Override
        public void craftingComplete() {}
    }

    private static final ItemStack[] ADDITIONAL_RECIPES = new ItemStack[] {
            GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L),
            GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L),
            GameRegistry.findItemStack("IC2", "itemCofeePowder", 1), };

    private static final short[] META_TOOLS = new short[] { (short) IDMetaTool01.HARDHAMMER.ID,
            (short) IDMetaTool01.SOFTMALLET.ID, (short) IDMetaTool01.KNIFE.ID, (short) IDMetaTool01.BUTCHERYKNIFE.ID,
            (short) IDMetaTool01.ROLLING_PIN.ID, (short) IDMetaTool01.MORTAR.ID, };

    public GregTech5UAddon() {

        for (short meta : META_TOOLS) {
            ItemStack toolItem = GameRegistry.makeItemStack("gregtech:gt.metatool.01", meta, 1, null);
            if (toolItem != null) {
                CookingForBlockheadsAPI.addToolItem(toolItem);
            }
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onFoodRegistryInit(FoodRegistryInitEvent event) {
        for (ItemStack stack : ADDITIONAL_RECIPES) {
            if (stack != null) {
                event.registerNonFoodRecipe(stack);
            }
        }
    }
}
