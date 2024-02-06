package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.Material;

public class SourceTile extends GenericTile {
    public static final int MAX_CHARGE = 10;
    private final Material assignedMaterial;
    private int materialQuantity;

    public SourceTile(Material assignedMaterial) {
        this.assignedMaterial = assignedMaterial;
        super.setType(TileType.SOURCE);
    }

    public int getQuantity() {
        return materialQuantity;
    }

    public Material getAssignedMaterial() {
        return assignedMaterial;
    }

    public void accumulate() {
        if(materialQuantity < MAX_CHARGE) {
            materialQuantity++;
        }
    }

    public int collect() {
        int collected = this.materialQuantity;
        this.materialQuantity = 0;
        return collected;
    }

    @Override
    public void onEnter() {
        //TODO: need a reference to the player that entered this tile,
        //so that i can reward with materials
    }

    @Override
    public void onExit() {}
}
