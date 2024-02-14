package com.ccdr.labyrinth;
import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.engine.Executor.ID;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.game.GameInputAdapter;
import com.ccdr.labyrinth.game.GameJFXView;
import com.ccdr.labyrinth.jfx.JFXStage;
import com.ccdr.labyrinth.menu.MenuController;
import com.ccdr.labyrinth.menu.MenuInputAdapter;
import com.ccdr.labyrinth.menu.MenuJFXView;
import com.ccdr.labyrinth.result.ResultController;
import com.ccdr.labyrinth.result.ResultInputAdapter;
import com.ccdr.labyrinth.result.ResultJFXView;

import javafx.application.Application;
import javafx.application.Platform;

/**
 * Main class that gets executed.
 */
public final class Labyrinth {
    private static final int TARGET_FRAMERATE = 120;

    /**
     * @param args ignored
     */
    public static void main(final String[] args) {
        new Thread(() -> {
            //Makes sure the JavaFX environment is set up, in the case where the application below hasn´t started yet
            Platform.startup(() -> { });
            final Engine engine = new Engine(TARGET_FRAMERATE);

            //setting up the actual game
            final GameController gameController = new GameController();
            final GameJFXView gameView = new GameJFXView();
            gameController.addView(gameView);
            final GameInputAdapter gameInput = new GameInputAdapter(gameController);
            gameView.routeKeyboardEvents(gameInput);

            //setting up the main menu
            final MenuController menuController = new MenuController();
            final MenuJFXView menuView = new MenuJFXView();
            final MenuInputAdapter menuInput = new MenuInputAdapter(menuController);
            menuController.addView(menuView);
            menuView.routeKeyboardEvents(menuInput);

            //setting up the result screen
            final ResultController resultController = new ResultController();
            final ResultJFXView resultView = new ResultJFXView();
            resultController.addView(resultView);
            final ResultInputAdapter resultInput = new ResultInputAdapter(resultController);
            resultView.routeKeyboardEvents(resultInput);

            //setting up callbacks
            menuController.onPlay(config -> {
                gameController.init(config);
                engine.changeExecutor(ID.GAME);
            });

            gameController.onGameover(players -> {
                resultController.init(players);
                engine.changeExecutor(ID.RESULT);
            });

            resultController.onClose(() -> {
                engine.changeExecutor(ID.MENU);
            });

            final Runnable onClose = () -> {
                engine.stop();
                Platform.exit();
            };

            menuController.onExit(onClose);

            engine.bindExecutor(Executor.ID.MENU, menuController);
            engine.bindExecutor(Executor.ID.GAME, gameController);
            engine.bindExecutor(Executor.ID.RESULT, resultController);
            engine.changeExecutor(Executor.ID.MENU);

            JFXStage.addOnCloseListener(onClose);
            engine.start();
        }).start();

        Application.launch(JFXStage.class);
    }

    private Labyrinth() { }
}
