package com.ccdr.labyrinth.game.loader.tiles;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

import java.util.ArrayList;
import java.util.List;

/*FIXME: MAKE GUILDTILE IMPLEMENTS TILE INTERFACE OR EXTENDS STANDARDTILE CLASS*/
public class GuildTile extends GenericTile {

    boolean state;
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
    public void onEnter(/* id player */) {
        if(state == false){
            /*materiali bonus in numero o fiso o randomico ma con range ristretto */
        }
    }

    @Override
    public void onExit() {
    }

}
