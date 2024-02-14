package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.GetMissions;
import com.ccdr.labyrinth.game.loader.Item;

import javafx.scene.control.Menu;

import java.util.List;
import java.util.ArrayList;

public class GuildContext implements Context {

    private List<Item> missions = new ArrayList<>();
    private List<Material> materialpresents = new ArrayList<>();
    private GetMissions getM = new GetMissions();
    private int menuIndex = 0;

    public GuildContext(final int nPlayer){
        for (int i = 0; i < nPlayer * 2; i++) {
            missions.add(getM.generateMission());
        }

        materialpresents.addAll(getM.materialPresents());
    }

    public List<Item> returnListOfMissions() {
        return missions;
    }

    public List<Material> getMaterialPresents(){
        return materialpresents;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'left'");
    }

    @Override
    public void right() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'right'");
    }

    @Override
    public void primary() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'primary'");
    }

    @Override
    public void secondary() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'secondary'");
    }

    @Override
    public void back() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'back'");
    }

    @Override
    public boolean done() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'done'");
    }

    @Override
    public Context getNextContext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextContext'");
    }

}
