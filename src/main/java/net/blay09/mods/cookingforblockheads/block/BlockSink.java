package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.CookingConfig;
import net.blay09.mods.cookingforblockheads.client.render.block.SinkBlockRenderer;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.tile.TileSink;
import net.blay09.mods.cookingforblockheads.utils.DyeUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class BlockSink extends BlockBaseKitchen {

    public BlockSink() {
        super(Material.wood);

        setBlockName("cookingforblockheads:sink");
        setStepSound(soundTypeWood);
        setHardness(5f);
        setResistance(10f);
        setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.975f, 0.9375f);
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        findOrientation(worldIn, x, y, z);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return SinkBlockRenderer.RENDER_ID;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return Blocks.log.getIcon(side, 1);
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        TileSink sink = (TileSink) world.getTileEntity(x, y, z);
        if (sink.getColor() == colour) {
            return false;
        }
        sink.setColor(colour);
        return true;
    }

    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem();
        TileSink sink = (TileSink) world.getTileEntity(x, y, z);

        if(heldItem != null && DyeUtils.isDye(heldItem)) {
            Optional<Integer> dyeColor = DyeUtils.colorFromStack(heldItem);
            if (dyeColor.isPresent() && recolourBlock(world, x, y, z, ForgeDirection.UNKNOWN, dyeColor.get())) {
                player.getHeldItem().stackSize--;
                return true;
            }
        }
        if (FluidContainerRegistry.isEmptyContainer(player.getHeldItem())) {
            FluidStack fluidStack = null;
            int amount = FluidContainerRegistry.getContainerCapacity(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), player.getHeldItem());
            if(CookingConfig.sinkRequiresWater) {
                if(sink.getWaterAmount() >= amount) {
                    fluidStack = sink.drain(ForgeDirection.UNKNOWN, amount, true);
                }
            } else {
                fluidStack = new FluidStack(FluidRegistry.WATER, amount);
            }
            if(fluidStack != null && fluidStack.amount >= amount) {
                ItemStack filledContainer = FluidContainerRegistry.fillFluidContainer(fluidStack, player.getHeldItem());
                if (filledContainer != null) {
                    if (player.getHeldItem().stackSize <= 1) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, filledContainer);
                    } else {
                        if (player.inventory.addItemStackToInventory(filledContainer)) {
                            player.getHeldItem().stackSize--;
                        }
                    }
                }
                spawnParticles(world, x, y, z);
            }
            return true;
        } else if(FluidContainerRegistry.isFilledContainer(player.getHeldItem())) {
            FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(player.getHeldItem());
            if(CookingConfig.sinkRequiresWater) {
                sink.fill(ForgeDirection.UNKNOWN, fluidStack, true);
            }
            ItemStack emptyContainer = FluidContainerRegistry.drainFluidContainer(player.getHeldItem());
            if(emptyContainer != null) {
                if(player.getHeldItem().stackSize <= 1) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, emptyContainer);
                } else {
                    if(player.inventory.addItemStackToInventory(emptyContainer)) {
                        player.getHeldItem().stackSize--;
                    }
                }
            }
            spawnParticles(world, x, y, z);
            return true;
        } else {
            ItemStack resultStack = CookingRegistry.getSinkOutput(player.getHeldItem());
            if(resultStack != null) {
                ItemStack oldItem = player.getHeldItem();
                NBTTagCompound tagCompound = oldItem.getTagCompound();
                ItemStack newItem = resultStack.copy();
                newItem.setTagCompound(tagCompound);
                if(oldItem.stackSize <= 1) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, newItem);
                } else {
                    if(player.inventory.addItemStackToInventory(newItem)) {
                        oldItem.stackSize--;
                    }
                }
                spawnParticles(world, x, y, z);
                return true;
            } else {
                if(CookingConfig.sinkRequiresWater) {
                    if(sink.getWaterAmount() < FluidContainerRegistry.BUCKET_VOLUME) {
                        return false;
                    }
                }
                spawnParticles(world, x, y, z);
            }
        }
        return false;
    }

    public void spawnParticles(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        float dripWaterX = 0f;
        float dripWaterZ = 0f;
        switch(metadata) {
            case 2: dripWaterZ = 0.25f; dripWaterX = -0.05f; break;
            case 3: dripWaterX = 0.25f; break;
            case 4: dripWaterX = 0.25f; dripWaterZ = 0.25f; break;
            case 5: dripWaterZ = -0.05f; break;
        }
        float particleX = (float) x + 0.5f;
        float particleY = (float) y + 1.25f;
        float particleZ = (float) z + 0.5f;
        world.spawnParticle("dripWater", (double) particleX + dripWaterX, (double) particleY - 0.45f, (double) particleZ + dripWaterZ, 0, 0, 0);
        for(int i = 0; i < 5; i++) {
            world.spawnParticle("splash", (double) particleX + Math.random() - 0.5f, (double) particleY + Math.random() - 0.5f, (double) particleZ + Math.random() - 0.5f, 0, 0, 0);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemStack) {
        int l = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileSink();
    }

}
