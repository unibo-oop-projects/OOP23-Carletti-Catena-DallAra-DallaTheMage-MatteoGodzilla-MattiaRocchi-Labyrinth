package com.ccdr.labyrinth.game;

import java.util.List;

import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;

public interface GameView {
    void onEnable();

    void draw(double fps);

    void drawBoard(Board board);

    /**
     * Draw the players on the board
     * @param players the list of players to draw
     */
    void drawPlayersOnBoard(final List<Player> players);

    /**
     * Draw the players statistics
     * @param players the list of players to draw
     */
    void drawPlayersStats(final List<Player> players);

    void drawMissions(List<Item> missions);

    void onDisable();
}
