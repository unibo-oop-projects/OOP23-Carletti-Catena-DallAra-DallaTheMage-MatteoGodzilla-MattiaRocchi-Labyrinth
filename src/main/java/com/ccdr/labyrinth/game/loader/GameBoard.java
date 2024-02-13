package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.game.Board;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class GameBoard implements Board {
    private Map<Coordinate, Tile> map = new HashMap<>();
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

    private int getNext(int actual, int size) {
        if (actual >= size) {
             return 0;
        } else {
             return ++actual;
        }
    }

    @Override
    public void shiftRow(int row, int movement) {
        Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        while (movement-- > 0) {
            for (index = 0; index < this.width; index++) {
                pointer = new Coordinate(row, index);
                shiftedPointer = new Coordinate(row, getNext(index, this.width));
                shifted.put(shiftedPointer, this.map.get(pointer));
            }
            for (index = 0; index < this.width; index++) {
                pointer = new Coordinate(row, index);
                this.map.replace(pointer, shifted.get(pointer));
            }
        }
    }

    @Override
    public void shiftColumn(int column, int movement) {
        Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        while (movement-- > 0) {
            for (index = 0; index < this.width; index++) {
                pointer = new Coordinate(index, column);
                shiftedPointer = new Coordinate(getNext(index, this.height), column);
                shifted.put(shiftedPointer, this.map.get(pointer));
            }
            for (index = 0; index < this.width; index++) {
                pointer = new Coordinate(index, column);
                this.map.replace(pointer, shifted.get(pointer));
            }
        }
    }

    @Override
    public Optional<GuildTile> getGuildTile() {
        Coordinate center = new Coordinate(Math.round(width/2), Math.round(height/2));
        return map.containsKey(center) ? Optional.of((GuildTile)map.get(center)) : Optional.empty();
    }
}
