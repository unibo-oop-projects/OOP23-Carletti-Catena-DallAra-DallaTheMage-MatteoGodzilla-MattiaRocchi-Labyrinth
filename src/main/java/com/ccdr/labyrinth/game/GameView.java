package com.ccdr.labyrinth.game;

import java.util.List;

import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;

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
     * @param players the list of players to draw
     */
    void drawPlayersStats(List<Player> players);

    void drawMissions(List<Item> missions);
}
