package com.ccdr.labyrinth.game.loader;

import java.util.HashMap;
import java.util.Map;

import java.util.Random;

public class TileCreator {
    private Map<Integer, Tile> standardPatternMap;
    
    public TileCreator() { 
        //TODO - JSON READING COMPONENT FILE AND SETTING UP PATTERN MAP
    }

    public Map<Coordinate, Tile> generate(int rowCapacity, int columnCapacity, int specialCapacity) {
        Map<Coordinate, Tile>  generatedTiles = new HashMap<>();
        for(int row = 0; row < rowCapacity; row++) {
            for(int column = 0; column < columnCapacity; column++) {
                Tile generatedTile = new StandardTile();
                Coordinate coordinates = new Coordinate(row, column);
                generatedTile.setPattern(pickRandomPatternTile().getPattern());
                generatedTiles.put(coordinates,generatedTile);
            }
        }

        // TODO - WAITING FOR ITEM SOURCES/SPECIAL TILES        
            
        return generatedTiles;
    }

    private Tile pickRandomPatternTile() {
        Random patternSelector = new Random();
        return standardPatternMap.get(patternSelector.nextInt(0, standardPatternMap.size()));
    }
}
