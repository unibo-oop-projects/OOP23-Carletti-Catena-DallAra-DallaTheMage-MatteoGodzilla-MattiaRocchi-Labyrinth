package com.ccdr.labyrinth.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.Coordinate;

public class PlayerImpl implements Player{

    private final Map<Player, Coordinate> playersMapping = new HashMap<>();
    private final List<Player> players = new ArrayList<>();
    private final Map<Material, Integer> playerInventory = new HashMap<>();
    private int numPlayer;
    private int points = 0;

    public PlayerImpl(final Player player, final Coordinate coord) {
        if(this.players.isEmpty()) {
            this.players.add(player);
            this.numPlayer = 1;
        } else {
            this.players.add(player);
            this.numPlayer = this.players.size();
        }

        this.playersMapping.put(player, coord);
        
        for (var material : Material.values()) {
            this.playerInventory.put(material, 0);
        }
    }
    
    @Override
    public void moveUp(Coordinate coord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveUp'");
    }

    @Override
    public void moveRight(Coordinate coord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveRight'");
    }

    @Override
    public void moveLeft(Coordinate coord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveLeft'");
    }

    @Override
    public void moveDown(Coordinate coord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveDown'");
    }
    
    @Override
    public void drawPlayersOnBoard(final List<Player> players) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawPlayers'");
    }

    @Override
    public void drawPlayersStats(final List<Player> players) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawPlayersStats'");
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
    public Player getPlayer() {
        return this.players.get(numPlayer);
    }

    @Override
    public int getRow() {
        return this.playersMapping.get(this.getPlayer()).row();
    }

    @Override
    public int getColumn() {
        return this.playersMapping.get(this.getPlayer()).column();
    }

    @Override
    public void increasePoints(final int amount) {
        this.points += amount;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

}