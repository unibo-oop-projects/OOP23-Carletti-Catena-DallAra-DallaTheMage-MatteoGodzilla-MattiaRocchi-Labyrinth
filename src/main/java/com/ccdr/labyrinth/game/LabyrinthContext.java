package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;

import java.util.Set;
import java.util.HashSet;

public class LabyrinthContext implements Context {
    private Set<Coordinate> selected = new HashSet<>();
    private Context activeContext, shifter, rotator;
    private boolean switcher;

    public LabyrinthContext(final Board board, final Coordinate playerLocation) {
        this.switcher = true;
        this.shifter = new ShifterContext(board, selected);
        this.rotator = new RotationContext(board, playerLocation, selected);
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
        this.activeContext = switcher ? shifter : rotator;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextContext'");
    }

    public Set<Coordinate> getSelected() {
        return Set.copyOf(this.selected);
    }

}
