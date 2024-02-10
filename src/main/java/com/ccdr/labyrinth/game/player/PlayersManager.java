package com.ccdr.labyrinth.game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ccdr.labyrinth.game.Board;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Direction;

/**
 * A class that implements a manager of players, with a reference of all players in the game.
 * It also have the implementation of the manager of turns system.
 */
public class PlayersManager {

    private static final int MAX_DICEVAL = 12;

    private final List<Player> players = new ArrayList<>();
    private int activePlayer;
    private int diceVal;
    private Board board;
    /*
     * turnSubphase == 0 -> moverow/movecolumn/rotate
     * turnSubphase == 1 -> generate dice value
     * turnSubphase == 2 -> moveup/moveright/moveleft/movedown
     * turnSubphase == 3 -> end turn, so turnSubphase = 0
     */
    private int turnSubphase = 0;

    /**
     * The builder for a manager of players, with a list of all players in the game.
     * It also set as the first active player, the player identified by index 0.
     * @param numPlayers the number of players in the game
     */
    public PlayersManager(final int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            this.players.add(new PlayerImpl());
        }
        this.activePlayer = 0;
    }

    /**
     * Gives the list of players.
     * @return an unmodifiable list of players
     */
    public List<Player> getPlayers() {
        return List.copyOf(this.players);
    }

    /**
     * method to set the index of the active player.
     * @param activePlayer the index of the player to be set as active
     */
    public void setActivePlayer(final int activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Gives player active at the moment.
     * @return the active player
     */
    public Player getActivePlayer() {
        return this.players.get(this.activePlayer);
    }

    /**
     * method to generate a random number.
     */
    public void generateDiceValue() {
        if (this.turnSubphase == 1) {
            Random random = new Random();
            this.diceVal = random.nextInt(MAX_DICEVAL) + 1;
            this.setTurnSubphase(this.turnSubphase + 1);
        } else {
            System.out.println("Non puoi lanciare adesso il dado!!!");
        }
    }

    /**
     * method that sets the value of the point of the turn we are in.
     * @param value it's the new value of the turn subphase
     */
    public void setTurnSubphase(final int value) {
        if (value == 3) {
            this.turnSubphase = 0;
        } else {
            this.turnSubphase = value;
        }
    }

    /**
     * method to check if a row of the board is moved.
     * @param rowIndex the index of the row that is moved
     */
    public void tryMoveRow(final int rowIndex) {
        if (this.turnSubphase == 0 && rowIndex >= 0 && rowIndex < this.board.getHeight()) {
            this.board.shiftRow(rowIndex, 1);
            this.setTurnSubphase(this.turnSubphase + 1);
            //In base alla direzione del movimento si deve spostare insieme alla tile anche il/i player
        }
    }

    /**
     * method to check if a column of the board is moved.
     * @param columnIndex the index of the column that is moved
     */
    public void tryMoveColumn(final int columnIndex) {
        if (this.turnSubphase == 0 && columnIndex >= 0 && columnIndex < this.board.getWidth()) {
            this.board.shiftColumn(columnIndex, 1);
            this.setTurnSubphase(this.turnSubphase + 1);
            //In base alla direzione del movimento si deve spostare insieme alla tile anche il/i player
        }
    }

    /**
     * method to check if a tile of the board is rotated.
     * @param tileCoord the coordinate of the tile that is rotated
     */
    public void tryTileRotate(final Coordinate tileCoord) {
        if (this.turnSubphase == 0 && this.neighbours(this.getActivePlayer().getCoord(), tileCoord)) {
            this.board.getMap().get(tileCoord).rotate();
            this.setTurnSubphase(this.turnSubphase + 1);
        }
    }

    /**
     * checks that two tiles are adjacent in the eight directions.
     * @param coord1 the coordinate of the first tile
     * @param coord2 the coordinate of the second tile
     * @return true if the two tiles are adjacent, otherwise false
     */
    private boolean neighbours(final Coordinate coord1, final Coordinate coord2) {
        return Math.abs(coord1.row() - coord2.row()) <= 1
        && Math.abs(coord1.column() - coord2.column()) <= 1 && !coord1.equals(coord2);
    }

    /**
     * method that checks if upward movement is allowed.
     */
    public void tryMoveUp() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row() - 1, this.getActivePlayer().getCoord().column()));
        if (this.turnSubphase == 2 && this.diceVal > 0 && startTile.isOpen(Direction.UP) && endTile.isOpen(Direction.DOWN)) {
            startTile.onExit();
            this.getActivePlayer().moveUp();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method that checks if rightward movement is allowed.
     */
    public void tryMoveRight() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row(), this.getActivePlayer().getCoord().column() + 1));
        if (this.turnSubphase == 2 && this.diceVal > 0 && startTile.isOpen(Direction.RIGHT) && endTile.isOpen(Direction.LEFT)) {
            startTile.onExit();
            this.getActivePlayer().moveRight();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method that checks if leftward movement is allowed.
     */
    public void tryMoveLeft() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row(), this.getActivePlayer().getCoord().column() - 1));
        if (this.turnSubphase == 2 && this.diceVal > 0 && startTile.isOpen(Direction.LEFT) && endTile.isOpen(Direction.RIGHT)) {
            startTile.onExit();
            this.getActivePlayer().moveLeft();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method that checks if downward movement is allowed.
     */
    public void tryMoveDown() {
        var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row() + 1, this.getActivePlayer().getCoord().column()));
        if (this.turnSubphase == 2 && this.diceVal > 0 && startTile.isOpen(Direction.DOWN) && endTile.isOpen(Direction.UP)) {
            startTile.onExit();
            this.getActivePlayer().moveDown();
            endTile.onEnter();
            this.diceVal--;
        }
    }

    /**
     * method to control if the turn of a player is finished.
     * @return true if the player has finished his turn, otherwise false
     */
    public boolean isTurnFinished() {
        return this.turnSubphase == 2 && this.diceVal == 0;
    }

    /**
     * method for managing the turns system.
     */
    public void turnsManager() {
        //Poi cambio l'activePlayer (se isTurnFInisched è true),
        //mettendo turnSubpahse a 3 che verrà settato successivamente a 0
        if (this.isTurnFinished()) {
            this.setActivePlayer(this.activePlayer + 1 % this.players.size());
            this.setTurnSubphase(this.turnSubphase + 1);
        } else {
            System.out.println("Il tuo turno non è ancora terminato! Sei alla fase " + this.turnSubphase);
        }
    }
}
