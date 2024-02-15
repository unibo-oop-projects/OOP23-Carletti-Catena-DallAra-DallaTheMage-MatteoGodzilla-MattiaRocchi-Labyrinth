package com.ccdr.labyrinth.game.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ccdr.labyrinth.game.Board;
import com.ccdr.labyrinth.game.Context;
import com.ccdr.labyrinth.game.GuildContext;
import com.ccdr.labyrinth.game.UpdateBoardContext;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Direction;

/**
 * A class that implements a manager of players, with a reference of all players in the game.
 * It also have the implementation of the manager of turns system.
 */
public class PlayersManager implements Context {

    private static final int MAX_DICEVAL = 12;
    private static final Random RANDOM = new Random();

    private final List<Player> players = new ArrayList<>();
    private int activePlayer;
    private int diceVal;
    private final Board board;
    /**
     * An enum to indicate the two subphase that the Playersmanager has to manage.
     */
    public enum Subphase {
        /**
         * subphase == DICE -> call the method generateDiceValue.
         */
        DICE,
        /**
         * subphase == MOVEMENT -> call the method moveup/moveright/moveleft/movedown.
         */
        MOVEMENT
    }
    private Subphase subphase;
    private final UpdateBoardContext updateBoard;
    private final GuildContext guildContext;

    /**
     * The builder for a manager of players, with a list of all players in the game.
     * It also set as the first active player, the player identified by index 0.
     * @param numPlayers the number of players in the game
     * @param board the board of the game
     * @param updateContext the context that update the active player
     * @param guildContext the context of the guild
     */
    public PlayersManager(final int numPlayers, final Board board,
        final UpdateBoardContext updateContext, final GuildContext guildContext
    ) {
        this.board = board;
        this.updateBoard = updateContext;
        this.guildContext = guildContext;
        for (int i = 0; i < numPlayers; i++) {
            if (i == 0) {
                this.players.add(new PlayerImpl());
                this.players.get(i).setCoord(0, 0);
                board.discoverNearBy(this.players.get(i).getCoord(), 2);
            } else if (i == 1) {
                this.players.add(new PlayerImpl());
                this.players.get(i).setCoord(0, this.board.getWidth() - 1);
                board.discoverNearBy(this.players.get(i).getCoord(), 2);
            } else if (i == 2) {
                this.players.add(new PlayerImpl());
                this.players.get(i).setCoord(this.board.getHeight() - 1, 0);
                board.discoverNearBy(this.players.get(i).getCoord(), 2);
            } else if (i == 3) {
                this.players.add(new PlayerImpl());
                this.players.get(i).setCoord(this.board.getWidth() - 1, this.board.getWidth() - 1);
                board.discoverNearBy(this.players.get(i).getCoord(), 2);
            }
        }
        this.activePlayer = 0;
        this.subphase = Subphase.DICE;
    }

    /**
     * gives the list of players.
     * @return an unmodifiable list of players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    /**
     * method to set the index of the active player.
     * @param activePlayer the index of the player to be set as active
     */
    public void setActivePlayer(final int activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * gives player active at the moment.
     * @return the active player
     */
    public Player getActivePlayer() {
        return this.players.get(this.activePlayer);
    }

    /**
     * gives the index of the player active at the moment.
     * @return the index of the active player
     */
    public int getActivePlayerIndex() {
        return this.activePlayer;
    }

    /**
     * method to generate a random number.
     */
    public void generateDiceValue() {
        if (this.subphase == Subphase.DICE) {
            this.diceVal = RANDOM.nextInt(MAX_DICEVAL) + 1;
            this.subphase = Subphase.MOVEMENT;
        }
    }

    /**
     * gives the dice value.
     * @return the value of the dice
     */
    public int getDiceValue() {
        return this.diceVal;
    }

    /**
     * method to set the value of turn subphase.
     * @param subphase the new value of the turn subphase
     */
    public void setTurnSubphase(final Subphase subphase) {
        this.subphase = subphase;
    }

    /**
     * method that checks if upward movement is allowed.
     */
    @Override
    public void up() {
        final var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        final var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row() - 1, this.getActivePlayer().getCoord().column()));
        if (this.subphase == Subphase.MOVEMENT && this.diceVal > 0
            && startTile.isOpen(Direction.UP) && endTile.isOpen(Direction.DOWN)) {
            startTile.onExit(this.getActivePlayer());
            this.getActivePlayer().moveUp();
            endTile.onEnter(this.getActivePlayer());
            this.board.discoverNearBy(this.getActivePlayer().getCoord(), 2);
            this.diceVal--;
        }
    }

    /**
     * method that checks if downward movement is allowed.
     */
    @Override
    public void down() {
        final var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        final var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row() + 1, this.getActivePlayer().getCoord().column()));
        if (this.subphase == Subphase.MOVEMENT && this.diceVal > 0
            && startTile.isOpen(Direction.DOWN) && endTile.isOpen(Direction.UP)) {
            startTile.onExit(this.getActivePlayer());
            this.getActivePlayer().moveDown();
            endTile.onEnter(this.getActivePlayer());
            this.board.discoverNearBy(this.getActivePlayer().getCoord(), 2);
            this.diceVal--;
        }
    }

    /**
     * method that checks if leftward movement is allowed.
     */
    @Override
    public void left() {
        final var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        final var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row(), this.getActivePlayer().getCoord().column() - 1));
        if (this.subphase == Subphase.MOVEMENT && this.diceVal > 0
            && startTile.isOpen(Direction.LEFT) && endTile.isOpen(Direction.RIGHT)) {
            startTile.onExit(this.getActivePlayer());
            this.getActivePlayer().moveLeft();
            endTile.onEnter(this.getActivePlayer());
            this.board.discoverNearBy(this.getActivePlayer().getCoord(), 2);
            this.diceVal--;
        }
    }

    /**
     * method that checks if rightward movement is allowed.
     */
    @Override
    public void right() {
        final var startTile = this.board.getMap().get(this.getActivePlayer().getCoord());
        final var endTile = this.board.getMap()
            .get(new Coordinate(this.getActivePlayer()
            .getCoord().row(), this.getActivePlayer().getCoord().column() + 1));
        if (this.subphase == Subphase.MOVEMENT && this.diceVal > 0
            && startTile.isOpen(Direction.RIGHT) && endTile.isOpen(Direction.LEFT)) {
            startTile.onExit(this.getActivePlayer());
            this.getActivePlayer().moveRight();
            endTile.onEnter(this.getActivePlayer());
            this.board.discoverNearBy(this.getActivePlayer().getCoord(), 2);
            this.diceVal--;
        }
    }

    /**
     * method to do as first action in a player's turn.
     */
    @Override
    public void primary() {
        this.generateDiceValue();
    }

    /**
     * method to put a 0 the value of the dice, so the player ends his turn.
     */
    @Override
    public void secondary() {
        this.diceVal = 0;
    }

    /**
     * nothing to do as a back action.
     */
    @Override
    public void back() { }

    /**
     * method to control if the turn of a player is finished.
     * @return true if the player has finished his turn, otherwise false
     */
    @Override
    public boolean done() {
        return this.subphase == Subphase.MOVEMENT && this.diceVal == 0;
    }

    /**
     * method that executes the end of the turn, passing to the new active context.
     * @return the next active context
     */
    @Override
    public Context getNextContext() {
        final Coordinate guildTile = new Coordinate(this.board.getHeight() / 2, this.board.getWidth() / 2);
        if (this.getActivePlayer().getCoord().equals(guildTile)) {
            return this.guildContext;
        } else {
            return this.updateBoard;
        }
    }
}
