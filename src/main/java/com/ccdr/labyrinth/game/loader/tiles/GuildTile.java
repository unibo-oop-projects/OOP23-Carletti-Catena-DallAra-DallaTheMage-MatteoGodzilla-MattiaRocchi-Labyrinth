package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Material;

import java.util.ArrayList;
import java.util.List;

public class GuildTile extends GenericTile {

    private boolean state;
    private List<Item> missions = new ArrayList<>();
    private List<Material> materialpresents = new ArrayList<>();

    public GuildTile(final int nPlayer) {
        this.discover();
    }

    public List<Material> getMaterialPresents() {
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
