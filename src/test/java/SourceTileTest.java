import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;

public class SourceTileTest {

    @Test
    void accumulate() {
        //materials inside source tile should never go beyond the maximum
        SourceTile tile = new SourceTile(Material.DIAMOND);
        assertEquals(0, tile.getQuantity());

        for (int i = 0; i < SourceTile.MAX_CHARGE + 2; i++) {
            tile.accumulate();
        }
        assertEquals(SourceTile.MAX_CHARGE, tile.getQuantity());
    }

    @Test
    void collect() {
        SourceTile tile = new SourceTile(Material.WOOD);
        tile.accumulate();
        tile.accumulate();
        tile.accumulate();
        assertEquals(3, tile.collect());
        assertEquals(0, tile.collect());
    }

    @Test
    void getQuantity() {
        SourceTile tile = new SourceTile(Material.COAL);
        tile.accumulate();
        assertEquals(1, tile.getQuantity());
        tile.accumulate();
        tile.accumulate();
        tile.accumulate();
        assertEquals(4, tile.getQuantity());
        tile.collect();
        assertEquals(0, tile.getQuantity());
    }
}
