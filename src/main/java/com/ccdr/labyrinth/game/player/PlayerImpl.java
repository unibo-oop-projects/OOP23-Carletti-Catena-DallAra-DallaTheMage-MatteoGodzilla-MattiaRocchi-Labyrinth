package com.ccdr.labyrinth.game.player;

import java.util.HashMap;
import java.util.Map;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.Coordinate;

public class PlayerImpl implements Player{

    private final Map<Material, Integer> playerInventory = new HashMap<>();
    private Coordinate coord;
    private int points = 0;

    public PlayerImpl() {
        for (var material : Material.values()) {
            this.playerInventory.put(material, 0);
        }
    }
    
    @Override
    public void moveUp() {
        this.coord = new Coordinate(this.coord.row()-1, this.coord.column());
    }

    @Override
    public void moveRight() {
        this.coord = new Coordinate(this.coord.row(), this.coord.column()+1);
    }

    @Override
    public void moveLeft() {
        this.coord = new Coordinate(this.coord.row(), this.coord.column()-1);
    }

    @Override
    public void moveDown() {
        this.coord = new Coordinate(this.coord.row()+1, this.coord.column());
    }

    @Override
    public void increaseQuantityMaterial(final Material material, final int amount) {
        int newValue = this.getQuantityMaterial(material) + amount;
        this.playerInventory.replace(material, newValue);
    }

    @Override
    public void decreaseQuantityMaterial(final Material material, final int amount) {
        int newValue = this.getQuantityMaterial(material) - amount;
        this.playerInventory.replace(material, newValue);
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
    public int getRow() {
        return this.coord.row();
    }

    @Override
    public int getColumn() {
        return this.coord.column();
    }

}