package net.blay09.mods.cookingforblockheads.tile;

import java.util.List;
import java.util.Random;

import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenStorageProvider;
import net.blay09.mods.cookingforblockheads.container.ContainerWithInventory;
import net.blay09.mods.cookingforblockheads.container.inventory.InventoryLarge;
import net.blay09.mods.cookingforblockheads.container.inventory.InventoryNormal;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class BaseKitchenTileWithInventory extends TileEntity implements IInventory, IKitchenStorageProvider {

    protected static final Random random = new Random();
    private final String inventoryName;
    protected InventoryNormal internalInventory;
    protected IInventory sharedInventory;
    protected int color;
    protected boolean isFlipped;
    protected float prevDoorAngle;
    protected float doorAngle;
    protected int numPlayersUsing;
    protected int tickCounter;
    private EntityItem renderItem;

    public BaseKitchenTileWithInventory(String inventoryName) {
        this.inventoryName = inventoryName;
        internalInventory = new InventoryNormal(this.inventoryName);
        sharedInventory = internalInventory;
    }

    @Override
    public void setWorldObj(World world) {
        super.setWorldObj(world);

        renderItem = new EntityItem(world, 0, 0, 0);
        renderItem.hoverStart = 0f;
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == 1) {
            numPlayersUsing = value;
            return true;
        } else if (id == 2) {
            color = value;
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        internalInventory = new InventoryNormal(this.inventoryName);
        NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemCompound = tagList.getCompoundTagAt(i);
            internalInventory.setInventorySlotContents(
                    itemCompound.getByte("Slot"),
                    ItemStack.loadItemStackFromNBT(itemCompound));
        }
        color = tagCompound.getByte("Color");
        isFlipped = tagCompound.getBoolean("IsFlipped");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < internalInventory.getSizeInventory(); i++) {
            ItemStack itemStack = internalInventory.getStackInSlot(i);
            if (itemStack != null) {
                NBTTagCompound itemCompound = new NBTTagCompound();
                itemCompound.setByte("Slot", (byte) i);
                itemStack.writeToNBT(itemCompound);
                tagList.appendTag(itemCompound);
            }
        }
        tagCompound.setTag("Items", tagList);
        tagCompound.setByte("Color", (byte) color);
        tagCompound.setBoolean("IsFlipped", isFlipped);
    }

    protected void fixBrokenContainerClosedCall() {
        // Because Mojang people thought it would be more sane to check chest watchers every few ticks instead of fixing
        // the actual issue.
        if (!worldObj.isRemote && numPlayersUsing != 0 && (tickCounter + xCoord + yCoord + zCoord) % 200 == 0) {
            numPlayersUsing = 0;
            float range = 5.0F;
            List<EntityPlayer> players = (List<EntityPlayer>) worldObj.getEntitiesWithinAABB(
                    EntityPlayer.class,
                    AxisAlignedBB.getBoundingBox(
                            (float) xCoord - range,
                            (float) yCoord - range,
                            (float) zCoord - range,
                            (float) xCoord + 1 + range,
                            (float) yCoord + 1 + range,
                            (float) zCoord + 1 + range));
            for (EntityPlayer entityPlayer : players) {
                if (entityPlayer.openContainer instanceof ContainerWithInventory) {
                    IInventory inventory = ((ContainerWithInventory) entityPlayer.openContainer)
                            .getContainerInventory();
                    if (inventory == this || (inventory instanceof InventoryLarge
                            && ((InventoryLarge) inventory).containsInventory(this))) {
                        numPlayersUsing++;
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        tickCounter++;

        fixBrokenContainerClosedCall();

        prevDoorAngle = doorAngle;
        if (numPlayersUsing > 0) {
            final float doorSpeed = 0.2f;
            doorAngle = Math.min(1f, doorAngle + doorSpeed);
        } else {
            final float doorSpeed = 0.1f;
            doorAngle = Math.max(0f, doorAngle - doorSpeed);
        }
    }

    public boolean hasInventory() {
        return true;
    }

    @Override
    public void openInventory() {
        if (!hasInventory()) return;
        numPlayersUsing++;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numPlayersUsing);
    }

    @Override
    public void closeInventory() {
        if (!hasInventory()) return;
        numPlayersUsing--;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numPlayersUsing);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return hasInventory();
    }

    @Override
    public int getSizeInventory() {
        return hasInventory() ? sharedInventory.getSizeInventory() : 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return hasInventory() ? sharedInventory.getStackInSlot(i) : null;
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
        return hasInventory() ? sharedInventory.decrStackSize(i, amount) : null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return hasInventory() ? sharedInventory.getStackInSlotOnClosing(i) : null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        if (!hasInventory()) return;
        sharedInventory.setInventorySlotContents(i, itemStack);
    }

    @Override
    public String getInventoryName() {
        return hasInventory() ? sharedInventory.getInventoryName() : "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return hasInventory() && sharedInventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return hasInventory() ? sharedInventory.getInventoryStackLimit() : 0;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (hasWorldObj()) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public EntityItem getRenderItem() {
        return renderItem;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tagCompound = pkt.func_148857_g();
        readFromNBT(tagCompound);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tagCompound);
    }

    public void breakBlock() {
        for (int i = 0; i < internalInventory.getSizeInventory(); i++) {
            ItemStack itemStack = internalInventory.getStackInSlot(i);
            if (itemStack != null) {
                float offsetX = random.nextFloat() * 0.8f + 0.1f;
                float offsetY = random.nextFloat() * 0.8f + 0.1f;
                EntityItem entityItem;
                for (float offsetZ = random.nextFloat() * 0.8f + 0.1f; itemStack.stackSize > 0; worldObj
                        .spawnEntityInWorld(entityItem)) {
                    int stackSize = random.nextInt(21) + 10;

                    if (stackSize > itemStack.stackSize) {
                        stackSize = itemStack.stackSize;
                    }

                    itemStack.stackSize -= stackSize;
                    entityItem = new EntityItem(
                            worldObj,
                            (double) ((float) xCoord + offsetX),
                            (double) ((float) yCoord + offsetY),
                            (double) ((float) zCoord + offsetZ),
                            new ItemStack(itemStack.getItem(), stackSize, itemStack.getItemDamage()));
                    float f3 = 0.05F;
                    entityItem.motionX = (double) ((float) random.nextGaussian() * f3);
                    entityItem.motionY = (double) ((float) random.nextGaussian() * f3 + 0.2F);
                    entityItem.motionZ = (double) ((float) random.nextGaussian() * f3);

                    if (itemStack.hasTagCompound()) {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                    }
                }
            }
        }
    }

    public void setColor(int color) {
        this.color = color;
        markDirty();
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 2, color);
    }

    public int getColor() {
        return color;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return hasInventory();
    }

    public float getDoorAngle() {
        return doorAngle;
    }

    public float getPrevDoorAngle() {
        return prevDoorAngle;
    }

    @Override
    public IInventory getInventory() {
        return this;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean isFlipped) {
        this.isFlipped = isFlipped;
    }

    public InventoryNormal getInternalInventory() {
        return internalInventory;
    }
}
