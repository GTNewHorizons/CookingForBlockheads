package net.blay09.mods.cookingforblockheads.utils;

public class DirectionUtils {

    /**
     * Returns the result of the mathematical-operation "modulus" of any pair of a number and integer. This is distinct
     * from Java's mod-operator (%), which returns the remainder of any two numbers; the mathematics-operation,
     * "modulus" returns the remainder, in whole multiples.
     *
     * @param number  operand
     * @param modulus zero-congruent definition (maximum space for modular arithmetic)
     * @return modulus of number, given an operand, with negative-number support
     */
    private static double modulus(final double number, final int modulus) {
        if (number < 0) {
            return number - (modulus * Math.floor(number / modulus));
        } else {
            return number % modulus;
        }
    }

    /**
     * Maps direction, calculated from the nearest cardinal-directions, as {South, West, North, East} to {0, 1, 2, 3}
     *
     * @param yaw lateral-direction of gaze
     * @return cardinal-direction index
     */
    public static int getDirection(final double yaw) {
        double modified_yaw = (yaw * (4.0 / 360.0)) + 0.5;
        modified_yaw = modulus(modified_yaw, 4);
        return (int) Math.floor(modified_yaw);
    }

    /**
     * Returns the "handedness" of a current gaze, relative to the nearest cardinal-direction, represented as (binary)
     * being before or after (clockwise) the nearest cardinal-direction
     * 
     * @param yaw lateral-direction of gaze
     * @return truth of being before the center of the nearest cardinal-direction
     */
    public static boolean isHalfDirection(final double yaw) {
        double modified_yaw = yaw * (8.0 / 360.0);
        modified_yaw = modulus(modified_yaw, 2);
        return Math.floor(modified_yaw) == 0;
    }
}
