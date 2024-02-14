package com.ccdr.labyrinth.game;

import java.util.List;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.player.PlayersManager;

public interface GameView {
    void onEnable();

    void clear();

    void drawBoard(Board board);

    /**
     * Draw the players on the board
     * @param players the list of players to draw
     */
    void drawPlayersOnBoard(List<Player> players);

    /**
     * Draw the players statistics
     * @param playersManager an object of type PlayersManager
     * @param materialPresent the list of the materials present in the game
     */
    void drawPlayersStats(PlayersManager playersManager, List<Material> materialPresent);

    void drawMissions(List<Item> missions);

    void drawContext(Context context);
}
