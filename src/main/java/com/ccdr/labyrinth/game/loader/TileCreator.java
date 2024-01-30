package com.ccdr.labyrinth.game.loader;

import java.util.Map;

public interface TileCreator {
    
    public Map<Coordinate, Tile> generateTiles(int height, int width, int sourceQuantity);

}
