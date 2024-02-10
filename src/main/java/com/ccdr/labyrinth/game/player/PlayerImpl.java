package com.ccdr.labyrinth.game.player;

import java.util.HashMap;
import java.util.Map;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.Coordinate;

/**
 * A class that implements the interface Player, and represents the implementation of a player.
 * It's final because the class is not designed for extension.
 */
public final class PlayerImpl implements Player {

    private final Map<Material, Integer> playerInventory = new HashMap<>();
    private Coordinate coord;
    private final GameConfig gameconfig = new GameConfig();
    private int points;

    /**
     * The builder for a player, with an inventory that contains materials
     * and a position in coordinate (0,0).
     */
    public PlayerImpl() {
        for (final var material : Material.values()) {
            this.playerInventory.put(material, 0);
        }
        this.coord = new Coordinate(0, 0);
    }

    @Override
    public void moveUp() {
        if (this.coord.row() >= 1) {
            this.coord = new Coordinate(this.coord.row() - 1, this.coord.column());
        }
    }

    @Override
    public void moveRight() {
        if (this.coord.column() < this.gameconfig.getLabyrinthWidth() - 1) {
            this.coord = new Coordinate(this.coord.row(), this.coord.column() + 1);
        }
    }

    @Override
    public void moveLeft() {
        if (this.coord.column() >= 1) {
            this.coord = new Coordinate(this.coord.row(), this.coord.column() - 1);
        }
    }

    @Override
    public void moveDown() {
        if (this.coord.row() < this.gameconfig.getLabyrinthHeight() - 1) {
            this.coord = new Coordinate(this.coord.row() + 1, this.coord.column());
        }
    }

    @Override
    public void increaseQuantityMaterial(final Material material, final int amount) {
        final int newValue = this.getQuantityMaterial(material) + amount;
        this.playerInventory.replace(material, newValue);
    }

    @Override
    public void decreaseQuantityMaterial(final Material material, final int amount) {
        if (this.playerInventory.get(material) >= amount) {
            final int newValue = this.getQuantityMaterial(material) - amount;
            this.playerInventory.replace(material, newValue);
        }
    }

    @Override
    public int getQuantityMaterial(final Material material) {
        return this.playerInventory.get(material);
    }

    @Override
    public void increasePoints(final int amount) {
        this.points += amount;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public Coordinate getCoord() {
        return this.coord;
    }

}
