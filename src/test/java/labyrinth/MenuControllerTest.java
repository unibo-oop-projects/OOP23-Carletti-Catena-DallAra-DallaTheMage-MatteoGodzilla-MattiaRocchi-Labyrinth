package labyrinth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.menu.MenuController;

class MenuControllerTest {

    private boolean signal;

    @BeforeEach
    void init() {
        this.signal = false;
    }

    @Test
    void testPlay() {
        final MenuController controller = new MenuController();
        controller.onPlay(config -> {
            //check that the config has all the default values
            assertEquals(GameConfig.DEFAULT_LABYRINTH_SIZE, config.getLabyrinthWidth());
            assertEquals(GameConfig.DEFAULT_LABYRINTH_SIZE, config.getLabyrinthHeight());
            assertEquals(GameConfig.DEFAULT_PLAYER_COUNT, config.getPlayerCount());
            assertEquals(GameConfig.DEFAULT_SOURCE_TILE_COUNT, config.getSourceTiles());
            this.signal = true;
        });
        controller.onEnable();
        controller.select();
        assertTrue(this.signal);
    }

    @Test
    void testExit() {
        final MenuController controller = new MenuController();
        controller.onExit(() -> {
            this.signal = true;
        });
        controller.onEnable();
        //highlight Exit in the menu
        controller.moveDown();
        controller.moveDown();
        controller.moveDown();
        controller.moveDown();
        controller.select();
        //confirm exit
        controller.select();
        assertTrue(this.signal);
    }
}
