package com.ccdr.labyrinth.game.tiles;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

import com.ccdr.labyrinth.game.util.Direction;

/**
 * A GenericTile is an abstract implementation of the Tile interface that implements all common methods
 * and represents the super class of all tile variants.
 */
public abstract class GenericTile implements Tile {
    private final Map<Direction, Boolean> pattern = new HashMap<>();
    private boolean discovered;

    @Override
    public final void discover() {
        this.discovered = true;
    }

    @Override
    public final boolean isDiscovered() {
        return discovered;
    }

    @Override
    public final boolean isOpen(final Direction access) {
        return pattern.get(access);
    }

    @Override
    public final void setPattern(final Map<Direction, Boolean> readedPattern) {
        for (final var e : readedPattern.entrySet()) {
            pattern.put(e.getKey(), e.getValue());
        }
    }

    @Override
    public final Map<Direction, Boolean> getPattern() {
        return Map.copyOf(pattern);
    }

    @Override
    public final void rotate(final boolean clockwise) {
        final Map<Direction, Boolean> rotated = new HashMap<>();
        Function<Direction, Direction> action;
        if (clockwise) {
            action = (e) -> e.prev();
        } else {
            action = (e) -> e.next();
        }
        for (final Direction e : pattern.keySet()) {
            rotated.put(e, pattern.get(action.apply(e)));
        }
        this.setPattern(rotated);
    }

}
