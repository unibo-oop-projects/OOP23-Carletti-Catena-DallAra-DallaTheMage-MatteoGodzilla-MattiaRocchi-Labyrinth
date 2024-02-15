package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.player.PlayersManager;

import javafx.scene.control.Menu;

import java.util.List;
import java.util.ArrayList;

public class GuildContext implements Context {

    private List<Item> missions = new ArrayList<>();
    private List<Item> missionsCom = new ArrayList<>();
    private List<Material> materialpresents = new ArrayList<>();
    private GetMissions getM = new GetMissions();
    private int menuIndex = 0;
    private PlayersManager player;
    private boolean done;
    private Context following;

    public GuildContext(final int nPlayer){
        for (int i = 0; i < nPlayer * 2; i++) {
            missions.add(getM.generateMission());
        }

        materialpresents.addAll(getM.materialPresents());
    }

    public void setPlayerManager(PlayersManager pm){
        this.player = pm;
    }

    public List<Item> returnListOfMissions() {
        return missions;
    }

    public List<Material> getMaterialPresents(){
        return materialpresents;
    }

    public int getMenuIndex(){
        return this.menuIndex;
    }

    public void setNextContext(Context next){
        this.following = next;
    }

    @Override
    public void up() {
        if(this.menuIndex > 0){
            this.menuIndex--;
        }
    }

    @Override
    public void down() {
        if(this.menuIndex < missions.size()-1){
            this.menuIndex++;
        }
    }

    @Override
    public void left() {
        up();
    }

    @Override
    public void right() {
        down();
    }

    @Override
    public void primary() {
        if(player.getActivePlayer().getQuantityMaterial(missions.get(menuIndex).getMaterial()) >= missions.get(menuIndex).getQuantity()){
            player.getActivePlayer().decreaseQuantityMaterial(missions.get(menuIndex).getMaterial(), missions.get(menuIndex).getQuantity());
            player.getActivePlayer().increasePoints(missions.get(menuIndex).getPoints());
            missionsCom.add(missions.get(menuIndex));
            missions.remove(menuIndex);
        }
    }

    @Override
    public void secondary() {
    }

    @Override
    public void back() {
        this.done = true;
    }

    @Override
    public boolean done() {
        return this.done;
    }

    @Override
    public Context getNextContext() {
        this.done = false;
        return this.following;
    }

}
