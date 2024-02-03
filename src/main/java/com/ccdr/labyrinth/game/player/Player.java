package com.ccdr.labyrinth.game.player;

import com.ccdr.labyrinth.Material;

public interface Player {
    /**
     * Move the player of one tile up
     */
    void moveUp();

    /**
     * Move the player one tile to the right
     */
    void moveRight();

    /**
     * Move the player one tile to the left
     */
    void moveLeft();

    /**
     * Move the player one tile down
     */
    void moveDown();

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
}
