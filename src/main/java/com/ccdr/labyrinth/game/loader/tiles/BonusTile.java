package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.Material;

public class BonusTile extends GenericTile{

    private boolean rewardGiven = false;
    private int amount;
    private Material material;

    public BonusTile(Material materialType, int amount) {
        super.setType(TileType.BONUS);
        this.material = materialType;
        this.amount = amount;
    }

    @Override
    public void onEnter() {
        if (!this.rewardGiven) {
            //reward the player for exploring this tile
        }
        this.rewardGiven = true;
    }

    @Override
    public void onExit() { }

}
