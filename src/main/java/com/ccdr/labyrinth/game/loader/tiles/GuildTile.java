package com.ccdr.labyrinth.game.loader.tiles;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Guild;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

import java.util.ArrayList;
import java.util.List;

/*FIXME: MAKE GUILDTILE IMPLEMENTS TILE INTERFACE OR EXTENDS STANDARDTILE CLASS*/
public class GuildTile extends GenericTile implements Guild {

    private TileType type;
    public List<Item> missions = new ArrayList();
    public GetMissions getM = new GetMissions();

    public GuildTile(int nPlayer){
        for(int i = 0; i < nPlayer * 2; i++){
            missions.add(getM.generateMission());
        }
    }

    public List<Item> returnListOfMissions(){
        return missions;
    }

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
    public void onEnter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onExit'");
    }

}
