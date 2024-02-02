package com.ccdr.labyrinth.game.player;

import java.util.HashMap;
import java.util.Map;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.Coordinate;

public class PlayerImpl implements Player{

    private final Map<Material, Integer> playerInventory = new HashMap<>();
    //private PlayersManager player;
    private int points = 0;

    public PlayerImpl() {
        for (var material : Material.values()) {
            this.playerInventory.put(material, 0);
        }
    }
    
    @Override
    public void moveUp(Coordinate coord) {
        //ATTENZIONE: controllo su isOpen() è true (sempre variabile Tile), cioè posso andarci sopra altrimenti non mi sposto
        //Qui va chiamato onExit() su variabile di tipo Tile dalla casella da cui esco
        //IMPORTANTE!!! Metodo per trasformare da coord a oggetto Tile
        //Sposto il player una tile in avanti (rows-1, column)
        //Qui va chiamato onEnter() su variabile di tipo Tile dalla casella in cui entro dopo lo spostamento
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

}