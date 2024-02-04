package com.ccdr.labyrinth.game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ccdr.labyrinth.game.Board;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Direction;

public class PlayersManager {

    private final List<Player> players = new ArrayList<>();
    private int activePlayer;
    private int diceVal;
    private Board board;

    public PlayersManager(final int numPlayers) {
        for(int i = 0; i < numPlayers; i++) {
            this.players.add(new PlayerImpl());
        }
        this.activePlayer = 0;
    }

    /**
     * Gives the list of players
     * @return an unmodifiable list of players
     */
    public List<Player> getPlayer() {
        return List.copyOf(this.players);
    }

    /**
     * method to set the index of the active player
     * @param activePlayer the index of the player to be set as active
     */
    public void setActivePlayer(final int activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Gives player active at the moment
     * @return the active player
     */
    public Player getActivePlayer() {
        return this.players.get(this.activePlayer);
    }

    /**
     * method to generate a random number
     */
    public void generateDiceValue() {
        Random random = new Random();
        this.diceVal = random.nextInt(12) + 1;
    }

    /**
     * method for moving a tile and checking whether the player has moved a tile
     * @return true if the active player moved a tile, otherwise false
     */
    public boolean hasTileMoved() {
        //TODO: implementation to understand if the tile has been moved
        //final Coordinate tileCoord <-- è argomento del metodo???
        return true;
    }
    
    /**
     * method that checks if upward movement is allowed
     */
    public void tryMoveUp() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row()-1, this.getActivePlayer().getCoord().column()));
        if(startTile.isOpen(Direction.UP) && endTile.isOpen(Direction.DOWN)) {
            startTile.onExit();
            this.getActivePlayer().moveUp();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method that checks if rightward movement is allowed
     */
    public void tryMoveRight() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row(), this.getActivePlayer().getCoord().column()+1));
        if(startTile.isOpen(Direction.RIGHT) && endTile.isOpen(Direction.LEFT)) {
            startTile.onExit();
            this.getActivePlayer().moveRight();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method that checks if leftward movement is allowed
     */
    public void tryMoveLeft() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row(), this.getActivePlayer().getCoord().column()-1));
        if(startTile.isOpen(Direction.LEFT) && endTile.isOpen(Direction.RIGHT)) {
            startTile.onExit();
            this.getActivePlayer().moveLeft();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method that checks if downward movement is allowed
     */
    public void tryMoveDown() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row()+1, this.getActivePlayer().getCoord().column()));
        if(startTile.isOpen(Direction.DOWN) && endTile.isOpen(Direction.UP)) {
            startTile.onExit();
            this.getActivePlayer().moveDown();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method to control if the turn of a player is finished
     * @return true if the player has finished his turn, otherwise false
     */
    public boolean isTurnFinished() {
        return this.hasTileMoved() && this.diceVal == 0;
    }
}
