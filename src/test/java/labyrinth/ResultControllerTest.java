package labyrinth;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.result.ResultController;

class ResultControllerTest {
    private boolean signal;

    @BeforeEach
    void init() {
        this.signal = false;
    }

    @Test
    void testClose() {
        final ResultController controller = new ResultController();
        controller.onClose(() -> {
            this.signal = true;
        });
        controller.onEnable();
        controller.close();
        assertTrue(this.signal);
    }
}
