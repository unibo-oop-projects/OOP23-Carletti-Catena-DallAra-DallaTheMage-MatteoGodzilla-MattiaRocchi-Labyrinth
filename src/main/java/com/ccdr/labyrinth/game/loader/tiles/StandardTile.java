package com.ccdr.labyrinth.game.loader.tiles;

import java.util.Optional;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.player.Player;

public class StandardTile extends GenericTile {
    private Optional<Material> bonusMaterial = Optional.empty();
    private int bonusAmount;

    public StandardTile() {
        super.setType(TileType.NORMAL);
    }

    public StandardTile(Material bonusMaterial, int amount) {
        super.setType(TileType.BONUS);
        this.bonusMaterial = Optional.of(bonusMaterial);
        this.bonusAmount = amount;
    }

    @Override
    public void onEnter(final Player player) {
        if (this.bonusMaterial.isPresent()) {
            player.increaseQuantityMaterial(this.bonusMaterial.get(), this.bonusAmount);
            this.bonusMaterial = Optional.empty();
            this.bonusAmount = 0;
        }
    }

    @Override
    public void onExit(final Player player) { }
}
