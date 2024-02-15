package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;

import java.util.Set;
import java.util.HashSet;

public class LabyrinthContext implements Context {
    private final Context next; 
    private Set<Coordinate> selected = new HashSet<>();
    private Context activeContext, shifter, rotator;
    private boolean switcher;

    public LabyrinthContext(final Board board, final Coordinate playerLocation, final Context next) {
        this.next = next;
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
        if(switcher) {
            System.out.println("SHIFTER");
        }
        else {
            System.out.println("ROTATOR");
        }
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
        return next;
    }

    public Set<Coordinate> getSelected() {
        return Set.copyOf(this.selected);
    }

}
