package com.ccdr.labyrinth.game.loader;

import java.util.Map;

public interface Board {

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);

    void setMap(Map<Coordinate, Tile> map);

    Map<Coordinate, Tile> getMap();

    void insertTile(Coordinate coordinate, Tile tile);
    
    void remap(Map<Integer, Tile> generated);

    Map<Coordinate, Tile> shiftRow(int row);

    Map<Coordinate, Tile> shiftColumn(int column);

}