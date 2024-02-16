package labyrinth;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.engine.Executor.ID;

class EngineTest {
    @Test
    void testChangeExecutor() {
        final Engine engine = new Engine(10);
        engine.bindExecutor(ID.MENU, new Executor() {
            @Override
            public void onEnable() {
                engine.changeExecutor(ID.GAME);
            }
            @Override
            public void update(final double deltaTime) { }
        });
        engine.bindExecutor(ID.GAME, new Executor() {
            @Override
            public void onEnable() { }

            @Override
            public void update(final double deltaTime) {
                engine.stop();
            }
        });
        engine.changeExecutor(ID.MENU);
        //engine.start is a blocking call. If it returns to the caller, then that means engine.stop was called.
        //for engine.stop to be called, the change in executor must have happened from ID.Menu to ID.Game.
        engine.start();

    }
}
