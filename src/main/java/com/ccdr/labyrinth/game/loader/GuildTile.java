package com.ccdr.labyrinth.game.loader;
import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildTile implements Guild {

    private TileType type;
    public List<Item> missions;

    @Override
    public TileType getType() {
        return type;
    }

    @Override
    public void setType(TileType type) {
        this.type = type;
    }

    @Override
    public boolean state() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'state'");
    }

    @Override
    public void add_missions(Item mission) {
       missions.add(mission);
    }
    
}
