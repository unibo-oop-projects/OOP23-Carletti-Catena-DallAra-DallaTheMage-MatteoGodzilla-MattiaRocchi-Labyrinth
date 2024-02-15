package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.loader.Direction;
import com.ccdr.labyrinth.game.player.Player;

import java.util.Map;
import java.util.function.Function;

public interface Tile {

    boolean isOpen(Direction access);

    /**
     * Tile that want to always be visible should NOT override this and just call discover() on the constructor.
     * @return tru if the tile is discovered, otherwise false
     */
    boolean isDiscovered();

    boolean isSelected();

    void onEnter(Player player);

    void onExit(Player player);

    void rotate(Function<Direction, Direction> rotation);

    void setPattern(Map<Direction, Boolean> readedPattern);

    /**
     * Tile that want to always be visible should call this instead of overriding isDiscovered.
     */
    void discover();

    void select();

    void deselect();

    Map<Direction, Boolean> getPattern();
}
