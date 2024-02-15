package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;

import java.util.List;
import java.util.ArrayList;

public class RotationContext implements Context {
    private final Board board;
    private final Coordinate player;
    private final List<Coordinate> inRange = new ArrayList<>();
    private int toDeselect;

    public RotationContext(Board board, Coordinate player) {
        this.board = board;
        this.player = player;
        for(int x = player.column()-1; x <= player.column()+1; x++) {
            for(int y = player.row()-1; y <= player.row()+1; y++) {
                Coordinate target = new Coordinate(y, x);
                if(board.getMap().containsKey(target)) {
                    inRange.add(target);
                }
            }
        }
    }

    @Override
    public void up() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'up'");
    }

    @Override
    public void down() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'down'");
    }

    @Override
    public void left() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'left'");
    }

    @Override
    public void right() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'right'");
    }

    @Override
    public void primary() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'primary'");
    }

    @Override
    public void secondary() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'secondary'");
    }

    @Override
    public void back() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'back'");
    }

    @Override
    public boolean done() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'done'");
    }

    @Override
    public Context getNextContext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextContext'");
    }

}
