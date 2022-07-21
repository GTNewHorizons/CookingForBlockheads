package net.blay09.mods.cookingforblockheads.container;

import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

@ChestContainer
public class ContainerFridge extends ContainerWithInventory {

    public ContainerFridge(InventoryPlayer inventoryPlayer, IInventory fridgeInventory) {
        super(inventoryPlayer, fridgeInventory);

        fridgeInventory.openInventory();
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        inventory.closeInventory();
    }
}
