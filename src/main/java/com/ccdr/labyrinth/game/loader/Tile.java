package com.ccdr.labyrinth.game.loader;

import java.util.Map;

public interface Tile {
    boolean isMovable();
    boolean isOpen(Direction access);
    boolean getPath(Direction way);
    void setPattern(Map<Integer, Boolean> readedPattern);
    Map<Integer, Boolean> getPattern();
    void rotate();
}