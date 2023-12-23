package com.ccdr.labyrinth.game;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private GameConfig config;
    private List<GameView> views = new ArrayList<>();

    //this is the class responsible for controlling the entire game
    public void start(GameConfig config){
        this.config = config;
    }

    public void update(double deltaTimeInSeconds) {
        //game loop
        System.out.println("UPDATE " + 1.0/deltaTimeInSeconds);
    }

    public void attach(GameView gameView) {
        views.add(gameView);
    }
}
