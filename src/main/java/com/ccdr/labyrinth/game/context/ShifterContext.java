package com.ccdr.labyrinth.game.context;

import java.util.List;

import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.util.Coordinate;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.Collections;

public final class ShifterContext implements Context {
    private final Board board;
    private final PlayersContext players;
    private final List<Coordinate> selected;
    private int selectedRow;
    private int selectedColumn;
    private int indexSelected;
    private boolean done;
    private boolean isRow;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public ShifterContext(final Board board, final PlayersContext players) {
        this.board = board;
        this.players = players;
        this.selected = new ArrayList<>();
        this.selected.clear();
        this.selectRow(0);
        this.isRow = true;
    }

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

    @Override
    public void up() {
        do {
            this.selectRow(this.calculateCorrectIndex(--this.selectedRow, this.board.getHeight()));
        } while (this.board.getBlockedRows().contains(selectedRow));
    }

    @Override
    public void down() {
        do {
            this.selectRow(this.calculateCorrectIndex(++this.selectedRow, this.board.getHeight()));
        } while (this.board.getBlockedRows().contains(selectedRow));
    }

    @Override
    public void left() {
        do {
            this.selectColumn(this.calculateCorrectIndex(--this.selectedColumn, this.board.getWidth()));
        } while (this.board.getBlockedColumns().contains(selectedColumn));
    }

    @Override
    public void right() {
        do {
            this.selectColumn(this.calculateCorrectIndex(++this.selectedColumn, this.board.getWidth()));
        } while (this.board.getBlockedColumns().contains(selectedColumn));
    }

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

    @Override
    public boolean done() {
        final boolean exitCondition = done;
        this.done = false;
        return exitCondition;
    }

    @Override
    public Context getNextContext() {
        return this;
    }

    public List<Coordinate> selectedTiles() {
        return Collections.unmodifiableList(selected);
    }
}
