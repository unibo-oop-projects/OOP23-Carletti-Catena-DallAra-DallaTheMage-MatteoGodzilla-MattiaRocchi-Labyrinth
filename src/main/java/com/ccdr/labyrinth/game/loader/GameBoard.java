package com.ccdr.labyrinth.game.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameBoard implements Board {
    private Map<Coordinate, Tile> map;
    private int height, width;
    
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMap(Map<Coordinate, Tile> map) {
        this.map = map;
    }

    @Override
    public Map<Coordinate, Tile> getMap() {
        return Map.copyOf(this.map);
    }

    @Override
    public void insertTile(Coordinate coordinate, Tile tile) {
        map.put(coordinate, tile);
    }

    public void remap(Map<Integer, Tile> generated) {
        Random seed = new Random();
        Coordinate coordinate; 
        for(Tile t : generated.values()) {
            do {
                coordinate = new Coordinate(seed.nextInt(0, this.height), seed.nextInt(0, this.width));
            } while(!this.map.containsKey(coordinate));
            this.insertTile(coordinate, t);
        }
    }

    @Override
    public Map<Coordinate, Tile> shiftRow(int row) {
        Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        for(index = 0; index < this.width; index++) {
            pointer = new Coordinate(row, index);
            shiftedPointer = new Coordinate(row, getNext(index, this.width));
            shifted.put(shiftedPointer, this.map.get(pointer));
        }

        for(index = 0; index < this.width; index++) {
            pointer = new Coordinate(row, index);
            this.map.replace(pointer, shifted.get(pointer));
        }

        return Map.copyOf(this.map); // TODO: CAN BE A GOOD IDEA MAKE THIS METHOD VOID AND SIMPLY UPDATE THE MAP
    }

    private int getNext(int actual, int size) {
       if(actual >= size) {
            return 0;
       } else {
            return ++actual;
       }
    }

    @Override
    public Map<Coordinate, Tile> shiftColumn(int column) {
        Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        for(index = 0; index < this.width; index++) {
            pointer = new Coordinate(index, column );
            shiftedPointer = new Coordinate(getNext(index, this.height), column);
            shifted.put(shiftedPointer, this.map.get(pointer));
        }

        for(index = 0; index < this.width; index++) {
            pointer = new Coordinate(index, column);
            this.map.replace(pointer, shifted.get(pointer));
        }

        return Map.copyOf(this.map); // TODO: CAN BE A GOOD IDEA MAKE THIS METHOD VOID AND SIMPLY UPDATE THE MAP
    } 
}
