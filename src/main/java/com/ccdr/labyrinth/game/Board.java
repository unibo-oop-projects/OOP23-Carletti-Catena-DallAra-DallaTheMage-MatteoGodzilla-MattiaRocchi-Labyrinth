package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

import java.util.Map;

public interface Board {

    GuildTile getGuildTile();

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);

    void setMap(Map<Coordinate, Tile> map);

    Map<Coordinate, Tile> getMap();

    void insertTile(Coordinate coordinate, Tile tile);

    void shiftRow(int row, int movement);

    void shiftColumn(int column, int movement);

}