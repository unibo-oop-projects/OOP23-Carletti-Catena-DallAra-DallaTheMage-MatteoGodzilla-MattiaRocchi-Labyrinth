package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.player.PlayersManager;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;

import java.util.HashSet;
import java.util.Set;
import java.util.*;

//this is the class responsible for controlling the entire game
public class GameController implements Executor{
    private Set<GameView> views = new HashSet<>();
    private Engine engine;
    private Board board;
    private PlayersManager playerManager;
    private boolean menuGuild = false;
    private List<Item> missions = new ArrayList();
    private GuildTile guild = new GuildTile(4);

    @Override
    public void onEnable(Engine engine) {

        this.engine = engine;
        for (GameView gameView : views) {
            gameView.onEnable();
        }
        missions = guild.returnListOfMissions();
    }

    public void init(GameConfig config){
        this.board = GameLoader.generateTiles(config);
    }

    @Override
    public void update(double deltaTimeInSeconds) {
        //game loop
        for (GameView gameView : views) {
            gameView.drawMissions(missions);
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

    /**
     * method to change the context during the game phase.
     */
    public void changeContext() {
        //TODO:How to change the context here?
    }

    public void movePlayerUp() {
        //this.playerManager.tryMoveUp();
    }

    public void movePlayerRight() {
        //this.playerManager.tryMoveRight();
    }

    public void movePlayerLeft() {
        //this.playerManager.tryMoveLeft();
    }

    public void movePlayerDown() {
        //this.playerManager.tryMoveDown();
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
