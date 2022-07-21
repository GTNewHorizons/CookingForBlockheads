package net.blay09.mods.cookingforblockheads.client.model; // Cubik Studio 2.9.482 Beta JAVA exporter
// Designed by Blay09 with Cubik Studio - https://cubik.studio

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCabinetCorner extends ModelBase {

    // fields
    public ModelRenderer Bottom;
    public ModelRenderer Right;
    public ModelRenderer BackLeft;
    public ModelRenderer BackRight;
    public ModelRenderer BottomStick;
    public ModelRenderer RightInset;
    public ModelRenderer Top;
    public ModelRenderer TopTwo;
    public ModelRenderer Left;
    public ModelRenderer BottomStickTwo;
    public ModelRenderer BottomTwo;
    public ModelRenderer LeftInset;

    public ModelCabinetCorner() {
        textureWidth = 64;
        textureHeight = 128;
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.BackRight = new ModelRenderer(this, 86, 50);
        this.BackRight.setRotationPoint(-7.0F, 11.0F, 7.0F);
        this.BackRight.addBox(0.0F, 0.0F, 0.0F, 14, 13, 1, 0.0F);
        this.Top = new ModelRenderer(this, 32, 49);
        this.Top.setRotationPoint(-7.0F, 10.0F, -5.0F);
        this.Top.addBox(0.0F, 0.0F, 0.0F, 14, 1, 13, 0.0F);
        this.BottomStickTwo = new ModelRenderer(this, 72, 33);
        this.BottomStickTwo.setRotationPoint(-3.5F, 22.0F, -7.0F);
        this.BottomStickTwo.addBox(-1.5F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.RightInset = new ModelRenderer(this, 122, 52);
        this.RightInset.setRotationPoint(-7.0F, 11.0F, -5.0F);
        this.RightInset.addBox(0.0F, 0.0F, 0.0F, 2, 11, 1, 0.0F);
        this.TopTwo = new ModelRenderer(this, 0, 4);
        this.TopTwo.setRotationPoint(-5.0F, 10.0F, -7.0F);
        this.TopTwo.addBox(0.0F, 0.0F, 0.0F, 12, 2, 2, 0.0F);
        this.Right = new ModelRenderer(this, 0, 8);
        this.Right.setRotationPoint(-8.0F, 10.0F, -5.0F);
        this.Right.addBox(0.0F, -0.0F, 0.0F, 1, 14, 13, 0.0F);
        this.BottomTwo = new ModelRenderer(this, 0, 0);
        this.BottomTwo.setRotationPoint(-3.5F, 23.0F, -7.0F);
        this.BottomTwo.addBox(0.0F, 0.0F, 0.0F, 11, 1, 3, 0.0F);
        this.Left = new ModelRenderer(this, 32, 22);
        this.Left.setRotationPoint(10.0F, 10.0F, -8.0F);
        this.Left.addBox(-15.0F, 0.0F, 0.0F, 13, 14, 1, 0.0F);
        this.LeftInset = new ModelRenderer(this, 116, 51);
        this.LeftInset.setRotationPoint(-5.0F, 11.0F, -7.0F);
        this.LeftInset.addBox(0.0F, 0.0F, 0.0F, 1, 11, 2, 0.0F);
        this.BottomStick = new ModelRenderer(this, 60, 33);
        this.BottomStick.setRotationPoint(-7.0F, 22.0F, -5.0F);
        this.BottomStick.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
        this.BackLeft = new ModelRenderer(this, 0, 35);
        this.BackLeft.setRotationPoint(7.0F, 10.0F, -7.0F);
        this.BackLeft.addBox(0.0F, 0.0F, 0.0F, 1, 14, 15, 0.0F);
        this.Bottom = new ModelRenderer(this, 32, 37);
        this.Bottom.setRotationPoint(-7.0F, 23.0F, -4.0F);
        this.Bottom.addBox(0.0F, 0.0F, 0.0F, 14, 1, 11, 0.0F);
    }

    public void renderUncolored() {}

    public void renderColored() {
        float f5 = 0.0625f;

        // Base
        Bottom.render(f5);
        Right.render(f5);
        BackLeft.render(f5);
        BackRight.render(f5);
        BottomStick.render(f5);
        RightInset.render(f5);
        Top.render(f5);
        TopTwo.render(f5);
        Left.render(f5);
        BottomStickTwo.render(f5);
        BottomTwo.render(f5);
        LeftInset.render(f5);
    }
}
