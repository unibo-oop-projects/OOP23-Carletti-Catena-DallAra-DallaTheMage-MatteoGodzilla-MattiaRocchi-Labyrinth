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

/**
 * This is the main class responsible for managing everything related to the game.
 */
public final class GameController implements Executor, GameInputs {
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

    public void init(final GameConfig config) {
        this.guildContext = new GuildContext(config.getPlayerCount());
        board = new TilesGenerator(config, guildContext.returnListOfMissions() ,guildContext.getMaterialPresents()).generateTiles();
        board.setHeight(config.getLabyrinthHeight());
        board.setWidth(config.getLabyrinthWidth());
        //set up contexts
        this.updateBoardContext = new UpdateBoardContext(this.board);
        this.playerManager = new PlayersManager(config.getPlayerCount(), this.board,
        this.updateBoardContext, this.guildContext);
        this.labyrinthContext = new LabyrinthContext(this.board, playerManager.getActivePlayer().getCoord(), this.playerManager);

        this.updateBoardContext.bindNextContext(this.labyrinthContext);
        this.updateBoardContext.bindPlayerManager(this.playerManager);
        this.activeContext = this.updateBoardContext;
    }

    @Override
    public void update(final double deltaTimeInSeconds) {
        //game loop
        for (final GameView gameView : views) {
            gameView.clear();
            gameView.drawMissions(guildContext.returnListOfMissions());
            gameView.drawBoard(this.board);
            gameView.drawPlayersOnBoard(this.playerManager.getPlayers());
            gameView.drawPlayersStats(this.playerManager, guildContext.getMaterialPresents());
            gameView.drawContext(this.activeContext);
        }
    }

    /**
     * @param view GameView object to bind to this controller
     */
    public void addView(final GameView view) {
        this.views.add(view);
    }

    /**
     * @param action Runnable to execute once the game is over
     */
    public void onGameover(final Consumer<List<Player>> action) {
        this.gameover = action;
    }

    //input methods
    //note: this method gets called from the javafx application thread

    /**
     * method that calls the activeContext method to execute when the W or up arrow key is pressed.
     */
    @Override
    public void up() {
        //TEMPORARY
        //this.gameover.accept(this.playerManager.getPlayers());

        this.activeContext.up();
        switchContextIfNecessary();
    }

    /**
     * method that calls the activeContext method to execute when the S or down arrow key is pressed.
     */
    @Override
    public void down() {
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
        if (this.activeContext.done()) {
            this.activeContext = this.activeContext.getNextContext();
        }
    }
}
