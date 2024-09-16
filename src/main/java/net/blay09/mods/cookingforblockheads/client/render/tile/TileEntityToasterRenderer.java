package net.blay09.mods.cookingforblockheads.client.render.tile;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.client.model.ModelToaster;
import net.blay09.mods.cookingforblockheads.client.render.RenderUtils;
import net.blay09.mods.cookingforblockheads.tile.TileToaster;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityToasterRenderer extends TileEntitySpecialRenderer {

    private static final ResourceLocation texture = new ResourceLocation(
            CookingForBlockheads.MOD_ID,
            "textures/entity/ModelToaster.png");

    private ModelToaster model = new ModelToaster();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float delta) {
        GL11.glPushMatrix();
        int metadata = 0;
        boolean counterBelow = false;
        if (tileEntity.hasWorldObj()) {
            metadata = tileEntity.getBlockMetadata();
            counterBelow = tileEntity.getWorldObj()
                    .getBlock(tileEntity.xCoord, tileEntity.yCoord - 1, tileEntity.zCoord)
                    == CookingForBlockheads.blockCounter
                    || tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord - 1, tileEntity.zCoord)
                            == CookingForBlockheads.blockCounterCorner;
        } else {
            GL11.glScalef(2f, 2f, 2f);
            GL11.glTranslatef(0, 0.25f, 0);
        }
        TileToaster tileToaster = (TileToaster) tileEntity;
        boolean oldRescaleNormal = GL11.glIsEnabled(GL12.GL_RESCALE_NORMAL);
        if (oldRescaleNormal) {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glTranslatef(0.5f, 0.065f, 0.5f);
        if (counterBelow) {
            GL11.glTranslatef(0f, -0.0625F, 0f);
        }
        float angle = RenderUtils.getAngle(metadata);
        GL11.glRotatef(angle, 0f, 1f, 0f);
        GL11.glRotatef(180f, 0f, 0f, 1f);
        bindTexture(texture);
        model.renderAll();
        GL11.glRotatef(180f, 0f, 0f, 1f);

        if (tileToaster.isActive()) {
            model.ToasterButtonThingy.offsetY = 0.17f;
        } else {
            model.ToasterButtonThingy.offsetY = 0;
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0575f, 0.025f - (tileToaster.isActive() ? 0.08f : 0f), -0.05f);
        GL11.glScalef(0.75f, 0.75f, 0.75f);
        GL11.glRotatef(90f, 0f, 1f, 0f);
        if (tileToaster.getStackInSlot(0) != null) {
            RenderManager.instance.renderEntityWithPosYaw(tileToaster.getRenderItem(0), 0d, 0d, 0d, 0f, 0f);
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(-0.0625f, 0.025f - (tileToaster.isActive() ? 0.08f : 0f), -0.05f);
        GL11.glScalef(0.75f, 0.75f, 0.75f);
        GL11.glRotatef(90f, 0f, 1f, 0f);
        if (tileToaster.getStackInSlot(1) != null) {
            RenderManager.instance.renderEntityWithPosYaw(tileToaster.getRenderItem(1), 0, 0, 0, 0f, 0f);
        }
        GL11.glPopMatrix();
        if (!oldRescaleNormal) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
