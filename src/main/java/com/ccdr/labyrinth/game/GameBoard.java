package com.ccdr.labyrinth.game;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.function.BiFunction;

import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.tiles.GuildTile;
import com.ccdr.labyrinth.game.tiles.Tile;
import com.ccdr.labyrinth.game.util.Coordinate;

/**
 * A class that implements the interface Board, and represents the implementation of a board.
 */
public final class GameBoard implements Board {
    private Map<Coordinate, Tile> map = new HashMap<>();
    private final Set<Integer> blockedRows = new HashSet<>();
    private final Set<Integer> blockedColumns = new HashSet<>();
    private int height, width;

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(final int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(final int width) {
        this.width = width;
    }

    @Override
    public Map<Coordinate, Tile> getMap() {
        return Collections.unmodifiableMap(this.map);
    }

    @Override
    public Set<Integer> getBlockedColumns() {
        return Set.copyOf(blockedColumns);
    }

    @Override
    public void addBlocked(final Coordinate blocked) {
        this.blockedRows.add(blocked.row());
        this.blockedColumns.add(blocked.column());
    }

    @Override
    public Set<Integer> getBlockedRows() {
        return Set.copyOf(blockedRows);
    }

    @Override
    public void insertTile(final Coordinate coordinate, Tile tile) {
        map.put(coordinate, tile);
    }

    private int getNext(int actual, final int size) {
        if (actual >= size) {
             return 0;
        } else {
             return actual+1;
        }
    }

    private int getPrev(int actual, int size) {
        if (actual <= 0) {
             return size;
        } else {
             return actual-1;
        }
    }

    @Override
    public void shiftRow(final int row, final boolean forward) {
        final BiFunction<Integer, Integer, Integer> operation = forward ? (i, size) -> getNext(i, size) : (i, size) -> getPrev(i, size);
        final Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(row, index);
            shiftedPointer = new Coordinate(row, operation.apply(index, this.width-1));
            shifted.put(shiftedPointer, this.map.get(pointer));
        }
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(row, index);
            this.map.replace(pointer, shifted.get(pointer));
        }
    }

    @Override
    public void shiftColumn(final int column, final boolean forward) {
        final BiFunction<Integer, Integer, Integer> operation = forward ? (i, size) -> getNext(i, size) : (i, size) -> getPrev(i, size);
        final Map<Coordinate, Tile> shifted = new HashMap<>();
        Coordinate pointer, shiftedPointer;
        int index;
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(index, column);
            shiftedPointer = new Coordinate(operation.apply(index, this.height-1), column);
            shifted.put(shiftedPointer, this.map.get(pointer));
        }
        for (index = 0; index < this.width; index++) {
            pointer = new Coordinate(index, column);
            this.map.replace(pointer, shifted.get(pointer));
        }
    }

    @Override
    public GuildTile getGuildTile() {
        final Coordinate center = new Coordinate(width / 2, height / 2);
        if (map.containsKey(center)) {
            return (GuildTile) map.get(center);
        }
        else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void discoverNearBy(final Coordinate playerLocation, final int radius) {
        for (int x = playerLocation.column() - radius; x <= playerLocation.column() + radius; x++) {
            for (int y = playerLocation.row() - radius; y <= playerLocation.row() + radius; y++) {
                Coordinate target = new Coordinate(y, x);
                if (map.containsKey(target) && !map.get(target).isDiscovered()) {
                    map.get(target).discover();
                }
            }
        }
    }

    @Override
    public void rotateClockWiseTile(Coordinate actual) {
        this.map.get(actual).rotate((e) -> e.prev());
    }

    @Override
    public void rotateCounterClockWiseTile(Coordinate actual) {
        this.map.get(actual).rotate((e) -> e.next());
    }
}
