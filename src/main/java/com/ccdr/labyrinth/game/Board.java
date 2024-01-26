package com.ccdr.labyrinth.game;

import java.util.Map;

import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Tile;

public interface Board {

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);

    void setMap(Map<Coordinate, Tile> map);

    Map<Coordinate, Tile> getMap();

    void insertTile(Coordinate coordinate, Tile tile);

    void remap(Map<Integer, Tile> generated);

    Map<Coordinate, Tile> shiftRow(int row, int movement);

    Map<Coordinate, Tile> shiftColumn(int column, int movement);

}