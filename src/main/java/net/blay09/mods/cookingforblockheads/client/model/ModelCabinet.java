package net.blay09.mods.cookingforblockheads.client.model;
// Designed by Blay09 with Cubik Studio - https://cubik.studio

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCabinet extends ModelBase {
    protected boolean isFlipped;

    public ModelRenderer BottomWall;
    public ModelRenderer RightWall;
    public ModelRenderer LeftWall;
    public ModelRenderer BackWall;
    public ModelRenderer BottomStick;
    public ModelRenderer InnerBoard;
    public ModelRenderer LeftStick;
    public ModelRenderer RightStick;
    public ModelRenderer TopWall;
    public ModelRenderer Door;
    public ModelRenderer DoorFlipped;
    public ModelRenderer DoorHandle;
    public ModelRenderer DoorHandleFlipped;

    public ModelCabinet() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.Door = new ModelRenderer(this, 0, 47);
        this.Door.setRotationPoint(-5.5F, 23.0F, -6.0F);
        this.Door.addBox(0.0F, -11.0F, 0.0F, 11, 11, 1, 0.0F);
        this.LeftWall = new ModelRenderer(this, 0, 59);
        this.LeftWall.setRotationPoint(7.0F, 24.0F, -5.0F);
        this.LeftWall.addBox(0.0F, -14.0F, 0.0F, 1, 14, 13, 0.0F);
        this.TopWall = new ModelRenderer(this, 0, 113);
        this.TopWall.setRotationPoint(-7.0F, 11.5F, -5.0F);
        this.TopWall.addBox(0.0F, -1.5F, 0.0F, 14, 2, 13, 0.0F);
        this.DoorFlipped = new ModelRenderer(this, 0, 47);
        this.DoorFlipped.setRotationPoint(5.5F, 23.0F, -6.0F);
        this.DoorFlipped.addBox(-11.0F, -11.0F, 0.0F, 11, 11, 1, 0.0F);
        this.BackWall = new ModelRenderer(this, 28, 87);
        this.BackWall.setRotationPoint(-7.0F, 24.0F, 6.5F);
        this.BackWall.addBox(0.0F, -13.0F, 0.0F, 14, 13, 1, 0.0F);
        this.InnerBoard = new ModelRenderer(this, 54, 115);
        this.InnerBoard.setRotationPoint(-7.0F, 18.0F, -4.9F);
        this.InnerBoard.addBox(0.0F, -1.0F, 0.0F, 14, 1, 12, 0.0F);
        this.LeftStick = new ModelRenderer(this, 112, 116);
        this.LeftStick.setRotationPoint(-7.0F, 23.0F, -5.0F);
        this.LeftStick.addBox(0.0F, -11.0F, 0.0F, 2, 11, 1, 0.0F);
        this.DoorHandle = new ModelRenderer(this, 118, 124);
        this.DoorHandle.setRotationPoint(-5.5F, 19.0F, -6.5F);
        this.DoorHandle.addBox(8.5F, -3.0F, 0.0F, 1, 3, 1, 0.0F);
        this.RightStick = new ModelRenderer(this, 106, 116);
        this.RightStick.setRotationPoint(5.5F, 23.0F, -5.0F);
        this.RightStick.addBox(0.0F, -11.0F, 0.0F, 2, 11, 1, 0.0F);
        this.DoorHandleFlipped = new ModelRenderer(this, 118, 124);
        this.DoorHandleFlipped.setRotationPoint(5.5F, 19.0F, -6.5F);
        this.DoorHandleFlipped.addBox(-9.5F, -3.0F, 0.0F, 1, 3, 1, 0.0F);
        this.RightWall = new ModelRenderer(this, 0, 86);
        this.RightWall.setRotationPoint(-8.0F, 24.0F, -5.0F);
        this.RightWall.addBox(0.0F, -14.0F, 0.0F, 1, 14, 13, 0.0F);
        this.BottomWall = new ModelRenderer(this, 28, 101);
        this.BottomWall.setRotationPoint(-7.0F, 23.5F, -4.0F);
        this.BottomWall.addBox(0.0F, -0.5F, 0.0F, 14, 1, 11, 0.0F);
        this.BottomStick = new ModelRenderer(this, 78, 109);
        this.BottomStick.setRotationPoint(-7.0F, 23.5F, -5.0F);
        this.BottomStick.addBox(0.0F, -1.5F, 0.0F, 14, 2, 2, 0.0F);
    }

    public void renderUncolored() {
        float f5 = 0.0625f;
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
        this.TopWall.render(f5);
        this.RightWall.render(f5);
        this.LeftWall.render(f5);
        this.BackWall.render(f5);
        this.BottomStick.render(f5);
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
