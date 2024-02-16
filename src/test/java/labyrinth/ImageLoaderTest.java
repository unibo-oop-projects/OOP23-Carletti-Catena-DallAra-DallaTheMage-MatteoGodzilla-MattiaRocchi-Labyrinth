package labyrinth;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.ImageLoader;

import javafx.application.Platform;

class ImageLoaderTest {

    /**
     * Test of loading all imeges correctly
     */
    @Test
    void testImageLoading(){

        Platform.startup(null);
        for (ImageLoader image : ImageLoader.values()) {
            assertNotNull(image.getImage());
        }
    }
}
