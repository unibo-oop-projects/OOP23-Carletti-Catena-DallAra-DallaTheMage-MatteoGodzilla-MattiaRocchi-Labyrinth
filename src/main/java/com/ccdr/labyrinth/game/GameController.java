package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.player.PlayersManager;
import com.ccdr.labyrinth.game.loader.GameBoard;
import com.ccdr.labyrinth.game.loader.generators.TilesGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

//this is the class responsible for controlling the entire game
public final class GameController implements Executor, GameInputs{
    private final Set<GameView> views = new HashSet<>();
    private Board board;
    private boolean menuGuild = false;
    private Consumer<List<Player>> gameover;
    //Contexts
    private Context activeContext;
    private UpdateBoardContext updateBoardContext;
    private LabyrinthContext labyrinthContext;
    private PlayersManager playerManager;
    private GuildContext guildContext;

    @Override
    public void onEnable() {
        for (final GameView gameView : views) {
            gameView.onEnable();
        }
    }

    public void init(final GameConfig config){
        //Inizializzazione this.activeContext = new "LabyrinthManager";
        board = new GameBoard();
        board.setHeight(config.getLabyrinthHeight());
        board.setWidth(config.getLabyrinthWidth());
        board.setMap(new TilesGenerator(config).generateTiles());
        //set up contexts
        this.updateBoardContext = new UpdateBoardContext(board);
        this.labyrinthContext = new LabyrinthContext();
        this.playerManager = new PlayersManager(config.getPlayerCount());
        this.guildContext = new GuildContext();

        this.updateBoardContext.bindNextContext(this.labyrinthContext);
        this.updateBoardContext.bindPlayerManager(playerManager);
        this.activeContext = this.updateBoardContext;
    }

    @Override
    public void update(final double deltaTimeInSeconds) {
        //game loop
        for (final GameView gameView : views) {
            gameView.clear();
            gameView.drawMissions(board.getGuildTile().returnListOfMissions());
            gameView.drawBoard(this.board);
            gameView.drawPlayersOnBoard(this.playerManager.getPlayers());
            gameView.drawPlayersStats(this.playerManager , this.board.getGuildTile().getMaterialPresents());
            gameView.drawContext(this.activeContext);
        }
    }

    public void addView(final GameView view){
        this.views.add(view);
    }

    public void onGameover(final Consumer<List<Player>> action){
        this.gameover = action;
    }

    //input methods
    //note: this method gets called from the javafx application thread

    /**
     * method that calls the activeContext method to execute when the W or up arrow key is pressed.
     */
    @Override
    public void up(){
        //TEMPORARY
        //this.gameover.accept(this.playerManager.getPlayers());

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
