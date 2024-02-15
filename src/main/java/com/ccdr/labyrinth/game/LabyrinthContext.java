package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class LabyrinthContext implements Context {
    private final Context playersManager; 
    private List<Coordinate> selected = new ArrayList<>();
    private Context activeContext, shifter, rotator;
    private boolean switcher;

    public LabyrinthContext(final Board board, final Context next) {
        this.playersManager = next;
        this.switcher = true;
        this.shifter = new ShifterContext(board, this.selected, this.playersManager);
        this.rotator = new RotationContext(board, this.playersManager, this.selected);
        this.activeContext = shifter;
    }

    @Override
    public void up() {
        this.activeContext.up();
    }

    @Override
    public void down() {
        this.activeContext.down();
    }

    @Override
    public void left() {
        this.activeContext.left();
    }

    @Override
    public void right() {
        this.activeContext.right();
    }

    @Override
    public void primary() {
        this.activeContext.primary();
    }

    @Override
    public void secondary() {
        if(switcher) {
            rotator.getNextContext();
        }
        this.activeContext = switcher ? rotator : shifter;
        this.selected.clear();
        switcher = !switcher;
    }

    @Override
    public void back() {
        this.activeContext.back();
    }

    @Override
    public boolean done() {
        return this.activeContext.done();
    }

    @Override
    public Context getNextContext() {
        return playersManager;
    }

    public List<Coordinate> getSelected() {
        return Collections.unmodifiableList(this.selected);
    }

}
