package net.blay09.mods.cookingforblockheads.client;

import net.blay09.mods.cookingforblockheads.container.ContainerWithInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiInventory extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
    private final IInventory inventory;
    private int inventoryRows;

    public GuiInventory(ContainerWithInventory container) {
        super(container);
        this.inventory = container.getContainerInventory();
        this.inventoryRows = inventory.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }

    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(I18n.format(inventory.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(guiLeft, guiTop + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
