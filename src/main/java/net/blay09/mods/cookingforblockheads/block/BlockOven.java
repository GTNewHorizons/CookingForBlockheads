package net.blay09.mods.cookingforblockheads.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.GuiHandler;
import net.blay09.mods.cookingforblockheads.client.render.block.OvenBlockRenderer;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.tile.TileOven;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockOven extends BlockBaseKitchen {

    private final Random random = new Random();

    public BlockOven() {
        super(Material.iron);

        setBlockName("cookingforblockheads:cookingoven");
        setStepSound(soundTypeMetal);
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
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return OvenBlockRenderer.RENDER_ID;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {}

    @Override
    public IIcon getIcon(int side, int metadata) {
        return Blocks.iron_block.getIcon(side, 0);
    }

    @Override
    public boolean onBlockActivated(
            World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (side == ForgeDirection.UP.ordinal()) {
            if (CookingRegistry.isToolItem(player.getHeldItem())) {
                int metadata = world.getBlockMetadata(x, y, z);
                float hitX = subX;
                float hitZ = subZ;
                switch (metadata) {
                    case 2:
                        hitX = 1f - subX;
                        hitZ = 1f - subZ;
                        break;
                    case 3:
                        hitX = subX;
                        hitZ = subZ;
                        break;
                    case 4:
                        hitZ = 1f - subX;
                        hitX = subZ;
                        break;
                    case 5:
                        hitZ = subX;
                        hitX = 1f - subZ;
                        break;
                }
                int slotId = -1;
                if (hitX < 0.5f && hitZ < 0.5f) {
                    slotId = 1;
                } else if (hitX >= 0.5f && hitZ < 0.5f) {
                    slotId = 0;
                } else if (hitX < 0.5f && hitZ >= 0.5f) {
                    slotId = 2;
                } else if (hitX >= 0.5f && hitZ >= 0.5f) {
                    slotId = 3;
                }
                slotId += 16;
                TileOven tileEntityOven = (TileOven) world.getTileEntity(x, y, z);
                if (tileEntityOven.getStackInSlot(slotId) == null) {
                    ItemStack toolItem = player.getHeldItem().splitStack(1);
                    tileEntityOven.setInventorySlotContents(slotId, toolItem);
                }
                return true;
            }
        }
        if (!world.isRemote) {
            player.openGui(CookingForBlockheads.instance, GuiHandler.COOKING_OVEN, world, x, y, z);
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileOven();
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
    public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta) {
        TileOven tileEntity = (TileOven) world.getTileEntity(x, y, z);
        if (tileEntity != null) {
            for (int i1 = 0; i1 < tileEntity.getSizeInventory(); ++i1) {
                ItemStack itemstack = tileEntity.getStackInSlot(i1);
                if (itemstack != null) {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j1 = this.random.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(
                                world,
                                (double) ((float) x + f),
                                (double) ((float) y + f1),
                                (double) ((float) z + f2),
                                new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)
                                    itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
            world.func_147453_f(x, y, z, blockBroken); // updateNeighboursOfBlockChange
        }
        super.breakBlock(world, x, y, z, blockBroken, meta);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        TileOven tileEntity = (TileOven) world.getTileEntity(x, y, z);
        if (tileEntity.isBurning()) {
            int l = world.getBlockMetadata(x, y, z);
            float f = (float) x + 0.5F;
            float f1 = (float) y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float) z + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;

            if (l == 4) {
                world.spawnParticle("smoke", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 5) {
                world.spawnParticle("smoke", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 2) {
                world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
            } else if (l == 3) {
                world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
    }
}
