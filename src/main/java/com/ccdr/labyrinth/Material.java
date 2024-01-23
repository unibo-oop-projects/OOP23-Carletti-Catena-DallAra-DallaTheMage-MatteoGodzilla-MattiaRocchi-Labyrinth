package com.ccdr.labyrinth;

import java.util.Map;

public enum Material {
    WOOD,
    COAL,
    IRON,
    COPPER,
    DIAMOND,
    SILK;

    public static Map<Integer, Material> toList() {
        return Map.of(0, WOOD, 1, COAL, 2, IRON, 3, COPPER, 4, DIAMOND, 5, SILK);
    } 
}
