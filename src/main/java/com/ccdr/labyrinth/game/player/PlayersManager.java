package com.ccdr.labyrinth.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccdr.labyrinth.game.loader.Coordinate;

public class PlayersManager extends PlayerImpl{

    private final List<Player> players = new ArrayList<>();
    private final Map<Player, Coordinate> playersMapping = new HashMap<>();
    private int numPlayer;

    public PlayersManager(final Player player, final Coordinate coord) {
        if(this.players.isEmpty()) {
            this.players.add(player);
            this.numPlayer = 1;
        } else {
            this.players.add(player);
            this.numPlayer = this.players.size();
        }

        this.playersMapping.put(player, coord);
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
     * @return a player
     */
    public Player getPlayer() {
        return this.players.get(numPlayer);
    }

    /**
     * Gives the value of row of a player
     * @return the row's value of a player
     */
    public int getRow() {
        return this.playersMapping.get(this.getPlayer()).row();
    }

    /**
     * Gives the value of column of a player
     * @return the column's value of a player
     */
    public int getColumn() {
        return this.playersMapping.get(this.getPlayer()).column();
    }
}
