package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.player.PlayersManager;
import com.ccdr.labyrinth.game.loader.GameBoard;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.loader.TileCreator;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;

import java.util.*;

//this is the class responsible for controlling the entire game
public class GameController implements Executor, GameInputs{
    private Context activeContext;
    private Set<GameView> views = new HashSet<>();
    private Board board;
    private PlayersManager playerManager;
    private boolean menuGuild = false;
    private List<Item> missions = new ArrayList();
    private GuildTile guild = new GuildTile(4);

    @Override
    public void onEnable() {
        for (GameView gameView : views) {
            gameView.onEnable();
        }
        missions = guild.returnListOfMissions();
    }

    public void init(GameConfig config){
        //Inizializzazione this.activeContext = new "LabyrinthManager";
        board.setHeight(config.getLabyrinthHeight());
        board.setWidth(config.getLabyrinthWidth());
        board.setMap(new TileCreator(config).generateTiles());        
        this.playerManager = new PlayersManager(config.getPlayerCount());
    }

    @Override
    public void update(double deltaTimeInSeconds) {
        //game loop
        for (GameView gameView : views) {
            gameView.drawMissions(missions);
            gameView.drawBoard(this.board);
            gameView.drawPlayersOnBoard(this.playerManager.getPlayers());
            gameView.drawPlayersStats(this.playerManager.getPlayers());
        }
    }

    public void addView(GameView view){
        this.views.add(view);
    }

    //input methods
    //note: this method gets called from the javafx application thread

    /**
     * method that calls the activeContext method to execute when the W or up arrow key is pressed.
     */
    @Override
    public void up(){
        this.activeContext.up();
        switchContextIfNecessary();
    }

    /**
     * method that calls the activeContext method to execute when the S or down arrow key is pressed.
     */
    @Override
    public void down(){
        this.activeContext.down();
        switchContextIfNecessary();
    }

    /**
     * method that calls the activeContext method to execute when the A or left arrow key is pressed.
     */
    @Override
    public void left() {
        this.activeContext.left();
        switchContextIfNecessary();
    }

    /**
     * method that calls the activeContext method to execute when the D or right arrow key is pressed.
     */
    @Override
    public void right() {
        this.activeContext.right();
        switchContextIfNecessary();
    }

    /**
     * method that calls the activeContext method to execute when the ENTER or SPACE key is pressed.
     */
    @Override
    public void primary() {
        this.activeContext.primary();
        switchContextIfNecessary();
    }

    /**
     * method that calls the activeContext method to execute when the TAB or CTRL key is pressed.
     */
    @Override
    public void secondary() {
        this.activeContext.secondary();
        switchContextIfNecessary();
    }

    /**
     * method that calls the activeContext method to execute when the ESC or BACKSPACE key is pressed.
     */
    @Override
    public void back() {
        this.activeContext.back();
        switchContextIfNecessary();
    }

    /**
     * method that checks if the activeContext should be changed.
     * This method is checked when the player presses a key used by the game.
     */
    private void switchContextIfNecessary() {
        if(this.activeContext.done()){
            this.activeContext = this.activeContext.getNextContext();
        }
    }
}
