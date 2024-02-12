package com.ccdr.labyrinth.game.loader;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public Direction next() {
        switch (this) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            default:
                throw new IllegalStateException();
        }
    }

    public Direction prev() {
        switch (this) {
            case UP:
                return LEFT;
            case RIGHT:
                return UP;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            default:
                throw new IllegalStateException();
        }
    }
}
