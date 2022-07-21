package net.blay09.mods.cookingforblockheads.client.render.tile;

import net.blay09.mods.cookingforblockheads.CookingConfig;
import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.client.model.ModelBaseFridge;
import net.blay09.mods.cookingforblockheads.client.model.ModelFridge;
import net.blay09.mods.cookingforblockheads.client.model.ModelSmallFridge;
import net.blay09.mods.cookingforblockheads.client.render.RenderUtils;
import net.blay09.mods.cookingforblockheads.tile.TileFridge;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityFridgeRenderer extends TileEntityRendererBase {

    private static final ResourceLocation textureSmall =
            new ResourceLocation("cookingforblockheads", "textures/entity/ModelSmallFridge.png");
    private static final ResourceLocation textureBig =
            new ResourceLocation("cookingforblockheads", "textures/entity/ModelFridge.png");

    private ModelFridge modelBig = new ModelFridge();
    private ModelSmallFridge modelSmall = new ModelSmallFridge();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float delta) {
        int metadata = 0;
        boolean isLargeFridge = false;
        TileFridge tileFridge = (TileFridge) tileEntity;
        final boolean isDoorFlipped = tileFridge.isFlipped();
        final int dye = tileFridge.getColor();
        if (tileEntity.hasWorldObj()) {
            metadata = tileEntity.getBlockMetadata();
            final Block above =
                    tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord + 1, tileEntity.zCoord);
            if (above == CookingForBlockheads.blockFridge) {
                isLargeFridge = true;
            }
            final Block below =
                    tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord - 1, tileEntity.zCoord);
            if (below == CookingForBlockheads.blockFridge) {
                return;
            }
        }

        GL11.glPushMatrix();
        boolean oldRescaleNormal = GL11.glIsEnabled(GL12.GL_RESCALE_NORMAL);
        if (oldRescaleNormal) {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glTranslatef((float) x, (float) y + 1f, (float) z);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        float angle = RenderUtils.getAngle(metadata);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glRotatef(angle, 0f, 1f, 0f);
        GL11.glRotatef(180f, 0f, 0f, 1f);
        float doorAngle =
                tileFridge.getPrevDoorAngle() + (tileFridge.getDoorAngle() - tileFridge.getPrevDoorAngle()) * delta;
        doorAngle = 1.0f - doorAngle;
        doorAngle = 1.0f - doorAngle * doorAngle * doorAngle;

        ModelBaseFridge model;
        if (isLargeFridge) {
            model = modelBig;
            TileFridge upperFridge = tileFridge.findNeighbourFridge();
            if (upperFridge != null) {
                float neighbourDoorAngle = upperFridge.getPrevDoorAngle()
                        + (upperFridge.getDoorAngle() - upperFridge.getPrevDoorAngle()) * delta;
                if (neighbourDoorAngle > doorAngle) {
                    doorAngle = neighbourDoorAngle;
                }
            }
            bindTexture(textureBig);

        } else {
            model = modelSmall;
            bindTexture(textureSmall);
        }

        model.setFlipped(isDoorFlipped);
        if (isDoorFlipped) {
            model.DoorFlipped.rotateAngleY = -(float) ((Math.PI / 2f) * doorAngle);
            model.DoorHandleFlipped.rotateAngleY = -(float) ((Math.PI / 2f) * doorAngle);
        } else {
            model.Door.rotateAngleY = (float) ((Math.PI / 2f) * doorAngle);
            model.DoorHandle.rotateAngleY = (float) ((Math.PI / 2f) * doorAngle);
        }

        model.renderUncolored();
        GL11.glColor4f(colorTable[dye][0], colorTable[dye][1], colorTable[dye][2], 1f);
        model.renderColored();

        if (doorAngle > 0f) {
            GL11.glColor4f(1f, 1f, 1f, 1f);
            model.renderInterior();
            GL11.glRotatef(180f, 0f, 0f, -1f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            if (!CookingConfig.disableItemRender && Minecraft.getMinecraft().gameSettings.fancyGraphics) {
                RenderItem.renderInFrame = true;
                for (int i = 0; i < tileFridge.getSizeInventory(); i++) {
                    final ItemStack itemStack = tileFridge.getStackInSlot(i);
                    int relSlot;
                    if (itemStack != null) {
                        float itemX, itemY, itemZ, partialTickTime;
                        if (isLargeFridge) {
                            int shelfCapacity = tileFridge.getSizeInventory() / 3;
                            relSlot = i % shelfCapacity;
                            itemX = Math.min(8f / 9f, (float) (relSlot % 9) / 9f);
                            if (i >= shelfCapacity * 2) {
                                itemY = -0.75f;
                            } else if (i >= shelfCapacity) {
                                itemY = 0.125f;
                            } else {
                                itemY = -1.625f;
                            }
                            itemZ = (relSlot < 9) ? 0f : -0.5f;
                            partialTickTime = 5f;
                        } else {
                            relSlot = i;
                            if (i > tileFridge.getSizeInventory() / 2) {
                                relSlot -= tileFridge.getSizeInventory() / 2;
                            }
                            itemX = (relSlot > 8)
                                    ? Math.min(4f / 5f, (relSlot - 9) / 5f)
                                    : Math.min(8f / 9f, (float) relSlot / 9f);
                            itemY = -2f + ((i > tileFridge.getSizeInventory() / 2) ? -0.7f : 0.01f);
                            itemZ = (relSlot > 8) ? -0.8f : -0.1f;
                            partialTickTime = 0f;
                        }

                        if (relSlot % 2 == 0) {
                            itemZ -= 0.1f;
                        }

                        tileFridge.getRenderItem().setEntityItemStack(itemStack);
                        RenderManager.instance.renderEntityWithPosYaw(
                                tileFridge.getRenderItem(), 0.45f - itemX, itemY, 0.5f + itemZ, 0f, partialTickTime);
                    }
                }
                RenderItem.renderInFrame = false;
            }
        }

        if (!oldRescaleNormal) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
