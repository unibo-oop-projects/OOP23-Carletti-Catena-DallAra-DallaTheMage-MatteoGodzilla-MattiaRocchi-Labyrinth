package com.ccdr.labyrinth.game.loader.tiles;
import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Guild;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*FIXME: MAKE GUILDTILE IMPLEMENTS TILE INTERFACE OR EXTENDS STANDARDTILE CLASS*/
public class GuildTile extends GenericTile implements Guild {

    private TileType type;
    public List<Item> missions;
    private boolean control;

    private GuildTile(){
        //TODO: missing reference to player count
        for(int i = 0; i </*Player in game*/ 2; i++){
            GetMissions.setTypeMatItem();
            control = GetMissions.control(missions);
            GetMissions.setTypeMatItem();
            if(!control){
                i--;
            }
            else{
                missions.add(GetMissions.Get_mis());
            }
        }
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
    public void add_missions(Item mission) {
       missions.add(mission);
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
