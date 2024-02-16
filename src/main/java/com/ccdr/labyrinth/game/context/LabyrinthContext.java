package com.ccdr.labyrinth.game.context;

import java.util.List;

import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.util.Coordinate;

public class LabyrinthContext implements Context {
    private enum Subcontext{
        SHIFTER,
        ROTATOR
    }

    private final PlayersContext playersManager;
    private final ShifterContext shifter;
    private final RotationContext rotator;
    private boolean switcher;
    private Subcontext active;

    public LabyrinthContext(final Board board, final PlayersContext next) {
        this.playersManager = next;
        this.switcher = true;
        this.shifter = new ShifterContext(board, this.playersManager);
        this.rotator = new RotationContext(board, this.playersManager);
        this.active = Subcontext.SHIFTER;
    }

    @Override
    public void up() {
        this.getSubcontext().up();
    }

    @Override
    public void down() {
        this.getSubcontext().down();
    }

    @Override
    public void left() {
        this.getSubcontext().left();
    }

    @Override
    public void right() {
        this.getSubcontext().right();
    }

    @Override
    public void primary() {
        this.getSubcontext().primary();
    }

    @Override
    public void secondary() {
        switch(active){
            case ROTATOR:
                active = Subcontext.SHIFTER;
                break;
            default:
                active = Subcontext.ROTATOR;
                break;
        }
        if(switcher) {
            rotator.getNextContext();
        }
        this.getSubcontext();
        switcher = !switcher;
    }

    @Override
    public void back() {
        this.getSubcontext().back();
    }

    @Override
    public boolean done() {
        return this.getSubcontext().done();
    }

    @Override
    public Context getNextContext() {
        this.getSubcontext().getNextContext();
        return playersManager;
    }

    public List<Coordinate> getSelected() {
        switch (active) {
            case ROTATOR:
                return rotator.selectedTiles();
            default:
                return shifter.selectedTiles();
        }
    }

    private Context getSubcontext(){
        switch (active) {
            case ROTATOR:
                return rotator;
            default:
                return shifter;
        }
    }

}
