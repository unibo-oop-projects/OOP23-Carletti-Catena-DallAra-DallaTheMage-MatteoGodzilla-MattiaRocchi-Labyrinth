import com.ccdr.labyrinth.JFXStage;
import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.game.GameJFXView;
import com.ccdr.labyrinth.menu.MenuController;
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
            menuController.addView(new MenuJFXView());

            //setting up the actual game
            GameController gameController = new GameController();
            gameController.addView(new GameJFXView());

            Engine engine = new Engine(120);
            engine.bindExecutor(Executor.ID.MENU, menuController);
            engine.bindExecutor(Executor.ID.GAME, gameController);
            engine.changeExecutor(Executor.ID.GAME);

            JFXStage.addOnCloseListener(()->engine.stop());
            engine.start();
        }).start();

        Application.launch(JFXStage.class);
    }
}
