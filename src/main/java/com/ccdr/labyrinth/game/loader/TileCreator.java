package com.ccdr.labyrinth.game.loader;

import java.util.Map;

public interface TileCreator {
    
    public Map<Integer, Tile> generateTiles(int normalQuantity, int sourceQuantity);

}
