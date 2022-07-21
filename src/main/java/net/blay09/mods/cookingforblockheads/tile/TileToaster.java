package net.blay09.mods.cookingforblockheads.tile;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileToaster extends TileEntity implements IInventory, IKitchenStorageProvider {

    //    private static final int TOAST_TICKS = 60 * 20;
    private static final int TOAST_TICKS = 60;
    private ItemStack[] inventory = new ItemStack[2];
    private EntityItem[] renderItems = new EntityItem[2];
    private boolean active;
    private int toastTicks;

    @Override
    public void setWorldObj(World world) {
        super.setWorldObj(world);

        for (int i = 0; i < renderItems.length; i++) {
            renderItems[i] = new EntityItem(worldObj, 0, 0, 0);
            renderItems[i].hoverStart = 0f;
            renderItems[i].setEntityItemStack(inventory[i]);
        }
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int count) {
        if (inventory[i] != null) {
            ItemStack itemStack;
            if (inventory[i].stackSize <= count) {
                itemStack = inventory[i];
                inventory[i] = null;
                markDirty();
                return itemStack;
            } else {
                itemStack = inventory[i].splitStack(count);
                if (inventory[i].stackSize == 0) {
                    inventory[i] = null;
                }
                markDirty();
                return itemStack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (inventory[i] != null) {
            ItemStack itemstack = inventory[i];
            inventory[i] = null;
            markDirty();
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        inventory[i] = itemStack;
        if (renderItems[i] != null) {
            renderItems[i].setEntityItemStack(itemStack);
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "container." + CookingForBlockheads.MOD_ID + ":toaster";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (hasWorldObj()) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        inventory = new ItemStack[getSizeInventory()];
        NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemCompound = tagList.getCompoundTagAt(i);
            setInventorySlotContents(itemCompound.getByte("Slot"), ItemStack.loadItemStackFromNBT(itemCompound));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound itemCompound = new NBTTagCompound();
                itemCompound.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(itemCompound);
                tagList.appendTag(itemCompound);
            }
        }
        tagCompound.setTag("Items", tagList);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tagCompound);
    }

    @Override
    public void updateEntity() {
        if (active) {
            toastTicks--;
            if (toastTicks <= 0) {
                for (int i = 0; i < getSizeInventory(); i++) {
                    ItemStack inputStack = getStackInSlot(i);
                    if (inputStack != null) {
                        ItemStack outputStack = CookingRegistry.getToastOutput(inputStack);
                        if (outputStack == null) {
                            outputStack = inputStack;
                        }
                        if (!worldObj.isRemote) {
                            EntityItem entityItem =
                                    new EntityItem(worldObj, xCoord + 0.5f, yCoord + 0.75f, zCoord + 0.5f, outputStack);
                            entityItem.motionX = 0f;
                            entityItem.motionY = 0.1f;
                            entityItem.motionZ = 0f;
                            if (worldObj.spawnEntityInWorld(entityItem)) {
                                setInventorySlotContents(i, null);
                            }
                        }
                    }
                }
                setActive(false);
            }
        }
    }

    public EntityItem getRenderItem(int i) {
        return renderItems[i];
    }

    @Override
    public IInventory getInventory() {
        return (IInventory) worldObj.getTileEntity(xCoord, yCoord, zCoord);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active) {
            toastTicks = TOAST_TICKS;
        } else {
            toastTicks = 0;
        }
    }
}
