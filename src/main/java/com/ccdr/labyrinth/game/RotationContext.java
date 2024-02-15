package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;

public class RotationContext implements Context {
    private final Board board;
    private final Coordinate player;
    private final Set<Coordinate> selected;
    private final List<Coordinate> inRange = new ArrayList<>();
    private boolean done;
    private Coordinate actual;

    public RotationContext(Board board, Coordinate player, Set<Coordinate> selected) {
        this.board = board;
        this.player = player;
        this.selected = selected;
        for(int x = player.column() - 1; x <= player.column() + 1; x++) {
            for(int y = player.row() - 1; y <= player.row() + 1; y++) {
                Coordinate target = new Coordinate(y, x);
                if(board.getMap().containsKey(target)) {
                    inRange.add(target);
                }
            }
        }
        this.actual = inRange.get(0);
    }

    private int calculateCorrectIndex(Function<Integer, Integer> rule, int i) {    
        return rule.apply(i);
    }

    private void replaceSelected(Coordinate nextSelected) {
        this.selected.remove(actual);
        this.actual = nextSelected;
        this.selected.add(actual);
    }

    @Override
    public void up() {
        int actualRow = this.actual.row();
        int newIndex = this.calculateCorrectIndex((i) -> i < player.row() - 1 ?  player.row() + 1 : i, --actualRow);
        Coordinate nextSelected = new Coordinate(newIndex, this.actual.column());
        this.replaceSelected(nextSelected);
    }

    @Override
    public void down() {
        int actualRow = this.actual.row();
        int newIndex = this.calculateCorrectIndex((i) -> i > player.row() + 1 ?  player.row() - 1 : i, ++actualRow);
        Coordinate nextSelected = new Coordinate(newIndex, this.actual.column());
        this.replaceSelected(nextSelected);
    }

    @Override
    public void left() {
        int actualColumn = this.actual.column();
        int newIndex = this.calculateCorrectIndex((i) -> i < player.column() - 1 ?  player.column() + 1 : i, --actualColumn);
        Coordinate nextSelected = new Coordinate(this.actual.row(), newIndex);
        this.replaceSelected(nextSelected);
    }

    @Override
    public void right() {
        int actualColumn = this.actual.column();
        int newIndex =  this.calculateCorrectIndex((i) -> i > player.column() + 1 ?  player.column() - 1 : i, ++actualColumn);
        Coordinate nextSelected = new Coordinate(this.actual.row(), newIndex);
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
        return done;
    }

    @Override
    public Context getNextContext() {
        // TODO: FOR NOW DO NOTHING
        return this;
    }

}
