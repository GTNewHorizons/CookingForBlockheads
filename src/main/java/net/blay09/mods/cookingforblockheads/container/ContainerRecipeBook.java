package net.blay09.mods.cookingforblockheads.container;

import com.google.common.collect.ArrayListMultimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import invtweaks.api.container.IgnoreContainer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.blay09.mods.cookingforblockheads.KitchenMultiBlock;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenItemProvider;
import net.blay09.mods.cookingforblockheads.container.comparator.ComparatorName;
import net.blay09.mods.cookingforblockheads.container.inventory.InventoryCraftBook;
import net.blay09.mods.cookingforblockheads.container.inventory.InventoryRecipeBook;
import net.blay09.mods.cookingforblockheads.container.inventory.InventoryRecipeBookMatrix;
import net.blay09.mods.cookingforblockheads.container.slot.SlotCraftMatrix;
import net.blay09.mods.cookingforblockheads.container.slot.SlotRecipe;
import net.blay09.mods.cookingforblockheads.network.MessageClickRecipe;
import net.blay09.mods.cookingforblockheads.network.MessageRecipeInfo;
import net.blay09.mods.cookingforblockheads.network.MessageSyncList;
import net.blay09.mods.cookingforblockheads.network.NetworkHandler;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.registry.food.FoodRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2FPacketSetSlot;

@IgnoreContainer
public class ContainerRecipeBook extends Container {

    private final EntityPlayer player;
    private boolean allowCrafting;
    private boolean allowSmelting;
    private final boolean isClientSide;

    private final InventoryRecipeBook recipeBook;
    private final SlotRecipe[] recipeBookSlots = new SlotRecipe[12];
    private final InventoryRecipeBookMatrix craftMatrix;
    private final SlotCraftMatrix[] craftMatrixSlots = new SlotCraftMatrix[9];

    private final ArrayListMultimap<String, FoodRecipe> availableRecipes = ArrayListMultimap.create();
    private final List<ItemStack> sortedRecipes = new ArrayList<>();

    private String searchTerm = "";

    private final InventoryCraftBook craftBook;
    private Comparator<ItemStack> currentSort = new ComparatorName();
    private int scrollOffset;
    private boolean isFurnaceRecipe;

    private boolean isSelectionDirty;
    private boolean isRecipeListDirty;

    private int syncSlotIndex = -1;
    private int currentSlotIndex = -1;
    private FoodRecipe currentRecipe;
    private boolean hasVariants;
    private boolean isMissingTools;
    private boolean isMissingOven;

    private String currentRecipeKey;
    private List<FoodRecipe> currentRecipeList;
    private int currentRecipeIdx;
    private boolean noFilter;
    private final List<IKitchenItemProvider> emptyProviderList = new ArrayList<>();
    private final List<IInventory> playerInventoryList = new ArrayList<>();
    private KitchenMultiBlock kitchenMultiBlock;

