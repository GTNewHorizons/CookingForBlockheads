package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.KitchenMultiBlock;

import cpw.mods.fml.common.registry.GameRegistry;

public class StorageDrawersAddon {

    public StorageDrawersAddon() {
        KitchenMultiBlock.tileEntityWrappers.put(
                "com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawersStandard",
                SimpleStorageProvider.class);
        KitchenMultiBlock.tileEntityWrappers.put(
                "com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityController",
                SimpleStorageProvider.class);

        KitchenMultiBlock.registerConnectorBlock(GameRegistry.findBlock("StorageDrawers", "trim"));
    }
}
