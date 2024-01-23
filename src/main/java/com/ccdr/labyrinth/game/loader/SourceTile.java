package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.Material;

public class SourceTile extends StandardTile {
    private final int MAX_CHARGE = 10;
    private Material assignedMaterial;
    private int material;

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public Material getAssignedMaterial() {
        return assignedMaterial;
    }

    public void setAssignedMaterial(Material assignedMaterial) {
        this.assignedMaterial = assignedMaterial;
    }

    public void accumulate() {
        if(material < MAX_CHARGE) {
            material++;
        }
    }

    public int collect() {
        int collected = this.material;
        this.material = 0;
        return collected;
    }
}
