package net.blay09.mods.cookingforblockheads.client.render.tile;

import net.blay09.mods.cookingforblockheads.client.model.ModelCounterCorner;
import net.blay09.mods.cookingforblockheads.client.render.RenderUtils;
import net.blay09.mods.cookingforblockheads.tile.TileCounterCorner;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityCounterCornerRenderer extends TileEntityRendererBase {

    private static final ResourceLocation textureSmall =
            new ResourceLocation("cookingforblockheads", "textures/entity/ModelCounterCorner.png");

    private ModelCounterCorner model = new ModelCounterCorner();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float delta) {
        TileCounterCorner tileCorner = (TileCounterCorner) tileEntity;
        final int dye = tileCorner.getColor();
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

        bindTexture(textureSmall);

        model.renderUncolored();
        GL11.glColor4f(colorTable[dye][0], colorTable[dye][1], colorTable[dye][2], 1f);
        model.renderColored();

        if (!oldRescaleNormal) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
