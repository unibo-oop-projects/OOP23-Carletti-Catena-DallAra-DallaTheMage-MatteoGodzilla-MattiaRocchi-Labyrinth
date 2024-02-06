package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.loader.Direction;

import java.util.Map;

public interface Tile {

    boolean isOpen(Direction access);
    
    void onEnter();

    void onExit();

    void rotate();

    void setPattern(Map<Direction, Boolean> readedPattern);

    TileType getType();

    Map<Direction, Boolean> getPattern();
}