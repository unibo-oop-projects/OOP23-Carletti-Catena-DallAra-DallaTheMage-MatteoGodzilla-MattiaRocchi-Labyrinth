package com.ccdr.labyrinth.game.loader;

import java.util.Map;

public interface Tile {
    boolean isOpen(Direction access);

    void setPattern(Map<Direction, Boolean> readedPattern);

    void setType(TileType type);

    TileType getType();

    Map<Direction, Boolean> getPattern();

    void rotate();
    
}