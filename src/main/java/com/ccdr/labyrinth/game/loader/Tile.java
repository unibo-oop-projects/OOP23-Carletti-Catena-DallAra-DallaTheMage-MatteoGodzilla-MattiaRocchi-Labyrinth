package com.ccdr.labyrinth.game.loader;

import java.util.Map;

public interface Tile {
    
    boolean isOpen(Direction access);

    void rotate();

    void setPattern(Map<Direction, Boolean> readedPattern);

    TileType getType();

    Map<Direction, Boolean> getPattern();
}