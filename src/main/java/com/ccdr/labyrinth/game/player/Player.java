package com.ccdr.labyrinth.game.player;

import java.util.List;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.Coordinate;

public interface Player {
    /**
     * Move the player of one tile forward
     * @param coord the coordinate of the player
     */
    void moveUp(Coordinate coord);

    /**
     * Move the player one tile to the right
     * @param coord the coordinate of the player
     */
    void moveRight(Coordinate coord);

    /**
     * Move the player one tile to the left
     * @param coord the coordinate of the player
     */
    void moveLeft(Coordinate coord);

    /**
     * Move the player one tile back
     * @param coord the coordinate of the player
     */
    void moveDown(Coordinate coord);

    /**
     * Draw the players on the board
     * @param players the list of players to draw
     */
    void drawPlayersOnBoard(List<Player> players);

    /**
     * Draw the players statistics
     * @param players the list of players to draw
     */
    void drawPlayersStats(List<Player> players);

    /**
     * Increase the quantity of a specified material
     * @param material the material to increase
     * @param amount the quantity to increase
     */
    void increaseQuantityMaterial(Material material, int amount);

    /**
     * Decrease the quantity of a specified material
     * @param material the material to decrease
     * @param amount the quantity to decrease
     */
    void decreaseQuantityMaterial(Material material, int amount);

    /**
     * Gives the quantity of a material in the inventory
     * @param material the specified material
     * @return the quantity of a specified material
     */
    int getQuantityMaterial(Material material);

    /**
     * Gives a single player
     * @return a player
     */
    Player getPlayer();

    /**
     * Gives the value of row of a player
     * @return the row's value of a player
     */
    int getRow();

    /**
     * Gives the value of column of a player
     * @return the column's value of a player
     */
    int getColumn();

    /**
     * Increase the value of points
     * @param amount the value to increase points
     */
    void increasePoints(int amount);

    /**
     * Gives the quantity of points
     * @return the points' value
     */
    int getPoints();
}
