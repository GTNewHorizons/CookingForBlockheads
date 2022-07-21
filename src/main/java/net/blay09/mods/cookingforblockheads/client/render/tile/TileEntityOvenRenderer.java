package net.blay09.mods.cookingforblockheads.client.render.tile;

import net.blay09.mods.cookingforblockheads.CookingConfig;
import net.blay09.mods.cookingforblockheads.client.model.ModelOven;
import net.blay09.mods.cookingforblockheads.client.render.RenderUtils;
import net.blay09.mods.cookingforblockheads.tile.TileOven;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityOvenRenderer extends TileEntitySpecialRenderer {

    private static final ResourceLocation texture =
            new ResourceLocation("cookingforblockheads", "textures/entity/ModelOven.png");

    private ModelOven model = new ModelOven();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float delta) {
        TileOven tileEntityOven = (TileOven) tileEntity;
        int metadata = 0;
        if (tileEntity.hasWorldObj()) {
            metadata = tileEntity.getBlockMetadata();
        }
        GL11.glPushMatrix();
        boolean oldRescaleNormal = GL11.glIsEnabled(GL12.GL_RESCALE_NORMAL);
        if (oldRescaleNormal) {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glTranslatef((float) x, (float) y + 1f, (float) z);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        float angle = RenderUtils.getAngle(metadata);

        GL11.glRotatef(angle, 0f, 1f, 0f);
        GL11.glRotatef(180f, 0f, 0f, 1f);
        bindTexture(texture);
        float doorAngle = tileEntityOven.getPrevDoorAngle()
                + (tileEntityOven.getDoorAngle() - tileEntityOven.getPrevDoorAngle()) * delta;
        doorAngle = 1.0f - doorAngle;
        doorAngle = 1.0f - doorAngle * doorAngle * doorAngle;
        model.OvenDoor.rotateAngleX = (float) ((Math.PI / 2.5f) * doorAngle);
        model.OvenDoorBurning.rotateAngleX = (float) ((Math.PI / 2.5f) * doorAngle);
        model.renderAll(doorAngle <= 0.25f && tileEntityOven.isBurning());
        GL11.glRotatef(180f, 0f, 0f, -1f);
        if (doorAngle > 0f) {
            if (!CookingConfig.disableItemRender && Minecraft.getMinecraft().gameSettings.fancyGraphics) {
                RenderItem.renderInFrame = true;
                for (int i = 0; i < 9; i++) {
                    ItemStack itemStack = tileEntityOven.getStackInSlot(i + 7);
                    if (itemStack != null) {
                        int relSlot = i % 3;
                        float itemX = relSlot * 0.5f;
                        float itemZ = -(i / 3) * 0.4f;
                        GL11.glPushMatrix();
                        GL11.glScalef(0.5f, 0.5f, 0.5f);
                        GL11.glTranslatef(itemX - 0.5f, -2.25f, 0.05f + itemZ);
                        GL11.glRotatef(90f, 1f, 0f, 0f);
                        tileEntityOven.getInteriorRenderItem().setEntityItemStack(itemStack);
                        RenderManager.instance.renderEntityWithPosYaw(
                                tileEntityOven.getInteriorRenderItem(), 0, 0, 0, 0f, 0f);
                        GL11.glPopMatrix();
                    }
                }
                RenderItem.renderInFrame = false;
            }
        }
        final float scale = 0.7f;
        if (tileEntityOven.getStackInSlot(16) != null) {
            RenderItem.renderInFrame = true;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.2f, -0.55f, 0.1f);
            GL11.glRotatef(45f, 1f, 0f, 0f);
            GL11.glScalef(scale, scale, scale);
            RenderManager.instance.renderEntityWithPosYaw(tileEntityOven.getRenderItem(0), 0, 0, 0, 0f, 0f);
            GL11.glPopMatrix();
            RenderItem.renderInFrame = false;
        }
        if (tileEntityOven.getStackInSlot(17) != null) {
            RenderItem.renderInFrame = true;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.2f, -0.55f, 0.1f);
            GL11.glRotatef(45f, 1f, 0f, 0f);
            GL11.glScalef(scale, scale, scale);
            RenderManager.instance.renderEntityWithPosYaw(tileEntityOven.getRenderItem(1), 0, 0, 0, 0f, 0f);
            GL11.glPopMatrix();
            RenderItem.renderInFrame = false;
        }
        if (tileEntityOven.getStackInSlot(18) != null) {
            RenderItem.renderInFrame = true;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.2f, -0.55f, -0.35f);
            GL11.glRotatef(45f, 1f, 0f, 0f);
            GL11.glScalef(scale, scale, scale);
            RenderManager.instance.renderEntityWithPosYaw(tileEntityOven.getRenderItem(2), 0, 0, 0, 0f, 0f);
            GL11.glPopMatrix();
            RenderItem.renderInFrame = false;
        }
        if (tileEntityOven.getStackInSlot(19) != null) {
            RenderItem.renderInFrame = true;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.2f, -0.55f, -0.35f);
            GL11.glRotatef(45f, 1f, 0f, 0f);
            GL11.glScalef(scale, scale, scale);
            RenderManager.instance.renderEntityWithPosYaw(tileEntityOven.getRenderItem(3), 0, 0, 0, 0f, 0f);
            GL11.glPopMatrix();
            RenderItem.renderInFrame = false;
        }
        if (!oldRescaleNormal) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
