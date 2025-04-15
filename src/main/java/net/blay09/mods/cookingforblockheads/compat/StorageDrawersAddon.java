package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.KitchenMultiBlock;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.registry.GameRegistry;

public class StorageDrawersAddon {

    public StorageDrawersAddon() {
        KitchenMultiBlock.tileEntityWrappers
                .put("com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawersStandard", DrawerWrapper.class);
                .put("com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawersStandard", SimpleStorageProvider.class);
        KitchenMultiBlock.registerConnectorBlock(GameRegistry.findBlock("StorageDrawers", "trim"));
        MinecraftForge.EVENT_BUS.register(this);
    }
}
