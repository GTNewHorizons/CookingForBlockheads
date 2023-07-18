package net.blay09.mods.cookingforblockheads;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenItemProvider;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenSmeltingProvider;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.blay09.mods.cookingforblockheads.api.kitchen.IMultiblockKitchen;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.registry.GameRegistry;

public class KitchenMultiBlock {

    private static final List<Block> blockConnectors = new ArrayList<>();
    public static final Map<String, Class<? extends IMultiblockKitchen>> tileEntityWrappers = new HashMap<>();
    public static final Map<String, Class<? extends IMultiblockKitchen>> blockWrappers = new HashMap<>();

    private final Set<ChunkPosition> checkedPos = Sets.newHashSet();
    private final List<IKitchenStorageProvider> storageProviderList = new ArrayList<>();
    private final List<IKitchenItemProvider> itemProviderList = new ArrayList<>();
    private final List<IKitchenSmeltingProvider> smeltingProviderList = new ArrayList<>();

    public KitchenMultiBlock(World world, int x, int y, int z) {
        findNeighbourKitchenBlocks(world, x, y, z, true);
    }

    private void findNeighbourKitchenBlocks(World world, int x, int y, int z, boolean extendedUpSearch) {
        List<ChunkPosition> dirToCheck = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            final int upSearch = (extendedUpSearch && dir == ForgeDirection.UP) ? 2 : 1;
            for (int j = 0; j < upSearch; j++) {
                ChunkPosition pos = new ChunkPosition(x + dir.offsetX, y + dir.offsetY + j, z + dir.offsetZ);
                dirToCheck.add(pos);
            }
        }

        for (ChunkPosition pos : dirToCheck) {
            // TODO: Add extended up search for cabinets
            if (checkedPos.add(pos)) {
                final TileEntity tileEntity = world.getTileEntity(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                final Block block = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                final IMultiblockKitchen kitchenPart;
                if (tileEntity instanceof IMultiblockKitchen) {
                    kitchenPart = (IMultiblockKitchen) tileEntity;
                } else if (tileEntity != null) {
                    kitchenPart = getWrapper(tileEntity);
                } else {
                    kitchenPart = getWrapper(block);
                }
                if (kitchenPart != null) {
                    if (kitchenPart instanceof IKitchenStorageProvider) {
                        storageProviderList.add((IKitchenStorageProvider) kitchenPart);
                    }
                    if (kitchenPart instanceof IKitchenSmeltingProvider) {
                        smeltingProviderList.add((IKitchenSmeltingProvider) kitchenPart);
                    }
                    if (kitchenPart instanceof IKitchenItemProvider) {
                        itemProviderList.add((IKitchenItemProvider) kitchenPart);
                    }
                    findNeighbourKitchenBlocks(world, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, true);
                } else {
                    if (blockConnectors.contains(block)) {
                        findNeighbourKitchenBlocks(world, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, false);
                    }
                }
            }
        }
    }

    private final List<IInventory> sourceInventories = new ArrayList<>();

    public List<IInventory> getSourceInventories(InventoryPlayer playerInventory) {
        sourceInventories.clear();
        sourceInventories.add(playerInventory);
        for (IKitchenStorageProvider provider : storageProviderList) {
            sourceInventories.add(provider.getInventory());
        }
        return sourceInventories;
    }

    public ItemStack smeltItem(ItemStack itemStack, int count) {
        ItemStack restStack = itemStack.copy().splitStack(count);
        for (IKitchenSmeltingProvider provider : smeltingProviderList) {
            restStack = provider.smeltItem(restStack);
            if (restStack == null) {
                break;
            }
        }
        itemStack.stackSize -= (count - (restStack != null ? restStack.stackSize : 0));
        if (itemStack.stackSize <= 0) {
            return null;
        }
        return itemStack;
    }

    public static void registerConnectorBlock(final Block block) {
        blockConnectors.add(block);
    }

    public boolean hasSmeltingProvider() {
        return smeltingProviderList.size() > 0;
    }

    public List<IKitchenItemProvider> getItemProviders() {
        return itemProviderList;
    }

    public static IMultiblockKitchen getWrapper(Block block) {
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(block);
        if (identifier != null) {
            Class<? extends IMultiblockKitchen> clazz = blockWrappers.get(identifier.modId + ":" + identifier.name);
            if (clazz != null) {
                try {
                    return clazz.getConstructor(Block.class).newInstance(block);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static IMultiblockKitchen getWrapper(TileEntity tileEntity) {
        Class<? extends IMultiblockKitchen> clazz = tileEntityWrappers.get(tileEntity.getClass().getName());
        if (clazz != null) {
            try {
                return clazz.getConstructor(TileEntity.class).newInstance(tileEntity);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
