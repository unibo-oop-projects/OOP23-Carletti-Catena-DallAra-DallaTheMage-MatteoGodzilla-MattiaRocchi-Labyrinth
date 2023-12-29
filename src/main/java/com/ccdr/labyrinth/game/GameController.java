package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;

//this is the class responsible for controlling the entire game
public class GameController implements Executor{
    private Engine engine;
    private int frames;
    private int framesToSwitch = 100;

    @Override
    public void onEnable(Engine engine) {
        this.engine = engine;
        System.out.println("ENABLED GAME");
        frames = 0;
    }
    @Override
    public void update(double deltaTimeInSeconds) {
        //game loop
        System.out.println("GAME UPDATE " + 1.0/deltaTimeInSeconds);
        frames++;
        if(frames > framesToSwitch){
            engine.changeExecutor(Executor.ID.MENU);
        }
    }
    @Override
    public void onDisable() {
        System.out.println("DISABLED GAME");
    }
}