    public ContainerRecipeBook(EntityPlayer player, boolean isClientSide) {
        this.player = player;
        this.playerInventoryList.add(player.inventory);
        this.allowCrafting = false;
        this.allowSmelting = false;
        this.isClientSide = isClientSide;

        craftMatrix = new InventoryRecipeBookMatrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                craftMatrixSlots[j + i * 3] =
                        new SlotCraftMatrix(player, craftMatrix, j + i * 3, 24 + j * 18, 20 + i * 18);
                craftMatrixSlots[j + i * 3].setSourceInventories(playerInventoryList);
                craftMatrixSlots[j + i * 3].setItemProviders(emptyProviderList);
                addSlotToContainer(craftMatrixSlots[j + i * 3]);
            }
        }

        recipeBook = new InventoryRecipeBook();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                recipeBookSlots[j + i * 3] = new SlotRecipe(recipeBook, j + i * 3, 102 + j * 18, 11 + i * 18);
                addSlotToContainer(recipeBookSlots[j + i * 3]);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 92 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 150));
        }

        updateRecipeList();

        craftBook = new InventoryCraftBook(this);
        craftBook.setItemProviders(emptyProviderList);
        craftBook.setInventories(playerInventoryList);

        findAvailableRecipes();
    }

    public void setCraftMatrix(FoodRecipe recipe) {
        if (recipe != null) {
            for (SlotCraftMatrix previewSlot : craftMatrixSlots) {
                previewSlot.setIngredient(null);
                previewSlot.setEnabled(false);
                if (!isClientSide) {
                    previewSlot.updateVisibleStacks();
                }
            }
            isFurnaceRecipe = recipe.isSmeltingRecipe();
            if (isFurnaceRecipe) {
                craftMatrixSlots[4].setIngredient(recipe.getCraftMatrix().get(0));
                craftMatrixSlots[4].setEnabled(true);
                if (!isClientSide) {
                    craftMatrixSlots[4].updateVisibleStacks();
                }
            } else {
                int offset = 0;
                if (recipe.getRecipeWidth() == 1) {
                    // center column
                    offset += 1;
                }
                if (recipe.getRecipeHeight() == 1) {
                    // center row
                    offset += 3;
                }
                for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
                    int origX = i % recipe.getRecipeWidth();
                    int origY = i / recipe.getRecipeWidth();
                    int targetIdx = origY * 3 + origX;
                    targetIdx += offset;
                    if (i < recipe.getCraftMatrix().size()) {
                        craftMatrixSlots[targetIdx].setIngredient(
                                recipe.getCraftMatrix().get(i));
                    }
                    craftMatrixSlots[i].setEnabled(true);
                    if (!isClientSide) {
                        craftMatrixSlots[i].updateVisibleStacks();
                    }
                }
            }
        }
    }

    public boolean hasVariants() {
        return hasVariants;
    }

    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
        updateRecipeList(false);
    }

    public void search(String term) {
        this.searchTerm = term;
    }

    public void updateRecipeList(boolean resetCraftMatrix) {
        boolean noRecipes = getAvailableRecipeCount() == 0;
        for (int i = 0; i < recipeBook.getSizeInventory(); i++) {
            ItemStack lastItemStack = recipeBook.getStackInSlot(i);
            int recipeIdx = i + scrollOffset * 3;
            if (recipeIdx < sortedRecipes.size()) {
                recipeBook.setFoodItem(
                        i, availableRecipes.get(sortedRecipes.get(recipeIdx).toString()));
            } else {
                recipeBook.setFoodItem(i, null);
            }
            ItemStack itemStack = recipeBook.getStackInSlot(i);
            if (recipeIdx == currentSlotIndex
                    && !ItemStack.areItemStacksEqual(lastItemStack, itemStack)
                    && resetCraftMatrix) {
                currentSlotIndex = -1;
                currentRecipe = null;
                currentRecipeList = null;
                setCraftMatrix(null);
            }
            recipeBookSlots[i].putStack(itemStack);
            recipeBookSlots[i].setEnabled(!noRecipes);
        }
        if (noRecipes) {
            setCraftMatrix(null);
            if (!isClientSide) {
                currentRecipeList = null;
                currentRecipeIdx = -1;
            }
        } else if (!isClientSide) {
            currentRecipeList = availableRecipes.get(currentRecipeKey);
        }
    }

    public void updateRecipeList() {
        this.updateRecipeList(true);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack slotClick(int slotIdx, int button, int mode, EntityPlayer player) {
        if (mode == 6) {
            mode = 0;
        }
        if ((mode == 0 || mode == 1)) {
            if (isClientSide) {
                clickRecipe(slotIdx, mode == 1);
                NetworkHandler.instance.sendToServer(new MessageClickRecipe(slotIdx, scrollOffset, mode == 1));
            }
        }
        return super.slotClick(slotIdx, button, mode, player);
    }

    public void clickRecipe(int slotIdx, boolean shiftClick) {
        if (slotIdx > 0 && slotIdx < inventorySlots.size() && inventorySlots.get(slotIdx) instanceof SlotRecipe) {
            SlotRecipe slot = (SlotRecipe) inventorySlots.get(slotIdx);
            if (slot.getStack() != null) {
                if (!isClientSide && canClickCraft((scrollOffset * 3) + slot.getSlotIndex())) {
                    tryCraft(player, currentRecipe, shiftClick);
                    return;
                } else if (!isClientSide && !isMissingOven && canClickSmelt((scrollOffset * 3) + slot.getSlotIndex())) {
                    trySmelt(player, currentRecipe, shiftClick);
                    return;
                }
                int oldSlotIndex = currentSlotIndex;
                currentSlotIndex = (scrollOffset * 3) + slot.getSlotIndex();
                if (oldSlotIndex != currentSlotIndex) {
                    if (!isClientSide) {
                        currentRecipeKey =
                                recipeBook.getStackInSlot(slot.getSlotIndex()).toString();
                        currentRecipeList = recipeBook.getFoodList(slot.getSlotIndex());
                        currentRecipeIdx = 0;
                        currentRecipe = currentRecipeList.get(currentRecipeIdx);
                        setCraftMatrix(currentRecipe);
                        isSelectionDirty = true;
                    }
                }
            }
        }
    }

    private void trySmelt(EntityPlayer player, FoodRecipe recipe, boolean isShiftDown) {
        if (!recipe.isSmeltingRecipe()) {
            return;
        }
        List<IInventory> sourceInventories = kitchenMultiBlock.getSourceInventories(player.inventory);
        for (int i = 0; i < sourceInventories.size(); i++) {
            for (int j = 0; j < sourceInventories.get(i).getSizeInventory(); j++) {
                ItemStack itemStack = sourceInventories.get(i).getStackInSlot(j);
                if (itemStack != null) {
                    for (ItemStack ingredientStack :
                            recipe.getCraftMatrix().get(0).getItemStacks()) {
                        if (CookingRegistry.areItemStacksEqualWithWildcard(itemStack, ingredientStack)) {
                            int count =
                                    isShiftDown ? Math.min(itemStack.stackSize, ingredientStack.getMaxStackSize()) : 1;
                            ItemStack restStack = kitchenMultiBlock.smeltItem(itemStack, count);
                            sourceInventories.get(i).setInventorySlotContents(j, restStack);
                            if (i == 0) { // Player Inventory
                                if (j < 9) {
                                    ((EntityPlayerMP) player).sendSlotContents(this, 48 + j, restStack);
                                } else {
                                    ((EntityPlayerMP) player).sendSlotContents(this, 21 + j - 9, restStack);
                                }
                            }
                            player.inventory.markDirty();
                            return;
                        }
                    }
                }
            }
        }
    }

    private void tryCraft(EntityPlayer player, FoodRecipe recipe, boolean isShiftDown) {
        if (recipe.isSmeltingRecipe()) {
            return;
        }
        craftBook.prepareRecipe(player, recipe);
        if (!isShiftDown) {
            if (craftBook.canMouseItemHold(player, recipe)) {
                ItemStack craftingResult = craftBook.craft(player, recipe);
                if (craftingResult != null) {
                    ItemStack mouseItem = player.inventory.getItemStack();
                    if (mouseItem != null) {
                        mouseItem.stackSize += craftingResult.stackSize;
                        ((EntityPlayerMP) player)
                                .playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, 0, mouseItem));
                    } else {
                        player.inventory.setItemStack(craftingResult);
                        ((EntityPlayerMP) player)
                                .playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, 0, craftingResult));
                    }
                }
                player.inventory.markDirty();
                player.inventoryContainer.detectAndSendChanges();
            }
        } else {
            ItemStack craftingResult;
            int crafted = 0;
            while (crafted < 64 && (craftingResult = craftBook.craft(player, recipe)) != null) {
                crafted += craftingResult.stackSize;
                if (!player.inventory.addItemStackToInventory(craftingResult)) {
                    if (player.inventory.getItemStack() == null) {
                        player.inventory.setItemStack(craftingResult);
                    } else {
                        player.dropPlayerItemWithRandomChoice(craftingResult, false);
                    }
                    break;
                }
            }
            player.inventory.markDirty();
            player.inventoryContainer.detectAndSendChanges();
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if (i >= 48 && i < 57) { // Inventory to Hotbar
                if (!mergeItemStack(slotStack, 21, 48, false)) {
                    return null;
                }
            } else if (i >= 21 && i < 48) { // Hotbar to Inventory
                if (!mergeItemStack(slotStack, 48, 57, false)) {
                    return null;
                }
            }

            if (slotStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.stackSize == itemStack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, slotStack);
        }
        return itemStack;
    }

    public int getAvailableRecipeCount() {
        return sortedRecipes.size();
    }

    public boolean isFurnaceRecipe() {
        return isFurnaceRecipe;
    }

    public boolean hasSelection() {
        return currentRecipe != null;
    }

    public boolean canClickSmelt(int slotIndex) {
        return allowSmelting
                && currentSlotIndex == slotIndex
                && currentRecipe != null
                && currentRecipe.isSmeltingRecipe();
    }

    public boolean canClickCraft(int slotIndex) {
        return allowCrafting
                && currentSlotIndex == slotIndex
                && currentRecipe != null
                && !currentRecipe.isSmeltingRecipe();
    }

    public boolean isMissingTools() {
        return isMissingTools;
    }

    public boolean isRecipeListDirty() {
        return isRecipeListDirty;
    }

    public void markDirty(boolean dirty) {
        this.isRecipeListDirty = dirty;
    }

    @SideOnly(Side.CLIENT)
    public void setAvailableItems(
            List<ItemStack> sortedRecipes, ArrayListMultimap<String, FoodRecipe> availableRecipes) {
        this.sortedRecipes.clear();
        this.sortedRecipes.addAll(sortedRecipes);
        this.availableRecipes.clear();
        this.availableRecipes.putAll(availableRecipes);
        search(searchTerm);
        markDirty(true);
    }

    public boolean gotRecipeInfo() {
        return syncSlotIndex == currentSlotIndex;
    }

    /**
     * SERVER ONLY
     */
    public void findAvailableRecipes() {
        availableRecipes.clear();
        sortedRecipes.clear();
        for (FoodRecipe foodRecipe : CookingRegistry.getFoodRecipes()) {
            ItemStack foodStack = foodRecipe.getOutputItem();
            if (foodStack != null) {
                if (noFilter
                        || CookingRegistry.areIngredientsAvailableFor(
                                foodRecipe.getCraftMatrix(),
                                kitchenMultiBlock != null
                                        ? kitchenMultiBlock.getSourceInventories(player.inventory)
                                        : playerInventoryList,
                                kitchenMultiBlock != null ? kitchenMultiBlock.getItemProviders() : emptyProviderList)) {
                    String foodStackString = foodStack.toString();
                    if (!availableRecipes.containsKey(foodStackString)) {
                        sortedRecipes.add(foodStack);
                    }
                    availableRecipes.put(foodStackString, foodRecipe);
                }
            }
        }
        isRecipeListDirty = true;
    }

    /**
     * SERVER ONLY
     * @param comparator
     */
    public void sortRecipes(Comparator<ItemStack> comparator) {
        if (currentSort != comparator) {
            sortingChanged();
        }
        currentSort = comparator;
        sortedRecipes.sort(comparator);
        updateRecipeList();
        isRecipeListDirty = true;
    }

    /**
     * SERVER ONLY
     */
    public void prevRecipe() {
        if (currentRecipeList != null) {
            currentRecipeIdx--;
            if (currentRecipeIdx < 0) {
                currentRecipeIdx = currentRecipeList.size() - 1;
            }
            currentRecipe = currentRecipeList.get(currentRecipeIdx);
            setCraftMatrix(currentRecipe);
            isSelectionDirty = true;
        }
    }

    /**
     * SERVER ONLY
     */
    public void nextRecipe() {
        if (currentRecipeList != null) {
            currentRecipeIdx++;
            if (currentRecipeIdx >= currentRecipeList.size()) {
                currentRecipeIdx = 0;
            }
            currentRecipe = currentRecipeList.get(currentRecipeIdx);
            setCraftMatrix(currentRecipe);
            isSelectionDirty = true;
        }
    }

    /**
     * SERVER ONLY
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (!isClientSide) {
            if (player.inventory.inventoryChanged) {
                findAvailableRecipes();
                sortRecipes(currentSort);
                player.inventory.inventoryChanged = false;
            }

            if (isSelectionDirty) {
                isSelectionDirty = false;
                if (currentRecipe != null && !currentRecipe.isSmeltingRecipe()) {
                    craftBook.prepareRecipe(player, currentRecipe);
                    isMissingTools = !craftBook.matches(player.worldObj);
                } else {
                    isMissingTools = false;
                }
                hasVariants = currentRecipeList != null && currentRecipeList.size() > 1;
                isMissingOven = kitchenMultiBlock == null || !kitchenMultiBlock.hasSmeltingProvider();
                NetworkHandler.instance.sendTo(
                        new MessageRecipeInfo(
                                currentSlotIndex, currentRecipe, isMissingTools, hasVariants, isMissingOven),
                        (EntityPlayerMP) player);
            }

            if (isRecipeListDirty) {
                NetworkHandler.instance.sendTo(
                        new MessageSyncList(sortedRecipes, availableRecipes), (EntityPlayerMP) player);
                isRecipeListDirty = false;
            }

            for (SlotCraftMatrix previewSlot : craftMatrixSlots) {
                previewSlot.update();
            }
        }
    }

    public ContainerRecipeBook allowCrafting() {
        this.allowCrafting = true;
        return this;
    }

    public ContainerRecipeBook allowSmelting() {
        this.allowSmelting = true;
        return this;
    }

    /**
     * SERVER ONLY
     * @return
     */
    public ContainerRecipeBook setNoFilter() {
        this.noFilter = true;
        for (SlotCraftMatrix slotCraftMatrix : craftMatrixSlots) {
            slotCraftMatrix.setNoFilter(true);
        }
        findAvailableRecipes();
        sortRecipes(currentSort);
        return this;
    }

    /**
     * SERVER ONLY
     * @param kitchenMultiBlock
     */
    public ContainerRecipeBook setKitchenMultiBlock(KitchenMultiBlock kitchenMultiBlock) {
        this.kitchenMultiBlock = kitchenMultiBlock;
        findAvailableRecipes();
        sortRecipes(currentSort);
        List<IInventory> sourceInventories = kitchenMultiBlock.getSourceInventories(player.inventory);
        for (SlotCraftMatrix craftMatrixSlot : craftMatrixSlots) {
            craftMatrixSlot.setSourceInventories(sourceInventories);
            craftMatrixSlot.setItemProviders(kitchenMultiBlock.getItemProviders());
        }
        craftBook.setInventories(sourceInventories);
        craftBook.setItemProviders(kitchenMultiBlock.getItemProviders());
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void setSelectedRecipe(
            int currentSlotIndex,
            FoodRecipe currentRecipe,
            boolean hasVariants,
            boolean isMissingTools,
            boolean isMissingOven) {
        this.currentSlotIndex = currentSlotIndex;
        this.syncSlotIndex = currentSlotIndex;
        this.currentRecipe = currentRecipe;
        this.hasVariants = hasVariants;
        this.isMissingTools = isMissingTools;
        this.isMissingOven = isMissingOven;
        setCraftMatrix(currentRecipe);
    }

    public boolean isMissingOven() {
        return isMissingOven;
    }

    public void sortingChanged() {
        currentSlotIndex = -1;
        syncSlotIndex = -1;
    }
}
