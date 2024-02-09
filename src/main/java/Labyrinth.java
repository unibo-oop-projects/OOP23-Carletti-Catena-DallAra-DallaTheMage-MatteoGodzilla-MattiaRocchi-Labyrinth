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

import javafx.application.Application;
import javafx.application.Platform;

/**
 * Main class that gets executed.
 */
public final class Labyrinth {
    private static final int TARGET_FRAMERATE = 120;

    /**
     *
     * @param args ignored
     * @throws InterruptedException ignored
     */
    public static void main(final String[] args) throws InterruptedException {
        new Thread(() -> {
            //Makes sure the JavaFX environment is set up, in the case where the application below hasn´t started yet
            //System.out.println(System.getProperty("java.class.path"));
            Platform.startup(() -> {
            });
            Engine engine = new Engine(TARGET_FRAMERATE);

            //setting up the actual game
            GameController gameController = new GameController();
            GameJFXView gameView = new GameJFXView();
            gameController.addView(gameView);
            GameInputAdapter gameInput = new GameInputAdapter(gameController);
            gameView.routeKeyboardEvents(gameInput);

            //setting up the main menu
            MenuController menuController = new MenuController();
            MenuJFXView menuView = new MenuJFXView();
            menuController.addView(menuView);
            MenuInputAdapter menuInput = new MenuInputAdapter(menuController);
            menuView.routeKeyboardEvents(menuInput);

            menuController.onPlay(config -> {
                engine.changeExecutor(ID.GAME);
                gameController.init(config);
            });

            Runnable onClose = new Runnable() {
                @Override
                public void run() {
                    engine.stop();
                    Platform.exit();
                }
            };

            menuController.onExit(onClose);

            engine.bindExecutor(Executor.ID.MENU, menuController);
            engine.bindExecutor(Executor.ID.GAME, gameController);
            engine.changeExecutor(Executor.ID.MENU);

            JFXStage.addOnCloseListener(onClose);
            engine.start();
        }).start();

        Application.launch(JFXStage.class);
    }

    private Labyrinth() { }
}
