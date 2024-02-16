package com.ccdr.labyrinth.game;

import java.util.List;

import com.ccdr.labyrinth.game.context.Context;
import com.ccdr.labyrinth.game.context.PlayersContext;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.util.Item;
import com.ccdr.labyrinth.game.util.Material;

/**
 * This interface describes all the draw methods that should be called in order to render the game state.
 */
public interface GameView {
    /**
     * Enables this object to render on screen.
     */
    void onEnable();

    /**
     * Clears the screen.
     * This method should be called before any of the draw methods, in order to avoid drawing on top of the previous frame
     */
    void clear();

    /**
     * Draw the labyrinth.
     * @param board board object that contains the tiles to draw
     */
    void drawBoard(Board board);

    /**
     * Draw the players on the board.
     * @param players the list of players to draw
     */
    void drawPlayersOnBoard(List<Player> players);

    /**
     * Draw the players statistics.
     * @param playersManager an object of type PlayersManager
     * @param materialPresent the list of the materials present in the game
     */
    void drawPlayersStats(PlayersContext playersManager, List<Material> materialPresent);

    /**
     * Draw the objectives showing what the player needs to do to get points.
     * @param missions list of objectives to show
     * @param completed list of objectives already completed
     */
    void drawGuildinfo(List<Item> missions, List<Item> completed);

    /**
     * Draw an overlay to the screen, based on what context should be shown to the user.
     * @param context context object to show on the screen
     */
    void drawContext(Context context);
}
