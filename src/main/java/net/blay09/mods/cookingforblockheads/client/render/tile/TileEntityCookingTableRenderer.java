package net.blay09.mods.cookingforblockheads.client.render.tile;

import net.blay09.mods.cookingforblockheads.client.model.ModelCookingTable;
import net.blay09.mods.cookingforblockheads.client.render.RenderUtils;
import net.blay09.mods.cookingforblockheads.tile.TileCookingTable;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityCookingTableRenderer extends TileEntityRendererBase {

    private static final ResourceLocation texture =
            new ResourceLocation("cookingforblockheads", "textures/entity/ModelCookingTable.png");

    private ModelCookingTable model = new ModelCookingTable();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float delta) {
        int metadata = 0;
        TileCookingTable tileCookingTable = (TileCookingTable) tileEntity;
        final int dye = tileCookingTable.getColor();
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

        model.renderUncolored();
        GL11.glColor4f(colorTable[dye][0], colorTable[dye][1], colorTable[dye][2], 1f);
        model.renderColored();

        if (!oldRescaleNormal) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
        TileCookingTable tileEntityTable = (TileCookingTable) tileEntity;
        if (tileEntityTable.hasNoFilterBook()) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5f, (float) y + 0.9f, (float) z + 0.5f);
            GL11.glRotatef(angle, 0f, 1f, 0f);
            GL11.glTranslatef(0f, 0f, -0.2f);
            GL11.glRotatef(90f, 1f, 0f, 0f);
            RenderManager.instance.renderEntityWithPosYaw(tileEntityTable.getRenderItem(), 0, 0, 0, 0f, 0f);
            GL11.glPopMatrix();
        }
    }
}
