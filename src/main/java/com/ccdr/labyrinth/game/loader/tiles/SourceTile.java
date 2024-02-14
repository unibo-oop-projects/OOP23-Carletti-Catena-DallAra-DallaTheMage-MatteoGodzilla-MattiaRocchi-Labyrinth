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
public final class SourceTile extends GenericTile {
    /**
     * Maximum number of materials that any source tile can keep inside.
     */
    public static final int MAX_QUANTITY = 10;
    /**
     * Minimum quantity of materials that any source tile has right after it becomes active.
     */
    public static final int MIN_QUANTITY = 3;
    /**
     * Quantity of materials right at the start of the game.
     */
    public static final int STARTING_QUANTITY = MIN_QUANTITY - 2;
    private final Material materialType;
    private int quantity;
    private final int turnsToWait;
    private int remainingCooldown;

    /**
     * @param assignedMaterial Material type that this source must generate
     * @param waitingTurns how many turns is this source locked after giving materials
     */
    public SourceTile(final Material assignedMaterial, final int waitingTurns) {
        this.materialType = assignedMaterial;
        this.quantity = STARTING_QUANTITY;
        this.turnsToWait = waitingTurns;
        this.remainingCooldown = 0;
        this.discover();
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

    @Override
    public void onEnter(final Player player) {
        player.increaseQuantityMaterial(this.materialType, collect());
    }

    @Override
    public void onExit(final Player player) { }

    private int collect() {
        if (this.isActive()) {
            final int collected = this.quantity;
            this.quantity = 0;
            this.remainingCooldown = this.turnsToWait + 1;
            return collected;
        }
        return 0;
    }

    // Getters
    /**
     * @return amount of materials inside this source tile
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * @return material type that this source tile generates
     */
    public Material getMaterialType() {
        return this.materialType;
    }

    /**
     * @return true if this source tile can give materials to a player
     */
    public boolean isActive() {
        return this.remainingCooldown <= 0;
    }
}
