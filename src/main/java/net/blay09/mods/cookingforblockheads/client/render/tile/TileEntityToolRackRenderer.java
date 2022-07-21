package net.blay09.mods.cookingforblockheads.client.render.tile;

import net.blay09.mods.cookingforblockheads.client.model.ModelToolRack;
import net.blay09.mods.cookingforblockheads.client.render.RenderUtils;
import net.blay09.mods.cookingforblockheads.tile.TileToolRack;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityToolRackRenderer extends TileEntitySpecialRenderer {

    private static final ResourceLocation texture =
            new ResourceLocation("cookingforblockheads", "textures/entity/ModelToolRack-texture.png");

    private ModelToolRack model = new ModelToolRack();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float delta) {
        TileToolRack tileToolRack = (TileToolRack) tileEntity;
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
        GL11.glTranslatef((float) x, (float) y - 0.5f, (float) z);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        float angle = RenderUtils.getAngle(metadata);
        GL11.glRotatef(angle, 0f, 1f, 0f);
        bindTexture(texture);
        model.renderAll();
        if (tileToolRack.getStackInSlot(0) != null) {
            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(tileToolRack.getRenderItem(0), 0.25d, 0.3d, 0.4d, 0f, 0f);
            RenderItem.renderInFrame = false;
        }
        if (tileToolRack.getStackInSlot(1) != null) {
            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(tileToolRack.getRenderItem(1), -0.25d, 0.3d, 0.4d, 0f, 0f);
            RenderItem.renderInFrame = false;
        }
        if (!oldRescaleNormal) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
