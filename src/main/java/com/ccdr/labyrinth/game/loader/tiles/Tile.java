package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.loader.Direction;
import com.ccdr.labyrinth.game.player.Player;

import java.util.Map;

public interface Tile {

    boolean isOpen(Direction access);
    
    void onEnter(Player player);

    void onExit(Player player);

    void rotate();

    void setPattern(Map<Direction, Boolean> readedPattern);

    Map<Direction, Boolean> getPattern();
}