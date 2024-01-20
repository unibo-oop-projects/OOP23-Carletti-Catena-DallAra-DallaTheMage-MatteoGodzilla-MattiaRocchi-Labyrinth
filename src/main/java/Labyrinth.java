import com.ccdr.labyrinth.JFXStage;
import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.game.GameInputAdapter;
import com.ccdr.labyrinth.game.GameJFXView;
import com.ccdr.labyrinth.game.GameView;
import com.ccdr.labyrinth.menu.MenuController;
import com.ccdr.labyrinth.menu.MenuInputAdapter;
import com.ccdr.labyrinth.menu.MenuJFXView;

import javafx.application.Application;
import javafx.application.Platform;

public class Labyrinth {

    //Entry point of the entire application
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            //Makes sure the JavaFX environment is set up, in the case where the application below hasn´t started yet
            Platform.startup(()->{});
            //setting up the main menu
            MenuController menuController = new MenuController();
            MenuJFXView menuView = new MenuJFXView();
            menuController.addView(menuView);
            MenuInputAdapter menuInput = new MenuInputAdapter(menuController);
            menuView.routeEvents(menuInput);

            //setting up the actual game
            GameController gameController = new GameController();
            GameJFXView gameView = new GameJFXView();
            gameController.addView(gameView);
            GameInputAdapter gameInput = new GameInputAdapter(gameController);
            gameView.routeEvents(gameInput);

            Engine engine = new Engine(120);
            engine.bindExecutor(Executor.ID.MENU, menuController);
            engine.bindExecutor(Executor.ID.GAME, gameController);
            engine.changeExecutor(Executor.ID.MENU);

            JFXStage.addOnCloseListener(()->engine.stop());
            engine.start();
        }).start();

        Application.launch(JFXStage.class);
    }
}
