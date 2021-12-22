package net.blay09.mods.cookingforblockheads.compat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.api.CookingForBlockheadsAPI;
import net.blay09.mods.cookingforblockheads.api.FoodStatsProvider;
import net.blay09.mods.cookingforblockheads.api.ToastHandler;
import net.blay09.mods.cookingforblockheads.api.event.FoodRegistryInitEvent;
import net.blay09.mods.cookingforblockheads.api.SinkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class VanillaAddon implements FoodStatsProvider {

    public VanillaAddon() {
        SinkHandler simpleHandler = new SinkHandler() {
            @Override
            public ItemStack getSinkOutput(ItemStack itemStack) {
                ItemStack result = itemStack.copy();
                result.stackSize = 1;
                result.setItemDamage(0);
                return result;
            }
        };
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), simpleHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Blocks.carpet, 1, OreDictionary.WILDCARD_VALUE), simpleHandler);
        SinkHandler armorHandler = new SinkHandler() {
            @Override
            public ItemStack getSinkOutput(ItemStack itemStack) {
                if(itemStack.getItem() instanceof ItemArmor) {
                    ((ItemArmor) itemStack.getItem()).removeColor(itemStack);
                }
                return itemStack;
            }
        };
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.leather_boots, 1, OreDictionary.WILDCARD_VALUE), armorHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.leather_chestplate, 1, OreDictionary.WILDCARD_VALUE), armorHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.leather_helmet, 1, OreDictionary.WILDCARD_VALUE), armorHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.leather_leggings, 1, OreDictionary.WILDCARD_VALUE), armorHandler);

        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.milk_bucket), new SinkHandler() {
            @Override
            public ItemStack getSinkOutput(ItemStack itemStack) {
                return new ItemStack(Items.bucket, 1);
            }
        });

        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.potionitem, 1, OreDictionary.WILDCARD_VALUE), new SinkHandler() {
            @Override
            public ItemStack getSinkOutput(ItemStack itemStack) {
                return new ItemStack(Items.glass_bottle, 1);
            }
        });

        CookingForBlockheadsAPI.addToastHandler(new ItemStack(Items.bread), new ToastHandler() {
            @Override
            public ItemStack getToasterOutput(ItemStack itemStack) {
                return new ItemStack(CookingForBlockheads.itemToast);
            }
        });

        MinecraftForge.EVENT_BUS.register(this);

        CookingForBlockheadsAPI.setFoodStatsProvider(this);
    }

    @SubscribeEvent
    public void onFoodRegistryInit(FoodRegistryInitEvent event) {
        event.registerNonFoodRecipe(new ItemStack(Items.cake));
        event.registerNonFoodRecipe(new ItemStack(Items.sugar));
    }

    @Override
    public float getSaturation(ItemStack itemStack, EntityPlayer entityPlayer) {
        ItemFood item = (ItemFood) itemStack.getItem();
        return item.func_150906_h(itemStack);
    }

    @Override
    public int getFoodLevel(ItemStack itemStack, EntityPlayer entityPlayer) {
        ItemFood item = (ItemFood) itemStack.getItem();
        return item.func_150905_g(itemStack);
    }
}
