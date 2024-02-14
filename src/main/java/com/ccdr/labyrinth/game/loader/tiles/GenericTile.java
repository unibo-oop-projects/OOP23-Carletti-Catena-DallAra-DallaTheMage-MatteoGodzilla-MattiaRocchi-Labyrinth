package com.ccdr.labyrinth.game.loader.tiles;
import com.ccdr.labyrinth.game.loader.Direction;

import java.util.Map;
import java.util.HashMap;

public abstract class GenericTile implements Tile {
    private Map<Direction, Boolean> pattern = new HashMap<>();
    private boolean discovered;

    public void discover() {
        this.discovered = true;
    }

    @Override
    public boolean isDiscovered() {
        return discovered;
    }

    @Override
    public boolean isOpen(final Direction access) {
        return pattern.get(access);
    }

    @Override
    public void setPattern(final Map<Direction, Boolean> readedPattern) {
        for (Direction dir : readedPattern.keySet()) {
            pattern.put(dir, readedPattern.get(dir));
        }
    }

    @Override
    public Map<Direction, Boolean> getPattern() {
        return Map.copyOf(pattern);
    }

    @Override
    public void rotate() {
        Map<Direction, Boolean> rotated = new HashMap<>();
        for (Direction e : pattern.keySet()) {
            rotated.put(e, pattern.get(e.next()));
        }
        this.setPattern(rotated);
    }

}
