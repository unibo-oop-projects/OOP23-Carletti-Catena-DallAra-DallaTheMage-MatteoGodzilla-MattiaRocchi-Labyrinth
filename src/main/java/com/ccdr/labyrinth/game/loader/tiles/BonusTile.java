package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.player.Player;

public class BonusTile extends GenericTile{

    private int amount;
    private Material material;

    public BonusTile(Material materialType, int amount) {
        super.setType(TileType.BONUS);
        this.material = materialType;
        this.amount = amount;
    }

    @Override
    public void onEnter(Player player) {
        player.increaseQuantityMaterial(this.material, this.amount);
        this.amount = 0;
    }

    @Override
    public void onExit(Player player) { }

}
