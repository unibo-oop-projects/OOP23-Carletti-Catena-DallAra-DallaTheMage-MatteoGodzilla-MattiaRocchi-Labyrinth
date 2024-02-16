package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.player.PlayersManager;

import java.util.List;
import java.util.ArrayList;

/**
 * Class for the context of the guild and the implements of the menu missions.
 */
public class GuildContext implements Context {

    private final List<Item> missions = new ArrayList<>();
    private final List<Item> missionsCom = new ArrayList<>();
    private final List<Material> materialpresents = new ArrayList<>();
    private final GetMissions getM = new GetMissions();
    private int menuIndex;
    private PlayersManager players;
    private boolean done;
    private Context following;

    /**
     * Class costructor.
     * @param nPlayer for the number of missions to generate
     */
    public GuildContext(final int nPlayer) {
        for (int i = 0; i < nPlayer * 2; i++) {
            missions.add(getM.generateMission());
        }

        materialpresents.addAll(getM.materialPresents());
    }
    /**
     * Set the PlayerManager.
     * @param pm
     */
    public final void setPlayerManager(final PlayersManager pm) {
        this.players = pm;
    }
    /**
     * 
     * @return List of missions
     */
    public final List<Item> getListOfMissions() {
        return missions;
    }
    /**
     * 
     * @return materials presents in game
     */
    public final List<Material> getMaterialPresents() {
        return materialpresents;
    }
    /**
     * 
     * @return Index of the menu Guild missions
     */
    public final int getMenuIndex() {
        return this.menuIndex;
    }
    /**
     * 
     * @return Missions completed
     */
    public final List<Item> getMissionCompl() {
        return missionsCom;
    }
    /**
     * 
     * @return GetMissions
     */
    public final GetMissions getMissions() {
        return this.getM;
    }

    /**
     * Set the following context.
     * @param next next context
     */
    public final void setNextContext(final Context next) {
        this.following = next;
    }

    @Override
    public final void up() {
        if (this.menuIndex > 0) {
            this.menuIndex--;
        }
    }

    @Override
    public final void down() {
        if (this.menuIndex < missions.size() - 1) {
            this.menuIndex++;
        }
    }

    @Override
    public final void left() {
        up();
    }
    @Override
    public final void right() {
        down();
    }
    @Override
    public final void primary() {
        final Player player = players.getActivePlayer();
        if (player.getQuantityMaterial(missions.get(menuIndex).getMaterial()) >= missions.get(menuIndex).getQuantity()) {
            player.decreaseQuantityMaterial(missions.get(menuIndex).getMaterial(), 
            missions.get(menuIndex).getQuantity());
            player.increasePoints(missions.get(menuIndex).getPoints());
            missionsCom.add(missions.get(menuIndex));
            missions.remove(menuIndex);
        }
    }

    @Override
    public final void secondary() {
    }

    @Override
    public final void back() {
        this.done = true;
    }

    @Override
    public final boolean done() {
        return this.done;
    }

    @Override
    public final Context getNextContext() {
        this.done = false;
        return this.following;
    }

}
