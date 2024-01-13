package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.Set;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;

//this is where the main menu will go
public class MenuController implements Executor {
    private Engine engine;
    private int frames = 0;
    private int framesToSwitch = 3*120;

    private Set<MenuView> views = new HashSet<>();

    @Override
    public void onEnable(Engine engine) {
        this.engine = engine;
        this.frames = 0;
        for (MenuView view : views) {
            view.onEnable();
        }
    }
    @Override
    public void update(double deltaTime) {
        //TODO: temporary until once menu is more implemented
        this.frames++;
        if(this.frames > this.framesToSwitch){
            this.engine.changeExecutor(Executor.ID.GAME);
        }
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
}
