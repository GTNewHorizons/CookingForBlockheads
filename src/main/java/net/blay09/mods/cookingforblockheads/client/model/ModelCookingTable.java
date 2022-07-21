package net.blay09.mods.cookingforblockheads.client.model; // Cubik Studio 2.9.482 Beta JAVA exporter
// Designed by Blay09 with Cubik Studio - https://cubik.studio

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCookingTable extends ModelBase {

    // fields
    public ModelRenderer Backsplash;
    public ModelRenderer Top;
    public ModelRenderer Bottom;
    public ModelRenderer Body;
    public ModelRenderer CraftingPlate;
    public ModelRenderer DoorTop;
    public ModelRenderer DoorBottom;
    public ModelRenderer HandleTop;
    public ModelRenderer HandleBottom;

    public ModelCookingTable() {
        textureWidth = 64;
        textureHeight = 128;
        this.Bottom = new ModelRenderer(this, 0, 71);
        this.Bottom.setRotationPoint(-8.0F, 24.0F, -5.5F);
        this.Bottom.addBox(0.0F, -1.0F, 0.0F, 16, 1, 13, 0.0F);
        this.Backsplash = new ModelRenderer(this, 0, 58);
        this.Backsplash.setRotationPoint(8.0F, 7.0F, 6.5F);
        this.Backsplash.addBox(-16.0F, 0.0F, 0.0F, 16, 2, 1, 0.0F);
        this.Top = new ModelRenderer(this, 0, 85);
        this.Top.setRotationPoint(8.0F, 9.0F, -7.5F);
        this.Top.addBox(-16.0F, 0.0F, 0.0F, 16, 1, 15, 0.0F);
        this.HandleTop = new ModelRenderer(this, 36, 61);
        this.HandleTop.setRotationPoint(-1.5F, 13.0F, -8.0F);
        this.HandleTop.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.HandleBottom = new ModelRenderer(this, 44, 61);
        this.HandleBottom.setRotationPoint(-1.5F, 19.0F, -8.0F);
        this.HandleBottom.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.Body = new ModelRenderer(this, 0, 101);
        this.Body.setRotationPoint(8.0F, 10.0F, -6.5F);
        this.Body.addBox(-16.0F, 0.0F, 0.0F, 16, 13, 14, 0.0F);
        this.CraftingPlate = new ModelRenderer(this, 0, 61);
        this.CraftingPlate.setRotationPoint(-4.5F, 8.0F, -5.0F);
        this.CraftingPlate.addBox(0.0F, 0.0F, 0.0F, 9, 1, 9, 0.0F);
        this.DoorBottom = new ModelRenderer(this, 36, 63);
        this.DoorBottom.setRotationPoint(-5.5F, 18.0F, -7.5F);
        this.DoorBottom.addBox(0.0F, 0.0F, 0.0F, 11, 3, 1, 0.0F);
        this.DoorTop = new ModelRenderer(this, 36, 67);
        this.DoorTop.setRotationPoint(-5.5F, 12.0F, -7.5F);
        this.DoorTop.addBox(0.0F, 0.0F, 0.0F, 11, 3, 1, 0.0F);
    }

    public void renderUncolored() {
        float f5 = 0.0625f;
        Backsplash.render(f5);
        HandleTop.render(f5);
        HandleBottom.render(f5);
        Top.render(f5);
        CraftingPlate.render(f5);
    }

    public void renderColored() {
        float f5 = 0.0625f;
        Bottom.render(f5);
        Body.render(f5);
        DoorTop.render(f5);
        DoorBottom.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
