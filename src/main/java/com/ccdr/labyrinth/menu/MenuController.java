package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;

//this is where the main menu will go
public class MenuController implements Executor {
    private Engine engine;
    private int frames = 0;
    private int framesToSwitch = 50;
    @Override
    public void onEnable(Engine engine) {
        this.engine = engine;
        frames = 0;
    }
    @Override
    public void update(double deltaTime) {
        System.out.println("MENU UPDATE");

        frames++;
        if(frames > framesToSwitch){
            engine.changeExecutor(Executor.ID.GAME);
        }
    }
    @Override
    public void onDisable() {

    }
}
