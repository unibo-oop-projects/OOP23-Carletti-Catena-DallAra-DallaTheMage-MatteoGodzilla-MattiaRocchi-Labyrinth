package com.ccdr.labyrinth.game.player;

import java.util.ArrayList;
import java.util.List;

public class PlayersManager {

    private final List<Player> players = new ArrayList<>();

    public PlayersManager(final int numPlayers) {
        for(int i = 0; i < numPlayers; i++) {
            this.players.add(new PlayerImpl());
        }
    }

    /**
     * Draw the players on the board
     * @param players the list of players to draw
     */
    public void drawPlayersOnBoard(final List<Player> players) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawPlayers'");
    }

    /**
     * Draw the players statistics
     * @param players the list of players to draw
     */
    public void drawPlayersStats(final List<Player> players) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawPlayersStats'");
    }

    /**
     * Gives a single player
     * @param numPlayer the index of the player that you want
     * @return a player
     */
    public Player getPlayer(final int numPlayer) {
        return this.players.get(numPlayer);
    }
}
