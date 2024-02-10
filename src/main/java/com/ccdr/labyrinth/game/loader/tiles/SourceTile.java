package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Material;

/**
 * How source tiles work in the game:
 * - A source tile starts with a set amount of materials as soon as it's created
 * - A source tile can only give one type of material to the player.
 * - A source tile can be either inactive or active, only active ones reward the
 * player.
 * - Once a player steps on the tile, it will be rewarded an amount of materials
 * and the source tile will become inactive.
 * - After N turns (where N is the number of players), the source tile will
 * become active with the minimum quantity.
 * - If a tile is active, every turn it will increase the amount of materials
 * stored inside, until the maximum quantity.
 */
public class SourceTile extends GenericTile {
    public static final int MAX_QUANTITY = 10;
    public static final int MIN_QUANTITY = 3;
    public static final int STARTING_QUANTITY = MIN_QUANTITY - 2;
    private final Material materialType;
    private int quantity;
    private int turnsToWait;
    private int remainingCooldown;

    public SourceTile(final Material assignedMaterial, final int waitingTurns) {
        super.setType(TileType.SOURCE);
        this.materialType = assignedMaterial;
        this.quantity = STARTING_QUANTITY;
        this.turnsToWait = waitingTurns;
        this.remainingCooldown = 0;
    }

    /**
     * this function must be called after every player's end of turn.
     */
    public void updateTile() {
        if (this.isActive()) {
            if (this.quantity < MAX_QUANTITY) {
                this.quantity++;
            }
        } else {
            this.remainingCooldown--;
            this.quantity = MIN_QUANTITY;
        }
    }

    public int collect() {
        if (this.isActive()) {
            int collected = this.quantity;
            this.quantity = 0;
            this.remainingCooldown = this.turnsToWait + 1;
            return collected;
        }
        return 0;
    }

    @Override
    public void onEnter(final Player player) {
        // TODO: need a reference to the player that entered this tile,
        // so that i can reward with materials.
    }

    @Override
    public void onExit(final Player player) { }

    // Getters
    public int getQuantity() {
        return this.quantity;
    }

    public Material getMaterialType() {
        return this.materialType;
    }

    public boolean isActive() {
        return this.remainingCooldown <= 0;
    }
}
