import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.menu.MenuController;

public class Labyrinth {

    //Entry point of the entire application
    public static void main(String[] args) throws InterruptedException {
        GameController gameController = new GameController();
        MenuController menuController = new MenuController();

        Engine engine = new Engine();
        engine.bindExecutor(Executor.ID.MENU, menuController);
        engine.bindExecutor(Executor.ID.GAME, gameController);
        engine.changeExecutor(Executor.ID.GAME);
        engine.start();
    }
}
