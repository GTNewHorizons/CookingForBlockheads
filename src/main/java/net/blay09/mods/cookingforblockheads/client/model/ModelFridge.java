package net.blay09.mods.cookingforblockheads.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * ModelFridge - BlayTheNinth & Zero9o1o
 * Created using Tabula 5.1.0
 */
public class ModelFridge extends ModelBaseFridge {
    public ModelRenderer FrontRightFoot;
    public ModelRenderer FrontLeftFoot;
    public ModelRenderer BackRightFoot;
    public ModelRenderer BackLeftFoot;
    public ModelRenderer PlugThingy;

    public ModelRenderer DoorShelfBottom;
    public ModelRenderer DoorShelfBottomFlipped;
    public ModelRenderer DoorShelfLeft;
    public ModelRenderer DoorShelfLeftFlipped;
    public ModelRenderer DoorShelfRight;
    public ModelRenderer DoorShelfRightFlipped;
    public ModelRenderer DoorShelfFront;
    public ModelRenderer DoorShelfFrontFlipped;
    public ModelRenderer TopHinge;
    public ModelRenderer TopHingeFlipped;
    public ModelRenderer BottomHinge;
    public ModelRenderer BottomHingeFlipped;
    public ModelRenderer LeftSeal;
    public ModelRenderer RightSeal;
    public ModelRenderer TopSeal;
    public ModelRenderer BottomSeal;
    public ModelRenderer FreezerTop;
    public ModelRenderer TopShelf;
    public ModelRenderer MiddleShelf;
    public ModelRenderer FreezerMiddle;
    public ModelRenderer FreezerRightDoor;
    public ModelRenderer FreezerLeftDoor;
    public ModelRenderer FreezerRightDoorHandle;
    public ModelRenderer FreezerLeftDoorHandle;

    public ModelFridge() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.FrontLeftFoot = new ModelRenderer(this, 0, 0);
        this.FrontLeftFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FrontLeftFoot.addBox(9.0F, 1.3F, 1.0F, 2, 1, 2, 0.0F);
        this.FreezerLeftDoorHandle = new ModelRenderer(this, 88, 2);
        this.FreezerLeftDoorHandle.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FreezerLeftDoorHandle.addBox(1.4F, 2.0F, -0.4F, 2, 1, 1, 0.0F);
        this.DoorShelfFront = new ModelRenderer(this, 57, 3);
        this.DoorShelfFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfFront.addBox(4.0F, 6.0F, 1.3F, 6, 1, 1, 0.0F);
        this.DoorShelfFrontFlipped = new ModelRenderer(this, 57, 3);
        this.DoorShelfFrontFlipped.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfFrontFlipped.addBox(-10.0F, 6.0F, 1.3F, 6, 1, 1, 0.0F);
        this.DoorShelfRight = new ModelRenderer(this, 71, 0);
        this.DoorShelfRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfRight.addBox(3.0F, 6.0F, 0.3F, 1, 1, 2, 0.0F);
        this.DoorShelfRightFlipped = new ModelRenderer(this, 71, 0);
        this.DoorShelfRightFlipped.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfRightFlipped.addBox(-4.0F, 6.0F, 0.3F, 1, 1, 2, 0.0F);
        this.RightWall = new ModelRenderer(this, 42, 0);
        this.RightWall.setRotationPoint(-7.0F, -7.3F, -5.0F);
        this.RightWall.addBox(0.0F, 0.0F, 0.0F, 2, 31, 11, 0.0F);
        this.LeftWall = new ModelRenderer(this, 68, 0);
        this.LeftWall.setRotationPoint(5.0F, -7.3F, -5.0F);
        this.LeftWall.addBox(0.0F, 0.0F, 0.0F, 2, 31, 11, 0.0F);
        this.LeftSeal = new ModelRenderer(this, 120, 3);
        this.LeftSeal.setRotationPoint(5.5F, -6.8F, -6.0F);
        this.LeftSeal.addBox(0.0F, 0.0F, 0.0F, 1, 30, 1, 0.0F);
        this.TopShelf = new ModelRenderer(this, 30, 44);
        this.TopShelf.setRotationPoint(-5.0F, -1.0F, -2.0F);
        this.TopShelf.addBox(0.0F, 0.0F, 0.0F, 10, 1, 7, 0.0F);
        this.FreezerTop = new ModelRenderer(this, 69, 42);
        this.FreezerTop.setRotationPoint(-5.0F, 12.9F, -4.0F);
        this.FreezerTop.addBox(0.0F, 0.1F, 0.0F, 10, 1, 9, 0.0F);
        this.BottomSeal = new ModelRenderer(this, 54, 42);
        this.BottomSeal.setRotationPoint(-5.5F, 22.2F, -6.0F);
        this.BottomSeal.addBox(0.0F, 0.0F, 0.0F, 11, 1, 1, 0.0F);
        this.MiddleShelf = new ModelRenderer(this, 22, 52);
        this.MiddleShelf.setRotationPoint(-5.0F, 6.0F, -3.0F);
        this.MiddleShelf.addBox(0.0F, 0.0F, 0.0F, 10, 1, 8, 0.0F);
        this.BottomHinge = new ModelRenderer(this, 120, 0);
        this.BottomHinge.setRotationPoint(-6.7F, 22.4F, -6.9F);
        this.BottomHinge.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.BottomHingeFlipped = new ModelRenderer(this, 120, 0);
        this.BottomHingeFlipped.setRotationPoint(6.7F, 22.4F, -6.9F);
        this.BottomHingeFlipped.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.FreezerLeftDoor = new ModelRenderer(this, 94, 28);
        this.FreezerLeftDoor.setRotationPoint(0.9F, 0.0F, 0.2F);
        this.FreezerLeftDoor.addBox(0.0F, 0.0F, 0.0F, 5, 8, 1, 0.0F);

