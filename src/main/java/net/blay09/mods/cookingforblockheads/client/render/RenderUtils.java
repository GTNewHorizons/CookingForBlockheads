package net.blay09.mods.cookingforblockheads.client.render;

import net.minecraftforge.common.util.ForgeDirection;

public class RenderUtils {

    public static float getAngle(int metadata) {

        switch (ForgeDirection.getOrientation(metadata)) {
            case NORTH:
                return 0;
            case EAST:
                return -90;
            case SOUTH:
                return 180;
            case WEST:
                return 90;
            default:
                return -90;
        }
    }
}
