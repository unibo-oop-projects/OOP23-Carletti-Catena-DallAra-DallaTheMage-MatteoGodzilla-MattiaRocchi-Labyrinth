package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

import java.util.Map;
import java.util.Set;

public interface Board {

    GuildTile getGuildTile();

    Map<Coordinate, Tile> getMap();
    
    Set<Integer> getBlockedColumns();

    Set<Integer> getBlockedRows();

    int getHeight();

    int getWidth();

    void setHeight(int height);

    void setWidth(int width);

    void insertTile(Coordinate coordinate, Tile tile);

    void shiftRow(int row, boolean forward);

    void shiftColumn(int column, boolean forward);

    void discoverNearBy(Coordinate playerLocation, int radius);

    void addBlocked(Coordinate blocked);

    void rotateClockWiseTile(Coordinate actual);

    void rotateCounterClockWiseTile(Coordinate actual);
}