        this.DoorHandle = new ModelRenderer(this, 116, 0);
        this.DoorHandle.setRotationPoint(-6.7F, -6.5F, -6.5F);
        this.DoorHandle.addBox(12.0F, 2.7F, -1.0F, 1, 23, 1, 0.0F);
        this.DoorHandleFlipped = new ModelRenderer(this, 116, 0);
        this.DoorHandleFlipped.setRotationPoint(6.7F, -6.5F, -6.5F);
        this.DoorHandleFlipped.addBox(-13F, 2.7F, -1.0F, 1, 23, 1, 0.0F);

        this.TopHinge = new ModelRenderer(this, 83, 0);
        this.TopHinge.setRotationPoint(-6.7F, -7.0F, -6.9F);
        this.TopHinge.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.TopHingeFlipped = new ModelRenderer(this, 83, 0);
        this.TopHingeFlipped.setRotationPoint(6.7F, -7.0F, -6.9F);
        this.TopHingeFlipped.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);

        this.Door = new ModelRenderer(this, 0, 26);
        this.Door.setRotationPoint(-6.7F, -6.5F, -6.5F);
        this.Door.addBox(-0.3F, -0.8F, -0.5F, 14, 31, 1, 0.0F);
        this.DoorFlipped = new ModelRenderer(this, 0, 26);
        this.DoorFlipped.setRotationPoint(6.7F, -6.5F, -6.5F);
        this.DoorFlipped.addBox(-13.7F, -0.8F, -0.5F, 14, 31, 1, 0.0F);

        this.FreezerMiddle = new ModelRenderer(this, 58, 44);
        this.FreezerMiddle.setRotationPoint(-0.5F, 14.0F, -4.0F);
        this.FreezerMiddle.addBox(0.0F, 0.0F, 0.0F, 1, 8, 9, 0.0F);
        this.BottomWall = new ModelRenderer(this, 0, 0);
        this.BottomWall.setRotationPoint(-5.0F, 21.7F, -5.0F);
        this.BottomWall.addBox(0.0F, 0.0F, 0.0F, 10, 2, 11, 0.0F);
        this.TopSeal = new ModelRenderer(this, 30, 42);
        this.TopSeal.setRotationPoint(-5.5F, -6.8F, -6.0F);
        this.TopSeal.addBox(0.0F, 0.0F, 0.0F, 11, 1, 1, 0.0F);
        this.BackWall = new ModelRenderer(this, 94, 0);
        this.BackWall.setRotationPoint(-5.0F, -5.3F, 4.7F);
        this.BackWall.addBox(0.0F, 0.0F, 0.0F, 10, 27, 1, 0.0F);
        this.DoorShelfLeft = new ModelRenderer(this, 65, 0);
        this.DoorShelfLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfLeft.addBox(10.0F, 6.0F, 0.3F, 1, 1, 2, 0.0F);
        this.DoorShelfLeftFlipped = new ModelRenderer(this, 65, 0);
        this.DoorShelfLeftFlipped.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfLeftFlipped.addBox(-11.0F, 6.0F, 0.3F, 1, 1, 2, 0.0F);
        this.PlugThingy = new ModelRenderer(this, 45, 0);
        this.PlugThingy.setRotationPoint(0.0F, 26.0F, 0.2F);
        this.PlugThingy.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.DoorShelfBottom = new ModelRenderer(this, 31, 3);
        this.DoorShelfBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfBottom.addBox(3.0F, 7.0F, 0.3F, 8, 1, 2, 0.0F);
        this.DoorShelfBottomFlipped = new ModelRenderer(this, 31, 3);
        this.DoorShelfBottomFlipped.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.DoorShelfBottomFlipped.addBox(-11.0F, 7.0F, 0.3F, 8, 1, 2, 0.0F);
        this.FrontRightFoot = new ModelRenderer(this, 0, 0);
        this.FrontRightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FrontRightFoot.addBox(-1.0F, 1.3F, 1.0F, 2, 1, 2, 0.0F);

        this.TopWall = new ModelRenderer(this, 0, 0);
        this.TopWall.setRotationPoint(-5.0F, -7.3F, -5.0F);
        this.TopWall.addBox(0.0F, 0.0F, 0.0F, 10, 2, 11, 0.0F);

        this.FreezerRightDoorHandle = new ModelRenderer(this, 87, 0);
        this.FreezerRightDoorHandle.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FreezerRightDoorHandle.addBox(1.4F, 2.0F, -0.4F, 2, 1, 1, 0.0F);
        this.FreezerRightDoor = new ModelRenderer(this, 30, 26);
        this.FreezerRightDoor.setRotationPoint(-5.0F, 0.0F, 0.2F);
        this.FreezerRightDoor.addBox(0.0F, 0.0F, 0.0F, 5, 8, 1, 0.0F);
        this.BackLeftFoot = new ModelRenderer(this, 0, 0);
        this.BackLeftFoot.setRotationPoint(10.0F, 0.0F, 7.0F);
        this.BackLeftFoot.addBox(-1.0F, 1.3F, 1.0F, 2, 1, 2, 0.0F);
        this.BackRightFoot = new ModelRenderer(this, 0, 0);
        this.BackRightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BackRightFoot.addBox(-1.0F, 1.3F, 8.0F, 2, 1, 2, 0.0F);
        this.RightSeal = new ModelRenderer(this, 116, 24);
        this.RightSeal.setRotationPoint(-6.5F, -6.8F, -6.0F);
        this.RightSeal.addBox(0.0F, 0.0F, 0.0F, 1, 30, 1, 0.0F);
        this.BottomWall.addChild(this.FrontLeftFoot);
        this.FreezerLeftDoor.addChild(this.FreezerLeftDoorHandle);
        this.DoorShelfRight.addChild(this.DoorShelfFront);
        this.DoorShelfRightFlipped.addChild(this.DoorShelfFrontFlipped);
        this.DoorShelfLeft.addChild(this.DoorShelfRight);
        this.DoorShelfLeftFlipped.addChild(this.DoorShelfRightFlipped);
        this.FreezerMiddle.addChild(this.FreezerLeftDoor);
        this.DoorShelfBottom.addChild(this.DoorShelfLeft);
        this.DoorShelfBottomFlipped.addChild(this.DoorShelfLeftFlipped);
        this.BackWall.addChild(this.PlugThingy);
        this.DoorHandle.addChild(this.DoorShelfBottom);
        this.DoorHandleFlipped.addChild(this.DoorShelfBottomFlipped);
        this.BottomWall.addChild(this.FrontRightFoot);
        this.FreezerRightDoor.addChild(this.FreezerRightDoorHandle);
        this.FreezerMiddle.addChild(this.FreezerRightDoor);
        this.BottomWall.addChild(this.BackLeftFoot);
        this.BottomWall.addChild(this.BackRightFoot);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.BackWall.render(f5);
        this.BottomHinge.render(f5);
        this.BottomSeal.render(f5);
        this.BottomWall.render(f5);
        this.Door.render(f5);
        this.DoorHandle.render(f5);
        this.FreezerMiddle.render(f5);
        this.FreezerTop.render(f5);
        this.LeftSeal.render(f5);
        this.LeftWall.render(f5);
        this.MiddleShelf.render(f5);
        this.RightSeal.render(f5);
        this.RightWall.render(f5);
        this.TopHinge.render(f5);
        this.TopSeal.render(f5);
        this.TopShelf.render(f5);
        this.TopWall.render(f5);
    }

    public void renderInterior() {
        float f5 = 0.0625f;
        this.FreezerMiddle.render(f5);
        this.FreezerTop.render(f5);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1f, 1f, 1f, 0.5f);
        this.MiddleShelf.render(f5);
        this.TopShelf.render(f5);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void renderUncolored() {
        float f5 = 0.0625f;
        if (isFlipped) {
            this.BottomHingeFlipped.render(f5);
            this.TopHingeFlipped.render(f5);
            this.DoorHandleFlipped.render(f5);
        } else {
            this.BottomHinge.render(f5);
            this.TopHinge.render(f5);
            this.DoorHandle.render(f5);
        }
        this.LeftSeal.render(f5);
        this.BottomSeal.render(f5);
        this.RightSeal.render(f5);
        this.TopSeal.render(f5);
    }
}
