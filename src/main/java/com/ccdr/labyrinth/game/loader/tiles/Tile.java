package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.loader.Direction;
import com.ccdr.labyrinth.game.player.Player;

import java.util.Map;

public interface Tile {

    boolean isOpen(Direction access);

    /**
     * Tile that want to always be visible should NOT override this and just call discover() on the constructor.
     */
    boolean isDiscovered();

    void onEnter(Player player);

    void onExit(Player player);

    void rotate();

    void setPattern(Map<Direction, Boolean> readedPattern);

    Map<Direction, Boolean> getPattern();

    /**
     * Tile that want to always be visible should call this instead of overriding isDiscovered.
     */
    void discover();
}