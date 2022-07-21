package net.blay09.mods.cookingforblockheads.compat;

import cpw.mods.fml.common.registry.GameRegistry;
import net.blay09.mods.cookingforblockheads.KitchenMultiBlock;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class StorageDrawersAddon {
    public StorageDrawersAddon() {
        KitchenMultiBlock.tileEntityWrappers.put(
                "com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawersStandard", DrawerWrapper.class);
        KitchenMultiBlock.registerConnectorBlock(GameRegistry.findBlock("StorageDrawers", "trim"));
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static class DrawerWrapper implements IKitchenStorageProvider {
        TileEntity tile;

        public DrawerWrapper(TileEntity tile) {
            this.tile = tile;
        }

        @Override
        public IInventory getInventory() {
            return (IInventory) tile;
        }
    }
}
