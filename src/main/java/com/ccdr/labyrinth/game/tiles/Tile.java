package com.ccdr.labyrinth.game.tiles;

import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.util.Direction;

import java.util.Map;

/**
 * The Tile interface represents a maze's tile, and outlines the criteria for operation and interactions.
 */
public interface Tile {

    /**
     * This method tells if the given direction is an open direction in this specific tile.
     * @param access the direction to check.
     * @return true if the given direction is open, otherwise false.
     */
    boolean isOpen(Direction access);

    /**
     * Tile that want to always be visible should NOT override this and just call discover() on the constructor.
     * @return true if the tile is discovered, otherwise false
     */
    boolean isDiscovered();

    /**
     * Executes specific subroutines when a player enters the tile.
     * @param player the player that enters the tile.
     */
    void onEnter(Player player);

    /**
     * Executes specific subroutines when a player leaves the tile.
     * @param player the player that leaves the tile.
     */
    void onExit(Player player);

    /**
     * Rotate the tile.
     * @param clockwise if true rotate the tile clockwise, otherwise rotate the tile counterclockwise.
     */
    void rotate(boolean clockwise);

    /**
     * Assign the given pattern to the tile.
     * @param readedPattern pattern to assign.
     */
    void setPattern(Map<Direction, Boolean> readedPattern);

    /**
     * Tile that want to always be visible should call this instead of overriding isDiscovered.
     * This method discover the tile.
     */
    void discover();

    /**
     * Give the tile pattern.
     * @return the actual assigned tile pattern.
     */
    Map<Direction, Boolean> getPattern();
}
