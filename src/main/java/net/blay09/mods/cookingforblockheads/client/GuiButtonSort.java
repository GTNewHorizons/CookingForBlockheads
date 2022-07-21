package net.blay09.mods.cookingforblockheads.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonSort extends GuiButton {

    private static final ResourceLocation guiTexture =
            new ResourceLocation("cookingforblockheads", "textures/gui/gui.png");

    private final int texCoordX;
    private final int texCoordY;
    private final int texCoordHoverX;
    private final int texCoordHoverY;
    private final int texCoordDisabledX;
    private final int texCoordDisabledY;
    private final List<String> tooltipLines = new ArrayList<String>();

    public GuiButtonSort(int buttonId, int x, int y, int texCoordX, int texCoordYOffset, String tooltipName) {
        super(buttonId, x, y, 20, 20, "");
        this.texCoordX = texCoordX;
        this.texCoordHoverX = texCoordX;
        this.texCoordDisabledX = texCoordX;
        this.texCoordY = texCoordYOffset;
        this.texCoordHoverY = 20 + texCoordYOffset;
        this.texCoordDisabledY = 40 + texCoordYOffset;
        this.tooltipLines.add(I18n.format(tooltipName));
    }

    public GuiButtonSort(int buttonId, int x, int y, int texCoordX, String tooltipName) {
        this(buttonId, x, y, texCoordX, 0, tooltipName);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.field_146123_n = mouseX >= this.xPosition
                && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width
                && mouseY < this.yPosition + this.height;

        int texX = texCoordX;
        int texY = texCoordY;
        if (!enabled) {
            texX = texCoordDisabledX;
            texY = texCoordDisabledY;
        } else if (field_146123_n) {
            texX = texCoordHoverX;
            texY = texCoordHoverY;
        }
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(guiTexture);
        drawTexturedModalRect(xPosition, yPosition, texX, texY, width, height);
    }

    public List<String> getTooltipLines() {
        return tooltipLines;
    }
}
