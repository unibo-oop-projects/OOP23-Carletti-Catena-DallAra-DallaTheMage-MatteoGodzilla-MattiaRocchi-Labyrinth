package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.game.Board;
import com.ccdr.labyrinth.game.loader.tiles.Tile;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.function.BiFunction;

public final class GameBoard implements Board {
    private final Map<Coordinate, Tile> map = new HashMap<>();
    private final Set<Integer> blockedRows = new HashSet<>();
    private final Set<Integer> blockedColumns = new HashSet<>();
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
    public Map<Coordinate, Tile> getMap() {
        return Map.copyOf(this.map);
    }

    @Override
    public Set<Integer> getBlockedColumns() {
        return Set.copyOf(blockedColumns);
    }

    @Override
    public void addBlocked(Coordinate blocked) {
        this.blockedRows.add(blocked.row());
        this.blockedColumns.add(blocked.column());
    }

    @Override
    public Set<Integer> getBlockedRows() {
        return Set.copyOf(blockedRows);
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

    private int getPrev(int actual, int size) {
        if (actual < 0) {
             return size-1;
        } else {
             return --actual;
        }
    }

    @Override
    public void shiftRow(int row, boolean forward) {
        BiFunction<Integer, Integer, Integer> operation = forward ? (i, size) -> getNext(i, size) : (i, size) -> getPrev(i, size);
        Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(row, index);
            shiftedPointer = new Coordinate(row, operation.apply(index, this.width));
            shifted.put(shiftedPointer, this.map.get(pointer));
        }
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(row, index);
            this.map.replace(pointer, shifted.get(pointer));
        }
    }

    @Override
    public void shiftColumn(int column, boolean forward) {
        BiFunction<Integer, Integer, Integer> operation = forward ? (i, size) -> getNext(i, size) : (i, size) -> getPrev(i, size);
        Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(index, column);
            shiftedPointer = new Coordinate(operation.apply(index, this.height), column);
            shifted.put(shiftedPointer, this.map.get(pointer));
        }
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(index, column);
            this.map.replace(pointer, shifted.get(pointer));
        }
    }

    @Override
    public GuildTile getGuildTile() {
        Coordinate center = new Coordinate(width / 2, height / 2);
        if(map.containsKey(center)) {
            return (GuildTile)map.get(center);
        }
        else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void discoverNearBy(Coordinate playerLocation, int radius) {
        for(int x = playerLocation.column() - radius; x <= playerLocation.column() + radius; x++) {
            for(int y = playerLocation.row() - radius; y <= playerLocation.row() + radius; y++) {
                Coordinate target = new Coordinate(y, x);
                if(map.containsKey(target) && !map.get(target).isDiscovered()) {
                    map.get(target).discover();
                }
            }
        }
    }

    @Override
    public void rotateClockWiseTile(Coordinate actual) {
        this.map.get(actual).rotate((e) -> e.next());
    }

    @Override
    public void rotateCounterClockWiseTile(Coordinate actual) {
        this.map.get(actual).rotate((e) -> e.prev());
    }
}
