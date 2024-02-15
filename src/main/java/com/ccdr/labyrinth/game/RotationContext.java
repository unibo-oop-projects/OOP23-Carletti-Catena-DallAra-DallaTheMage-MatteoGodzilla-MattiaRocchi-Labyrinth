package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.player.PlayersManager;

import java.util.List;
import java.util.ArrayList;

public class RotationContext implements Context {
    private final Board board;
    private final List<Coordinate> selected;
    private final List<Coordinate> inRange = new ArrayList<>();
    private final Context playerManager;
    private boolean done;
    private boolean generatedRange;
    private Coordinate actual;
    private Coordinate player;
    private int minColumn, minRow, maxColumn, maxRow;
    
    public RotationContext(Board board, Context playerManager, List<Coordinate> selected) {
        this.board = board;
        this.selected = selected;
        this.playerManager = playerManager;
        this.setRange(playerManager);
        this.generatedRange = true;
        this.actual = inRange.get(0);
    }

    private void setRange(Context playerManager) {
        this.player = ((PlayersManager)playerManager).getActivePlayer().getCoord();
        minRow = Math.max(0, this.player.row() - 1);
        minColumn = Math.max(0, this.player.column()-1);
        maxRow = Math.min(this.player.row()+1, this.board.getHeight()-1);
        maxColumn = Math.min(this.player.column()+1, this.board.getWidth()-1);

        for(int x = minColumn; x <= maxColumn; x++) {
            for(int y = minRow; y <= maxRow; y++) {
                Coordinate target = new Coordinate(y, x);
                if(board.getMap().containsKey(target)) {
                    inRange.add(target);
                }
            }
        }
    }

    private void replaceSelected(Coordinate nextSelected) {
        this.selected.remove(actual);
        this.actual = nextSelected;
        this.selected.add(actual);
    }

    @Override
    public void up() {
        if(!generatedRange) {
            this.setRange(this.playerManager);
        }
        int actualRow = this.actual.row();
        actualRow = Math.max(minRow, actualRow-1);
        Coordinate nextSelected = new Coordinate(actualRow, this.actual.column());
        this.replaceSelected(nextSelected);
    }

    @Override
    public void down() {
        if(!generatedRange) {
            this.setRange(this.playerManager);
        }
        int actualRow = this.actual.row();
        actualRow = Math.min(maxRow, actualRow+1);
        Coordinate nextSelected = new Coordinate(actualRow, this.actual.column());
        this.replaceSelected(nextSelected);
    }

    @Override
    public void left() {
        if(!generatedRange) {
            this.setRange(this.playerManager);
        }
        int actualColumn = this.actual.column();
        actualColumn = Math.max(minColumn, actualColumn-1);
        Coordinate nextSelected = new Coordinate(this.actual.row(), actualColumn);
        this.replaceSelected(nextSelected);
    }

    @Override
    public void right() {
        if(!generatedRange) {
            this.setRange(this.playerManager);
        }
        int actualColumn = this.actual.column();
        actualColumn = Math.min(maxColumn, actualColumn+1);
        Coordinate nextSelected = new Coordinate(this.actual.row(), actualColumn);
        this.replaceSelected(nextSelected);
    }

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
        this.board.rotateCounterClockWiseTile(this.actual);
    }

    @Override
    public boolean done() {
        boolean exitCondition = done;
        this.done = false;
        this.generatedRange = false;
        return exitCondition;
    }

    @Override
    public Context getNextContext() {
        return this;
    }

}
