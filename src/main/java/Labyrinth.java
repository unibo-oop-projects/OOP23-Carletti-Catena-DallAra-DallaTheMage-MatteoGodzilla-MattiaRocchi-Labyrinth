import java.time.Instant;
import java.time.temporal.TemporalField;

import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.GameController;
import com.ccdr.labyrinth.game.GameJFXView;

public class Labyrinth {
    private static final double ONE_SECOND_IN_NANOS = 1e9;

    public static final double FRAMERATE = 120;
    //Entry point of the entire application
    public static void main(String[] args) throws InterruptedException {
        GameConfig config = new GameConfig();
        GameController gameController = new GameController();
        gameController.attach(new GameJFXView());

        gameController.start(config);

        //Thought: is it overkill to abstract this away into a game loop class?

        double lastTick = System.nanoTime();
        double lastFrame = lastTick;
        double timeElapsed = 0;
        while(true){
            double currentTick = System.nanoTime();
            double deltaTick = currentTick - lastTick;
            timeElapsed += deltaTick;

            if(timeElapsed > ONE_SECOND_IN_NANOS / FRAMERATE){
                double newFrame = System.nanoTime();
                double deltaTimeInSeconds = (newFrame - lastFrame) / ONE_SECOND_IN_NANOS;
                gameController.update(deltaTimeInSeconds);

                lastFrame = newFrame;
                timeElapsed -= ONE_SECOND_IN_NANOS / FRAMERATE;
            }

            lastTick = currentTick;
        }

    }
}
