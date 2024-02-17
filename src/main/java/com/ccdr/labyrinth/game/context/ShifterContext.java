package com.ccdr.labyrinth.game.context;

import java.util.List;

import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.util.Coordinate;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ShifterContext manage player interactions as manipulation of one row or column.
 */
public final class ShifterContext implements Context {
    private final Board board;
    private final PlayersContext players;
    private final List<Coordinate> selected;
    private int selectedRow;
    private int selectedColumn;
    private int indexSelected;
    private boolean done;
    private boolean isRow;

    /**
     * The constructor of ShifterContext.
     * The SuppressFBWarning is necessary because to work this context
     * needs to directly manipulate the external passed objects.
     * @param board the labyrinth
     * @param players the player context is necessary in Board.discoverNearByPlayers(),
     * used to discover the new players nearby tiles.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
     public ShifterContext(final Board board, final PlayersContext players) {
        this.board = board;
        this.players = players;
        this.selected = new ArrayList<>();
        this.selected.clear();
        this.selectRow(0);
        this.isRow = true;
    }

    /**
     * This method first clear the list of selected tiles and next add to the List all tiles that compose the selected row.
     * @param i represents the row index
     */
    private void selectRow(final int i) {
        isRow = true;
        selected.clear();
        indexSelected = i;
        for (final Coordinate c : this.board.getMap().keySet()) {
            if (c.row() == i) {
                selected.add(c);
            }
        }
        this.selectedRow = i;
    }

    /**
     * This method first clear the list of selected tiles and next add to the List all tiles that compose the selected column.
     * @param i represents the column index
     */
    private void selectColumn(final int i) {
        isRow = false;
        selected.clear();
        indexSelected = i;
        for (final Coordinate c : this.board.getMap().keySet()) {
            if (c.column() == i) {
                selected.add(c);
            }
        }
        this.selectedColumn = i;
    }

    private int calculateCorrectIndex(final int i, final int size) {
        return (i + size) % size;
    }

    /**
     * This method changes the index of the selected row, making it point to the row immediately above it.
     * If the line above represents a row outside the margins, the index will point to the last row of the
     * labyrinth if not blocked.
     * The index moves up until it points to a non-blocked row and within the margins. 
     */
    @Override
    public void up() {
        do {
            this.selectRow(this.calculateCorrectIndex(--this.selectedRow, this.board.getHeight()));
        } while (this.board.getBlockedRows().contains(selectedRow));
    }

    /**
     * This method changes the index of the selected row, making it point to the row immediately below it.
     * If the line below represents a row outside the margins, the index will point to the first row of the
     * labyrinth if not blocked.
     * The index moves down until it points to a non-blocked row and within the margins. 
     */
    @Override
    public void down() {
        do {
            this.selectRow(this.calculateCorrectIndex(++this.selectedRow, this.board.getHeight()));
        } while (this.board.getBlockedRows().contains(selectedRow));
    }

    /**
     * This method changes the index of the selected column, making it point to the column immediately left of it.
     * If the left column represents a column outside the margins, the index will point to the last column of the
     * labyrinth if not blocked.
     * The index moves left until it points to a non-blocked column and within the margins.
     */
    @Override
    public void left() {
        do {
            this.selectColumn(this.calculateCorrectIndex(--this.selectedColumn, this.board.getWidth()));
        } while (this.board.getBlockedColumns().contains(selectedColumn));
    }

    /**
     * This method changes the index of the selected column, making it point to the column immediately right of it.
     * If the right column represents a column outside the margins, the index will point to the first column of the
     * labyrinth if not blocked.
     * The index moves until it points to a non-blocked column and within the margins.
     */
    @Override
    public void right() {
        do {
            this.selectColumn(this.calculateCorrectIndex(++this.selectedColumn, this.board.getWidth()));
        } while (this.board.getBlockedColumns().contains(selectedColumn));
    }

    /**
     * This method start shift procedure of the selected tiles.
     * If a row is selected, this method make the tiles right shift.
     * If a column is selected, this method makes the tiles down shift. 
     */
    @Override
    public void primary() {
        final boolean forward = true;
        this.done = true;
        if (isRow) {
            this.board.shiftRow(this.indexSelected, forward);
        } else {
            this.board.shiftColumn(this.indexSelected, forward);
        }
        this.discoverNearPlayers();
    }

    private void discoverNearPlayers() {
        for (final Player p : this.players.getPlayers()) {
            this.board.discoverNearBy(p.getCoord(), 2);
        }
    }

    @Override
    public void secondary() { }

    /**
     * This method start shift procedure of the selected tiles.
     * If a row is selected, this method make the tiles left shift.
     * If a column is selected, this method makes the tiles up shift. 
     */
    @Override
    public void back() {
        final boolean forward = false;
        this.done = true;
        if (isRow) {
            this.board.shiftRow(this.indexSelected, forward);
        } else {
            this.board.shiftColumn(this.indexSelected, forward);
        }
        this.discoverNearPlayers();
    }

    /**
     * This method tells if the player has done the labyrinth manipulation interaction.
     */
    @Override
    public boolean done() {
        final boolean exitCondition = done;
        this.done = false;
        return exitCondition;
    }

    /**
     * For ShifterContext, this method simply return the identity.
     */
    @Override
    public Context getNextContext() {
        return this;
    }

    /**
     * This method return the list of selected tiles.
     * @return List of selected tiles
     */
    public List<Coordinate> selectedTiles() {
        return Collections.unmodifiableList(selected);
    }
}
