package net.blay09.mods.cookingforblockheads.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public abstract class TileEntityRendererBase extends TileEntitySpecialRenderer {
    public static final float[][] colorTable = new float[][] {
        {1.0F, 1.0F, 1.0F},
        {0.85F, 0.5F, 0.2F},
        {0.7F, 0.3F, 0.85F},
        {0.4F, 0.6F, 0.85F},
        {0.9F, 0.9F, 0.2F},
        {0.5F, 0.8F, 0.1F},
        {0.95F, 0.5F, 0.65F},
        {0.3F, 0.3F, 0.3F},
        {0.6F, 0.6F, 0.6F},
        {0.3F, 0.5F, 0.6F},
        {0.5F, 0.25F, 0.7F},
        {0.2F, 0.3F, 0.7F},
        {0.4F, 0.3F, 0.2F},
        {0.4F, 0.5F, 0.2F},
        {0.6F, 0.2F, 0.2F},
        {0.1F, 0.1F, 0.1F}
    };
}
