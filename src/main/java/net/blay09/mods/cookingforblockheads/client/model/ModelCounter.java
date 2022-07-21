package net.blay09.mods.cookingforblockheads.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCounter extends ModelBase {
    protected boolean isFlipped;

    public ModelRenderer Door;
    public ModelRenderer DoorFlipped;
    public ModelRenderer DoorHandle;
    public ModelRenderer DoorHandleFlipped;
    public ModelRenderer BackSplash;
    public ModelRenderer TopWall;
    public ModelRenderer BottomWall;
    public ModelRenderer RightWall;
    public ModelRenderer LeftWall;
    public ModelRenderer BackWall;
    public ModelRenderer BottomStick;
    public ModelRenderer InnerBoard;
    public ModelRenderer TopStick;
    public ModelRenderer LeftStick;
    public ModelRenderer RightStick;

    public ModelCounter() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.LeftWall = new ModelRenderer(this, 0, 20);
        this.LeftWall.setRotationPoint(-7.0F, 24.0F, -6.5F);
        this.LeftWall.addBox(-1.0F, -14.0F, 0.0F, 1, 14, 14, 0.0F);
        this.LeftStick = new ModelRenderer(this, 54, 9);
        this.LeftStick.setRotationPoint(5.5F, 22.0F, -6.5F);
        this.LeftStick.addBox(0.0F, -10.0F, 0.0F, 2, 10, 1, 0.0F);
        this.TopStick = new ModelRenderer(this, 0, 3);
        this.TopStick.setRotationPoint(7.0F, 11.5F, -6.5F);
        this.TopStick.addBox(-14.0F, -1.5F, 0.0F, 14, 2, 1, 0.0F);
        this.DoorHandle = new ModelRenderer(this, 0, 0);
        this.DoorHandle.setRotationPoint(-5.5F, 14.0F, -8.0F);
        this.DoorHandle.addBox(9.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.RightWall = new ModelRenderer(this, 30, 20);
        this.RightWall.setRotationPoint(8.0F, 24.0F, -6.5F);
        this.RightWall.addBox(-1.0F, -14.0F, 0.0F, 1, 14, 14, 0.0F);

        this.BackSplash = new ModelRenderer(this, 62, 33);
        this.BackSplash.setRotationPoint(8.0F, 9.0F, 6.5F);
        this.BackSplash.addBox(-16.0F, -2.0F, 0.0F, 16, 2, 1, 0.0F);

        this.InnerBoard = new ModelRenderer(this, 62, 36);
        this.InnerBoard.setRotationPoint(7.0F, 17.5F, -5.7F);
        this.InnerBoard.addBox(-14.0F, -1.0F, 0.0F, 14, 1, 13, 0.0F);
        this.RightStick = new ModelRenderer(this, 116, 53);
        this.RightStick.setRotationPoint(-7.0F, 22.0F, -6.5F);
        this.RightStick.addBox(0.0F, -10.0F, 0.0F, 2, 10, 1, 0.0F);
        this.DoorFlipped = new ModelRenderer(this, 30, 9);
        this.DoorFlipped.setRotationPoint(5.5F, 21.299999237060547F, -7.5F);
        this.DoorFlipped.addBox(-11.0F, -10.0F, 0.0F, 11, 10, 1, 0.0F);
        this.BottomStick = new ModelRenderer(this, 30, 5);
        this.BottomStick.setRotationPoint(7.0F, 22.799999237060547F, -6.5F);
        this.BottomStick.addBox(-14.0F, -1.5F, 0.0F, 14, 2, 2, 0.0F);
        this.TopWall = new ModelRenderer(this, 0, 48);
        this.TopWall.setRotationPoint(8.0F, 9.0F, -7.5F);
        this.TopWall.addBox(-16.0F, 0.0F, 0.0F, 16, 1, 15, 0.0F);
        this.Door = new ModelRenderer(this, 30, 9);
        this.Door.setRotationPoint(-5.5F, 21.299999237060547F, -7.5F);
        this.Door.addBox(0.0F, -10.0F, 0.0F, 11, 10, 1, 0.0F);
        this.DoorHandleFlipped = new ModelRenderer(this, 0, 0);
        this.DoorHandleFlipped.setRotationPoint(5.5F, 14.0F, -8.0F);
        this.DoorHandleFlipped.addBox(-10.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.BackWall = new ModelRenderer(this, 0, 6);
        this.BackWall.setRotationPoint(7.0F, 23.0F, 6.5F);
        this.BackWall.addBox(-14.0F, -13.0F, 0.0F, 14, 13, 1, 0.0F);
        this.BottomWall = new ModelRenderer(this, 62, 50);
        this.BottomWall.setRotationPoint(-7.0F, 24.0F, -5.5F);
        this.BottomWall.addBox(0.0F, -1.0F, 0.0F, 14, 1, 13, 0.0F);
    }

    public void renderUncolored() {
        float f5 = 0.0625f;
        this.BackSplash.render(f5);
        this.TopWall.render(f5);
        if (isFlipped) {
            this.DoorHandleFlipped.render(f5);
        } else {
            this.DoorHandle.render(f5);
        }
    }

    public void renderColored() {
        float f5 = 0.0625f;
        if (isFlipped) {
            this.DoorFlipped.render(f5);
        } else {
            this.Door.render(f5);
        }

        this.BottomWall.render(f5);
        this.RightWall.render(f5);
        this.LeftWall.render(f5);
        this.BackWall.render(f5);
        this.BottomStick.render(f5);
        this.TopStick.render(f5);
        this.LeftStick.render(f5);
        this.RightStick.render(f5);
    }

    public void renderInterior() {
        float f5 = 0.0625f;
        this.InnerBoard.render(f5);
    }

    public void setFlipped(boolean doorFlipped) {
        this.isFlipped = doorFlipped;
    }
}
