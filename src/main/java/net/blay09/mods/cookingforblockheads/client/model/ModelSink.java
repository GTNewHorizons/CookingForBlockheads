package net.blay09.mods.cookingforblockheads.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSink extends ModelBase {

    public ModelRenderer Backsplash;
    public ModelRenderer TopRight;
    public ModelRenderer Bottom;
    public ModelRenderer WallBottom;
    public ModelRenderer Door;
    public ModelRenderer Handle;
    public ModelRenderer TopFront;
    public ModelRenderer TopLeft;
    public ModelRenderer TopBack;
    public ModelRenderer WallLeft;
    public ModelRenderer WallRight;
    public ModelRenderer WallBack;
    public ModelRenderer WallFront;
    public ModelRenderer MetalMiddle;
    public ModelRenderer FaucetBase;
    public ModelRenderer FaucetEnd;

    public ModelSink() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.MetalMiddle = new ModelRenderer(this, 34, 4);
        this.MetalMiddle.setRotationPoint(-2.0F, 10.0F, -4.5F);
        this.MetalMiddle.addBox(0.0F, 0.0F, 0.0F, 1, 6, 8, 0.0F);
        this.WallBottom = new ModelRenderer(this, 0, 43);
        this.WallBottom.setRotationPoint(8.0F, 16.0F, -6.5F);
        this.WallBottom.addBox(-16.0F, 0.0F, 0.0F, 16, 7, 14, 0.0F);
        this.WallRight = new ModelRenderer(this, 60, 44);
        this.WallRight.setRotationPoint(-8.0F, 10.0F, -6.5F);
        this.WallRight.addBox(0.0F, 0.0F, 0.0F, 3, 6, 14, 0.0F);
        this.TopLeft = new ModelRenderer(this, 94, 48);
        this.TopLeft.setRotationPoint(-6.0F, 9.0F, -7.5F);
        this.TopLeft.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 15, 0.0F);
        this.TopFront = new ModelRenderer(this, 60, 12);
        this.TopFront.setRotationPoint(-6.0F, 9.0F, -7.5F);
        this.TopFront.addBox(0.0F, 0.0F, 0.0F, 12, 1, 2, 0.0F);
        this.FaucetEnd = new ModelRenderer(this, 24, 0);
        this.FaucetEnd.setRotationPoint(1.0F, 7.0F, 2.5F);
        this.FaucetEnd.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.WallBack = new ModelRenderer(this, 60, 18);
        this.WallBack.setRotationPoint(-5.0F, 10.0F, 3.5F);
        this.WallBack.addBox(0.0F, 0.0F, 0.0F, 10, 6, 4, 0.0F);
        this.FaucetBase = new ModelRenderer(this, 24, 5);
        this.FaucetBase.setRotationPoint(1.0F, 7.0F, 3.5F);
        this.FaucetBase.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.TopBack = new ModelRenderer(this, 94, 44);
        this.TopBack.setRotationPoint(-6.0F, 9.0F, 4.5F);
        this.TopBack.addBox(0.0F, 0.0F, 0.0F, 12, 1, 3, 0.0F);
        this.WallFront = new ModelRenderer(this, 0, 1);
        this.WallFront.setRotationPoint(-5.0F, 10.0F, -6.5F);
        this.WallFront.addBox(0.0F, 0.0F, 0.0F, 10, 6, 2, 0.0F);
        this.Backsplash = new ModelRenderer(this, 60, 15);
        this.Backsplash.setRotationPoint(-8.0F, 7.0F, 6.5F);
        this.Backsplash.addBox(0.0F, 0.0F, 0.0F, 16, 2, 1, 0.0F);
        this.WallLeft = new ModelRenderer(this, 0, 9);
        this.WallLeft.setRotationPoint(5.0F, 10.0F, -6.5F);
        this.WallLeft.addBox(0.0F, 0.0F, 0.0F, 3, 6, 14, 0.0F);
        this.TopRight = new ModelRenderer(this, 60, 28);
        this.TopRight.setRotationPoint(8.0F, 9.0F, -7.5F);
        this.TopRight.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 15, 0.0F);
        this.Bottom = new ModelRenderer(this, 0, 29);
        this.Bottom.setRotationPoint(-8.0F, 24.0F, -5.5F);
        this.Bottom.addBox(0.0F, -1.0F, 0.0F, 16, 1, 13, 0.0F);
        this.Door = new ModelRenderer(this, 34, 18);
        this.Door.setRotationPoint(-5.5F, 11.8F, -7.5F);
        this.Door.addBox(0.0F, 0.0F, 0.0F, 11, 10, 1, 0.0F);
        this.Handle = new ModelRenderer(this, 24, 2);
        this.Handle.setRotationPoint(-4.5F, 16.0F, -8.0F);
        this.Handle.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
    }

    public void renderUncolored() {
        float f5 = 0.0625f;
        MetalMiddle.render(f5);
        FaucetBase.render(f5);
        FaucetEnd.render(f5);
        Handle.render(f5);
        TopFront.render(f5);
        TopLeft.render(f5);
        TopBack.render(f5);
        TopRight.render(f5);
    }

    public void renderColored() {
        float f5 = 0.0625f;
        Backsplash.render(f5);
        Bottom.render(f5);
        WallBottom.render(f5);
        Door.render(f5);

        WallLeft.render(f5);
        WallRight.render(f5);
        WallBack.render(f5);
        WallFront.render(f5);
    }
}
