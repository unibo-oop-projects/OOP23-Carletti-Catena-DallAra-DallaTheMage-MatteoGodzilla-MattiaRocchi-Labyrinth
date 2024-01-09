package com.ccdr.labyrinth.game.loader;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    private int index;
    
    public int getIndex() {
        return index;
    }

    private Direction(int index) {
        this.index = index;
    }
}
