package com.ccdr.labyrinth.game.tiles;

import java.util.Map;
import java.util.Set;

import com.ccdr.labyrinth.game.util.Coordinate;

/**
 * The Board interface represents a labyrinth, and outlines the criteria for operation and interactions with it.
 * For example: Row / Column shifting
 * Some rows and columns are blocked, that means the player can't interact with them.
 * If a row or a column is blocked means that contains one special tile. 
 */
public interface Board {
    /**
     * @return actual tile collection that represents the labyrinth.
     */
    Map<Coordinate, Tile> getMap();

    /** 
    * @return the blocked columns index collection.
    */
    Set<Integer> getBlockedColumns();

    /** 
    * @return the blocked rows index collection.
    */
    Set<Integer> getBlockedRows();

    /**
     * @return the actual labyrinth height.
     */
    int getHeight();

    /**
     * @return the actual labyrinth width.
     */
    int getWidth();

    /**
     * Assign a new labyrinth height.
     * @param height the new height.
     */
    void setHeight(int height);

    /**
     * Assign a new labyrinth width.
     * @param width the new width.
     */
    void setWidth(int width);

    /**
     * Insert a tile at the given coordinate.
     * @param coordinate the coordinates where place the tile.
     * @param tile the tile to place.
     */
    void insertTile(Coordinate coordinate, Tile tile);

    /**
     * Shift one row.
     * @param row the row to shift.
     * @param forward if true shift the row right, otherwise shift the row left.
     */
    void shiftRow(int row, boolean forward);

    /**
     * Shift one column.
     * @param column the column to shift.
     * @param forward if true shift the column down, otherwise shift the column up.
     */
    void shiftColumn(int column, boolean forward);

    /**
     * Discover the player nearby tiles based on a discover radius.
     * @param playerLocation the player location.
     * @param radius represents the size of the discovery radius.
     */
    void discoverNearBy(Coordinate playerLocation, int radius);

    /**
     * Add the respective index of the new blocked coordinate to the blocked row and column collections.
     * @param blocked the coordinate to base the row and column blocking.
     */
    void addBlocked(Coordinate blocked);

    /**
     * Rotate clockwise one labyrinth tile located at the given coordinate.
     * @param actual the coordinate of the tile to rotate.
     */
    void rotateClockWiseTile(Coordinate actual);

    /**
     * Rotate counterclockwise one labyrinth tile located at the given coordinate.
     * @param actual the coordinate of the tile to rotate.
     */
    void rotateCounterClockWiseTile(Coordinate actual);
}
