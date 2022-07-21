package net.blay09.mods.cookingforblockheads.client.model; // Cubik Studio 2.9.482 Beta JAVA exporter
// Designed by Blay09 with Cubik Studio - https://cubik.studio

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelCounterCorner extends ModelBase {

    // fields
    public ModelRenderer BackSplash;
    public ModelRenderer Top;
    public ModelRenderer Base;
    public ModelRenderer BackSplashTwo;
    public ModelRenderer WallFront;

    public ModelCounterCorner() {
        textureWidth = 64;
        textureHeight = 128;

        this.BackSplash = new ModelRenderer(this, 0, 62);
        this.BackSplash.setRotationPoint(-7.5F, 7.0F, 6.5F);
        this.BackSplash.addBox(0.0F, 0.0F, 0.0F, 16, 2, 1, 0.0F);
        this.Base = new ModelRenderer(this, 0, 99);
        this.Base.setRotationPoint(-7.5F, 10.0F, -7.0F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 14, 15, 0.0F);
        this.WallFront = new ModelRenderer(this, 32, 67);
        this.WallFront.setRotationPoint(-7.5F, 10.0F, -8.0F);
        this.WallFront.addBox(0.0F, 0.0F, 0.0F, 15, 14, 1, 0.0F);
        this.BackSplashTwo = new ModelRenderer(this, 0, 65);
        this.BackSplashTwo.setRotationPoint(-7.5F, 7.0F, -8.0F);
        this.BackSplashTwo.addBox(0.0F, 0.0F, 0.0F, 1, 2, 15, 0.0F);
        this.Top = new ModelRenderer(this, 0, 82);
        this.Top.setRotationPoint(-7.5F, 9.0F, -8.0F);
        this.Top.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
    }

    public void renderUncolored() {
        float f5 = 0.0625f;

        // Top
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Top.offsetX, this.Top.offsetY, this.Top.offsetZ);
        GL11.glTranslatef(this.Top.rotationPointX * f5, this.Top.rotationPointY * f5, this.Top.rotationPointZ * f5);
        GL11.glScaled(0.96875D, 1.0D, 0.96875D);
        GL11.glTranslatef(-this.Top.offsetX, -this.Top.offsetY, -this.Top.offsetZ);
        GL11.glTranslatef(-this.Top.rotationPointX * f5, -this.Top.rotationPointY * f5, -this.Top.rotationPointZ * f5);
        this.Top.render(f5);
        GL11.glPopMatrix();

        // Backsplash
        GL11.glPushMatrix();
        GL11.glTranslatef(this.BackSplash.offsetX, this.BackSplash.offsetY, this.BackSplash.offsetZ);
        GL11.glTranslatef(
                this.BackSplash.rotationPointX * f5,
                this.BackSplash.rotationPointY * f5,
                this.BackSplash.rotationPointZ * f5);
        GL11.glScaled(0.96875D, 1.0D, 1.0D);
        GL11.glTranslatef(-this.BackSplash.offsetX, -this.BackSplash.offsetY, -this.BackSplash.offsetZ);
        GL11.glTranslatef(
                -this.BackSplash.rotationPointX * f5,
                -this.BackSplash.rotationPointY * f5,
                -this.BackSplash.rotationPointZ * f5);
        this.BackSplash.render(f5);
        GL11.glPopMatrix();

        // Backsplash2
        GL11.glPushMatrix();
        GL11.glTranslatef(this.BackSplashTwo.offsetX, this.BackSplashTwo.offsetY, this.BackSplashTwo.offsetZ);
        GL11.glTranslatef(
                this.BackSplashTwo.rotationPointX * f5,
                this.BackSplashTwo.rotationPointY * f5,
                this.BackSplashTwo.rotationPointZ * f5);
        GL11.glScaled(1.0D, 1.0D, 0.96875D);
        GL11.glTranslatef(-this.BackSplashTwo.offsetX, -this.BackSplashTwo.offsetY, -this.BackSplashTwo.offsetZ);
        GL11.glTranslatef(
                -this.BackSplashTwo.rotationPointX * f5,
                -this.BackSplashTwo.rotationPointY * f5,
                -this.BackSplashTwo.rotationPointZ * f5);
        this.BackSplashTwo.render(f5);
        GL11.glPopMatrix();
    }

    public void renderColored() {
        float f5 = 0.0625f;

        // Base
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Base.offsetX, this.Base.offsetY, this.Base.offsetZ);
        GL11.glTranslatef(this.Base.rotationPointX * f5, this.Base.rotationPointY * f5, this.Base.rotationPointZ * f5);
        GL11.glScaled(0.96875D, 1.0D, 0.96875D);
        GL11.glTranslatef(-this.Base.offsetX, -this.Base.offsetY, -this.Base.offsetZ);
        GL11.glTranslatef(
                -this.Base.rotationPointX * f5, -this.Base.rotationPointY * f5, -this.Base.rotationPointZ * f5);
        this.Base.render(f5);
        GL11.glPopMatrix();

        // WallFront
        GL11.glPushMatrix();
        GL11.glTranslatef(this.WallFront.offsetX, this.WallFront.offsetY, this.WallFront.offsetZ);
        GL11.glTranslatef(
                this.WallFront.rotationPointX * f5,
                this.WallFront.rotationPointY * f5,
                this.WallFront.rotationPointZ * f5);
        GL11.glScaled(0.96875D, 1.0D, 1.0D);
        GL11.glTranslatef(-this.WallFront.offsetX, -this.WallFront.offsetY, -this.WallFront.offsetZ);
        GL11.glTranslatef(
                -this.WallFront.rotationPointX * f5,
                -this.WallFront.rotationPointY * f5,
                -this.WallFront.rotationPointZ * f5);
        this.WallFront.render(f5);
        GL11.glPopMatrix();
    }
}
