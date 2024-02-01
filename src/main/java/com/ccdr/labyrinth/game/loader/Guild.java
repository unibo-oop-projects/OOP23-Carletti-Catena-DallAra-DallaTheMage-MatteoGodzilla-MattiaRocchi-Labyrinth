package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.game.loader.tiles.TileType;

import java.util.List;

public interface Guild {
    
    boolean state();

    void setType(TileType type);

    TileType getType();
    
    void add_missions(Item mission);

}
