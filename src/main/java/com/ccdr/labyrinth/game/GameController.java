package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;

import java.util.HashSet;
import java.util.Set;

//this is the class responsible for controlling the entire game
public class GameController implements Executor{
    private Engine engine;
    private int frames;
    private int framesToSwitch = 3*120;

    private Set<GameView> views = new HashSet<>();

    @Override
    public void onEnable(Engine engine) {
        this.engine = engine;
        this.frames = 0;

        for (GameView gameView : views) {
            gameView.onEnable();
        }
    }
    @Override
    public void update(double deltaTimeInSeconds) {
        //game loop
        //System.out.println("GAME UPDATE " + 1.0/deltaTimeInSeconds);
        double framerate = 1.0/deltaTimeInSeconds;
        for (GameView gameView : views) {
            gameView.draw(framerate);
        }

        //TODO: temporary until once menu is more implemented
        this.frames++;
        if(this.frames > this.framesToSwitch){
            this.engine.changeExecutor(Executor.ID.MENU);
        }
    }
    @Override
    public void onDisable() {
        for (GameView gameView : views) {
            gameView.onDisable();
        }
    }

    public void addView(GameView view){
        this.views.add(view);
    }
}
