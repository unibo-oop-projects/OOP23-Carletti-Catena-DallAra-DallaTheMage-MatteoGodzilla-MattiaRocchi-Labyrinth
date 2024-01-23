package com.ccdr.labyrinth.game.loader;

import java.util.Map;
import java.util.HashMap;

public class StandardTile implements Tile {
    private TileType type;
    private Map<Direction, Boolean> pattern = new HashMap<>();
    
    @Override
    public TileType getType() {
        return type;
    }

    @Override
    public void setType(TileType type) {
        this.type = type;
    }

    @Override
    public boolean isOpen(Direction access) {
        return pattern.get(access);
    }

    @Override
    public void setPattern(Map<Direction, Boolean> readedPattern) {
        for(Direction dir : readedPattern.keySet()) {
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
        for(Direction e : pattern.keySet()) {
            rotated.put(e, pattern.get(Direction.next(e)));
        }
        this.setPattern(rotated);
    }
}
