package com.ccdr.labyrinth.game.loader;

import java.util.Map;

//TODO: move this so it's a layer above in the package (not inside loader)
public interface Board {

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);

    void setMap(Map<Coordinate, Tile> map);

    Map<Coordinate, Tile> getMap();

    void insertTile(Coordinate coordinate, Tile tile);

    void remap(Map<Integer, Tile> generated);

    //TODO:change this so it returns void and has a second parameter specifying how many to shift
    Map<Coordinate, Tile> shiftRow(int row);

    //TODO:change this so it returns void and has a second parameter specifying how many to shift
    Map<Coordinate, Tile> shiftColumn(int column);

}