package com.ccdr.labyrinth.game.loader;

import java.util.Map;
import java.util.HashMap;

public abstract class TileImpl implements Tile {
    private Map<Integer, Boolean> pattern = new HashMap<>();
    
    protected abstract boolean conditionsVerified();

    @Override
    public boolean isMovable()
    {
        return conditionsVerified();
    }

    @Override
    public boolean getPath(Direction way) {
        return pattern.get(way.getIndex());
    }

    @Override
    public boolean isOpen(Direction access) {
        return pattern.get(access.getIndex());
    }

    @Override
    public void setPattern(Map<Integer, Boolean> readedPattern) {
        for(Integer dir : readedPattern.keySet()) {
            pattern.put(dir, readedPattern.get(dir));
        }
    }

    @Override
    public Map<Integer, Boolean> getPattern() {
        return Map.copyOf(pattern);
    }

    @Override
    public void rotate() {
        Boolean oldValue = null, newValue = null;
        for(int dir = 0; dir <= pattern.size(); dir++) {
           if(dir < pattern.size()) {
                if(oldValue == null) {
                    oldValue = pattern.get(dir+1);
                    newValue = oldValue;
                    pattern.replace(dir+1, pattern.get((dir)));
                }
                else {
                    newValue = pattern.get(dir+1);
                    pattern.replace(dir+1, oldValue);
                    oldValue = newValue;
                }
           }
           else {
                pattern.replace(0, oldValue);
           }
            
        }
    }
}
