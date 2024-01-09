package com.ccdr.labyrinth.game;

import java.util.Map;

// TODO - OBJECTIVES MANAGEMENT COMPONENT
//import com.ccdr.labyrinth.game.loader.Objective;
import com.ccdr.labyrinth.game.loader.Tile;
import com.ccdr.labyrinth.game.loader.TileCreator;
import com.ccdr.labyrinth.game.loader.Coordinate;

public class GameLoader {
    //private final int OBJECTIVES_NUM = 10;
    private final int LABYRINTH_HEIGHT = 30;
    private final int LABYRINTH_WIDTH = 30;
    private final int SPECIAL_TILES = 8;
    
    public Map<Coordinate, Tile> generateTiles() {
        TileCreator tileCreator = new TileCreator();
        return tileCreator.generate( LABYRINTH_HEIGHT, LABYRINTH_WIDTH, SPECIAL_TILES);
    }
}
