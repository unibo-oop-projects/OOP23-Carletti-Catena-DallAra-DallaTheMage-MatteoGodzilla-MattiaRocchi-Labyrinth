package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Material;

import java.util.ArrayList;
import java.util.List;

public class GuildTile extends GenericTile {

    private boolean state;
    private int maxPoints;
    private List<Item> missions = new ArrayList<>();
    private List<Material> materialpresents = new ArrayList<>();

    public GuildTile(final int nPlayer, int maxPoints) {
        this.maxPoints = maxPoints;
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
            player.increasePoints(maxPoints / 2);
        }

        this.state = true;
    }

    @Override
    public void onExit(final Player player) {
    }
}
