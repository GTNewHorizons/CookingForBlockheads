package net.blay09.mods.cookingforblockheads;

import cpw.mods.fml.common.network.IGuiHandler;
import net.blay09.mods.cookingforblockheads.client.GuiCookingOven;
import net.blay09.mods.cookingforblockheads.client.GuiInventory;
import net.blay09.mods.cookingforblockheads.client.GuiRecipeBook;
import net.blay09.mods.cookingforblockheads.container.ContainerCookingOven;
import net.blay09.mods.cookingforblockheads.container.ContainerCounter;
import net.blay09.mods.cookingforblockheads.container.ContainerFridge;
import net.blay09.mods.cookingforblockheads.container.ContainerRecipeBook;
import net.blay09.mods.cookingforblockheads.tile.TileCookingTable;
import net.blay09.mods.cookingforblockheads.tile.TileCounter;
import net.blay09.mods.cookingforblockheads.tile.TileFridge;
import net.blay09.mods.cookingforblockheads.tile.TileOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static final int ITEM_RECIPE_BOOK = 1;
    public static final int COOKING_TABLE = 2;
    public static final int COOKING_OVEN = 3;
    public static final int FRIDGE = 4;
    public static final int COUNTER = 5;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == ITEM_RECIPE_BOOK) {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null) {
                switch (heldItem.getItemDamage()) {
                    case 0:
                        return new ContainerRecipeBook(player, false);
                    case 1:
                        return new ContainerRecipeBook(player, false).allowCrafting();
                    case 3:
                        return new ContainerRecipeBook(player, false).setNoFilter();
                }
            }
        } else {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            switch (id) {
                case COOKING_TABLE:
                    if (world.getBlock(x, y, z) == CookingForBlockheads.blockCookingTable
                            && tileEntity instanceof TileCookingTable) {
                        if (((TileCookingTable) tileEntity).hasNoFilterBook()) {
                            return new ContainerRecipeBook(player, false)
                                    .allowCrafting()
                                    .allowSmelting()
                                    .setNoFilter()
                                    .setKitchenMultiBlock(new KitchenMultiBlock(world, x, y, z));
                        } else {
                            return new ContainerRecipeBook(player, false)
                                    .allowCrafting()
                                    .allowSmelting()
                                    .setKitchenMultiBlock(new KitchenMultiBlock(world, x, y, z));
                        }
                    }
                    break;
                case COOKING_OVEN:
                    if (tileEntity instanceof TileOven) {
                        return new ContainerCookingOven(player.inventory, (TileOven) tileEntity);
                    }
                    break;
                case FRIDGE:
                    if (tileEntity instanceof TileFridge) {
                        ((TileFridge) tileEntity).updateMultiblock();
                        return new ContainerFridge(player.inventory, ((TileFridge) tileEntity).getInventory());
                    }
                    break;
                case COUNTER:
                    if (tileEntity instanceof TileCounter) {
                        return new ContainerCounter(player.inventory, ((TileCounter) tileEntity).getInventory());
                    }
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == ITEM_RECIPE_BOOK) {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null) {
                switch (heldItem.getItemDamage()) {
                    case 0:
                    case 3:
                        return new GuiRecipeBook(new ContainerRecipeBook(player, true));
                    case 1:
                        return new GuiRecipeBook(new ContainerRecipeBook(player, true).allowCrafting());
                }
            }
        } else {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            switch (ID) {
                case COOKING_TABLE:
                    return new GuiRecipeBook(new ContainerRecipeBook(player, true)
                            .allowCrafting()
                            .allowSmelting());
                case COOKING_OVEN:
                    if (tileEntity instanceof TileOven) {
                        return new GuiCookingOven(player.inventory, (TileOven) tileEntity);
                    }
                case FRIDGE:
                    if (tileEntity instanceof TileFridge) {
                        ((TileFridge) tileEntity).updateMultiblock();
                        return new GuiInventory(
                                new ContainerFridge(player.inventory, ((TileFridge) tileEntity).getInventory()));
                    }
                case COUNTER:
                    if (tileEntity instanceof TileCounter) {
                        return new GuiInventory(
                                new ContainerCounter(player.inventory, ((TileCounter) tileEntity).getInventory()));
                    }
            }
        }
        return null;
    }
}
