package com.ccdr.labyrinth.game.player;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.Coordinate;

public interface Player {
    /**
     * Move the player of one tile up
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
     * Move the player one tile down
     * @param coord the coordinate of the player
     */
    void moveDown(Coordinate coord);

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
