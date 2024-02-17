package com.ccdr.labyrinth.game.context;

import java.util.List;

import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.util.Coordinate;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.Collections;

/**
 * RotationContext manages player interactions such as selecting and rotating a tile among those adjacent to him.
 */
public final class RotationContext implements Context {
    private final Board board;
    private final List<Coordinate> selected;
    private final PlayersContext playerManager;
    private boolean done;
    private boolean generatedRange;
    private Coordinate actual;
    private int minColumn, minRow, maxColumn, maxRow;

    /**
     * The constructor of RotationContext.
     * The SuppressFBWarning is necessary because to work this context 
     * needs to directly manipulate the external passed objects.
     * @param board
     * @param playerManager
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public RotationContext(final Board board, final PlayersContext playerManager) {
        this.board = board;
        this.selected = new ArrayList<>();
        this.playerManager = playerManager;
        this.setRange(this.playerManager.getActivePlayer().getCoord());
        this.actual = new Coordinate(minRow, minColumn);
        this.selected.add(this.actual);
    }

    private void setRange(final Coordinate player) {
        minRow = Math.max(0, player.row() - 1);
        minColumn = Math.max(0, player.column() - 1);
        maxRow = Math.min(player.row() + 1, this.board.getHeight() - 1);
        maxColumn = Math.min(player.column() + 1, this.board.getWidth() - 1);
        this.actual = new Coordinate(minRow, minColumn);
        this.generatedRange = true;
    }

    private void replaceSelected(final Coordinate nextSelected) {
        this.actual = nextSelected;
        this.selected.add(actual);
    }

    /**
     * This method changes the row index of the selected tile, making it point to one tile immediately above it.
     * If the tile above is outside the margins, the new row index will point to the last row tile index of the
     * calculated range.
     */
    @Override
    public void up() {
        this.selected.clear();
        if (!generatedRange) {
            this.setRange(this.playerManager.getActivePlayer().getCoord());
        }
        int actualRow = this.actual.row();
        actualRow = Math.max(minRow, actualRow - 1);
        final Coordinate nextSelected = new Coordinate(actualRow, this.actual.column());
        this.replaceSelected(nextSelected);
    }

    /**
     * This method changes the row index of the selected tile, making it point to one tile immediately below it.
     * If the tile below is outside the margins, the new row index will point to the first row tile index of the
     * calculated range.
     */
    @Override
    public void down() {
        this.selected.clear();
        if (!generatedRange) {
            this.setRange(this.playerManager.getActivePlayer().getCoord());
        }
        int actualRow = this.actual.row();
        actualRow = Math.min(maxRow, actualRow + 1);
        final Coordinate nextSelected = new Coordinate(actualRow, this.actual.column());
        this.replaceSelected(nextSelected);
    }

    /**
     * This method changes the column index of the selected tile, making it point to one tile immediately left to it.
     * If the left tile is outside the margins, the new column index will point to the last column tile index of the
     * calculated range.
     */
    @Override
    public void left() {
        this.selected.clear();
        if (!generatedRange) {
            this.setRange(this.playerManager.getActivePlayer().getCoord());
        }
        int actualColumn = this.actual.column();
        actualColumn = Math.max(minColumn, actualColumn - 1);
        final Coordinate nextSelected = new Coordinate(this.actual.row(), actualColumn);
        this.replaceSelected(nextSelected);
    }

    /**
     * This method changes the column index of the selected tile, making it point to one tile immediately right to it.
     * If the right tile is outside the margins, the new column index will point to the first column tile index of the
     * calculated range
     */
    @Override
    public void right() {
        this.selected.clear();
        if (!generatedRange) {
            this.setRange(this.playerManager.getActivePlayer().getCoord());
        }
        int actualColumn = this.actual.column();
        actualColumn = Math.min(maxColumn, actualColumn + 1);
        final Coordinate nextSelected = new Coordinate(this.actual.row(), actualColumn);
        this.replaceSelected(nextSelected);
    }

    /**
     * This method starts rotation procedure of the selected tile.
     */
    @Override
    public void primary() {
        this.done = true;
        this.selected.clear();
        this.board.rotateClockWiseTile(this.actual);
    }

    @Override
    public void secondary() { }

    @Override
    public void back() {
        this.done = true;
        this.selected.clear();
        this.generatedRange = false;
        this.board.rotateCounterClockWiseTile(this.actual);
    }

    @Override
    public boolean done() {
        return done;
    }

    @Override
    public Context getNextContext() {
        this.generatedRange = false;
        this.done = false;
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
