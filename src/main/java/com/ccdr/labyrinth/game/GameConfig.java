package com.ccdr.labyrinth.game;

import java.util.List;

/**
 * This class is used to contain all the configuration necessary to create a game.
 * It is used as a transition class between the menu and the game.
 */
public final class GameConfig {
    //Possible options for configuring the game
    /**
     * Possible choices for setting up the player count.
     */
    public static final List<Integer> PLAYER_COUNT_OPTIONS = List.of(2, 3, 4);
    /**
     * Possible choices for setting up the labyrinth size.
     */
    public static final List<Integer> LABYRINTH_SIZE_OPTIONS = List.of(15, 31, 45);
    /**
     * Possible choices for setting up how many source are available in the labyrinth.
     */
    public static final List<Integer> SOURCE_OPTIONS = List.of(4, 8, 12, 16);

    /**
     * Default value for player count.
     */
    public static final int DEFAULT_PLAYER_COUNT = PLAYER_COUNT_OPTIONS.get(0);
    /**
     * Default value for labyrinth size.
     */
    public static final int DEFAULT_LABYRINTH_SIZE = LABYRINTH_SIZE_OPTIONS.get(1);
    /**
     * Default value for source tile count.
     */
    public static final int DEFAULT_SOURCE_TILE_COUNT = SOURCE_OPTIONS.get(1);

    private int playerCount = DEFAULT_PLAYER_COUNT;
    private int labyrinthHeight = DEFAULT_LABYRINTH_SIZE;
    private int labyrinthWidth = DEFAULT_LABYRINTH_SIZE;
    // change to percentage of source based on labyrinth dimensions
    private int sourceTiles = DEFAULT_SOURCE_TILE_COUNT;

    //Getters

    /**
     * @return width of the labyrinth to generate
     */
    public int getLabyrinthWidth() {
        return labyrinthWidth;
    }

    /**
     * @return height of the labyrinth to generate
     */
    public int getLabyrinthHeight() {
        return labyrinthHeight;
    }

    /**
     * @return amount of source tiles to generate around the center
     */
    public int getSourceTiles() {
        return sourceTiles;
    }

    /**
     * @return number of players that want to play
     */
    public int getPlayerCountOptions() {
        return playerCount;
    }

    /**
     * @param labyrinthWidth new width of the labyrinth
     */
    public void setLabyrinthWidth(final int labyrinthWidth) {
        this.labyrinthWidth = labyrinthWidth;
    }

    /**
     * @param labyrinthHeight new height of the labyrinth
     */
    public void setLabyrinthHeight(final int labyrinthHeight) {
        this.labyrinthHeight = labyrinthHeight;
    }

    /**
     * @param sourceTiles new amount of source tiles to generate
     */
    public void setSourceTiles(final int sourceTiles) {
        this.sourceTiles = sourceTiles;
    }

    /**
     * @param playerCount new number of players that want to play
     */
    public void setPlayerCount(final int playerCount) {
        this.playerCount = playerCount;
    }

}
