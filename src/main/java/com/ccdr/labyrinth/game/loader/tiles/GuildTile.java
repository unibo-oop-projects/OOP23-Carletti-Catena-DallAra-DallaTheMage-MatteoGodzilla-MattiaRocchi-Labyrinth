package com.ccdr.labyrinth.game.loader.tiles;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GuildTile extends GenericTile {

    boolean state;
    public List<Item> missions = new ArrayList();
    public List<Material> materialpresents = new ArrayList();
    public GetMissions getM = new GetMissions();

    public GuildTile(final int nPlayer) {
        for (int i = 0; i < nPlayer * 2; i++) {
            missions.add(getM.generateMission());
        }

        materialpresents.addAll(getM.materialPresents());
    }

    public List<Material> getMaterialPresents(){
        return materialpresents;
    }

    public List<Item> returnListOfMissions() {
        return missions;
    }

    @Override
    public void onEnter(final Player player) {
        if (state == false) {
            /*materiali bonus in numero o fiso o randomico ma con range ristretto */
        }
    }

    @Override
    public void onExit(final Player player) {
    }

}
