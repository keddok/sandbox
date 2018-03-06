package com.keddok;

/**
 * @author RMakhmutov
 * @since 06.03.2018
 */
public class Move {
    int deltaX;
    int deltaY;
    int k;
    int[] a;

    @Override
    public String toString() {
        String result = "MOVE " + deltaX + " " + deltaY + " " + k;
        for (int ai : a) {
            result += " " + ai;
        }

        return result;
    }
}
