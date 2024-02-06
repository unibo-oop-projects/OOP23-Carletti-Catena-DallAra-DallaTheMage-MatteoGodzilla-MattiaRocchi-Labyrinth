package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.player.PlayersManager;

import java.util.HashSet;
import java.util.Set;

//this is the class responsible for controlling the entire game
public class GameController implements Executor{
    private Set<GameView> views = new HashSet<>();
    private Engine engine;
    private Board board;
    private PlayersManager playerManager;
    private boolean menuGuild = false;

    @Override
    public void onEnable(Engine engine) {
        this.engine = engine;
        for (GameView gameView : views) {
            gameView.onEnable();
        }
    }

    public void init(GameConfig config){
        this.board = GameLoader.generateTiles(config);
    }

    @Override
    public void update(double deltaTimeInSeconds) {
        //game loop
        double framerate = 1.0/deltaTimeInSeconds;
        for (GameView gameView : views) {
            gameView.draw(framerate);
            gameView.drawBoard(this.board);
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

    //input methods
    //note: this method gets called from the javafx application thread
    public void switchToMenu(){
        this.engine.changeExecutor(ID.MENU);
    }

    public void movePlayerUp() {
        this.playerManager.tryMoveUp();
    }

    public void movePlayerRight() {
        this.playerManager.tryMoveRight();
    }

    public void movePlayerLeft() {
        this.playerManager.tryMoveLeft();
    }

    public void movePlayerDown() {
        this.playerManager.tryMoveDown();
    }

    public void numberPlayerMoves() {
        this.playerManager.generateDiceValue();
    }

    public void moveUpGuild() {
;       
    }

    public void moveDownGuild() {
 ;
    }

    public void selectGuild() {

    }

    public void backGuild() {

    }
}
