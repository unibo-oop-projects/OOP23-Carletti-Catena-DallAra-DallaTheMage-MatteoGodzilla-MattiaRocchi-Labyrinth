package labyrinth;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.ImageLoader;
import javafx.application.Platform;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ImageLoaderTest {

    /**
     * Test of loading all imeges correctly.
     */
    @Test
    void testImageLoading() {

        Platform.startup(null);
        for (final ImageLoader image : ImageLoader.values()) {
            assertNotNull(image.getImage());
        }
    }
}
