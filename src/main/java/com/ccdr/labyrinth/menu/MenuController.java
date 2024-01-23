package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.Set;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.GameConfig;

//this is where the main menu will go
public class MenuController implements Executor {
    private Engine engine;
    private GameConfig config = new GameConfig();

    private Set<MenuView> views = new HashSet<>();

    @Override
    public void onEnable(Engine engine) {
        this.engine = engine;
        for (MenuView view : views) {
            view.onEnable();
        }
    }
    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void onDisable() {
        for (MenuView menuView : views) {
            menuView.onDisable();
        }
    }

    public void addView(MenuView view){
        this.views.add(view);
    }
    public void switchToGame() {
        this.engine.changeExecutor(ID.GAME);
    }

    public GameConfig getConfig(){
        return this.config;

    }
}
