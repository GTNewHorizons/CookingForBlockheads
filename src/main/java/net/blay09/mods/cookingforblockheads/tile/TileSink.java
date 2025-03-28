package net.blay09.mods.cookingforblockheads.tile;

import java.util.ArrayList;
import java.util.List;

import net.blay09.mods.cookingforblockheads.CookingConfig;
import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.api.kitchen.IKitchenItemProvider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileSink extends TileEntity implements IKitchenItemProvider, IFluidHandler {

    protected int color;
    private final List<ItemStack> itemStacks = new ArrayList<>();

    private final FluidTank waterTank = new WaterTank(16000);
    private int craftingBuffer;

    public TileSink() {
        itemStacks.add(new ItemStack(Items.water_bucket));
        ItemStack pamsWater = GameRegistry.findItemStack("harvestcraft", "freshwaterItem", 1);
        if (pamsWater != null) {
            itemStacks.add(pamsWater);
        }
    }

    private static class WaterTank extends FluidTank {

        private static final FluidStack MAX_WATER = new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE);

        public WaterTank(int capacity) {
            super(capacity);
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (resource.getFluid() != FluidRegistry.WATER) {
                return 0;
            }
            return super.fill(resource, doFill);
        }

        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (!CookingConfig.sinkRequiresWater) {
                return new FluidStack(FluidRegistry.WATER, maxDrain);
            }
            return super.drain(maxDrain, doDrain);
        }

        @Override
        public int getCapacity() {
            if (!CookingConfig.sinkRequiresWater) {
                return Integer.MAX_VALUE;
            }
            return super.getCapacity();
        }

        @Override
        public int getFluidAmount() {
            if (!CookingConfig.sinkRequiresWater) {
                return Integer.MAX_VALUE;
            }

            return super.getFluidAmount();
        }

        @Override
        public FluidStack getFluid() {
            if (!CookingConfig.sinkRequiresWater) {
                return MAX_WATER;
            }

            return super.getFluid();
        }
    }

    @Override
    public List<ItemStack> getProvidedItemStacks() {
        return itemStacks;
    }

    @Override
    public boolean addToCraftingBuffer(ItemStack itemStack) {
        if (!CookingConfig.sinkRequiresWater) {
            return true;
        }
        if (waterTank.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME) {
            return false;
        }
        craftingBuffer += FluidContainerRegistry.BUCKET_VOLUME;
        return true;
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == 1) {
            waterTank.setFluid(new FluidStack(FluidRegistry.WATER, value));
            return true;
        }
        return false;
    }

    @Override
    public void clearCraftingBuffer() {
        craftingBuffer = 0;
    }

    @Override
    public void craftingComplete() {
        drain(ForgeDirection.UNKNOWN, craftingBuffer, true);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);

        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setByte("Color", (byte) color);
        waterTank.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        color = tagCompound.getByte("Color");
        waterTank.readFromNBT(tagCompound);
    }

    public int getWaterAmount() {
        return waterTank.getFluidAmount();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        // fluid type filtering is already done in the water tank fill method.
        int result = waterTank.fill(resource, doFill);
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, CookingForBlockheads.blockSink, 1, waterTank.getFluidAmount());
        return result;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (!canDrain(from, resource.getFluid())) {
            return null;
        }
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack result = waterTank.drain(maxDrain, doDrain);
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, CookingForBlockheads.blockSink, 1, waterTank.getFluidAmount());
        return result;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return CookingConfig.sinkRequiresWater && fluid == FluidRegistry.WATER;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return CookingConfig.sinkRequiresWater && fluid == FluidRegistry.WATER;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { waterTank.getInfo() };
    }

    public void setColor(int color) {
        this.color = color;
        markDirty();
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 2, color);
    }

    public int getColor() {
        return color;
    }
}
