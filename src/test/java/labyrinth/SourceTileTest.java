package labyrinth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.player.PlayerImpl;

/**
 * Class that contains all tests for Source tiles.
 */
class SourceTileTest {

    @Test
    void accumulate() {
        // materials inside source tile should never go beyond the maximum
        final SourceTile tile = new SourceTile(Material.DIAMOND, 4);
        assertEquals(SourceTile.STARTING_QUANTITY, tile.getQuantity());

        for (int i = 0; i < SourceTile.MAX_QUANTITY + 2; i++) {
            tile.updateTile();
        }
        assertEquals(SourceTile.MAX_QUANTITY, tile.getQuantity());
    }

    @Test
    void collect() {
        final SourceTile tile = new SourceTile(Material.WOOD, 3);
        tile.updateTile();
        tile.updateTile();
        tile.updateTile();
        assertEquals(SourceTile.STARTING_QUANTITY + 3, tile.collect());
        assertEquals(0, tile.collect());
    }

    @Test
    void testQuantity() {
        final SourceTile tile = new SourceTile(Material.COAL, 2);
        assertEquals(SourceTile.STARTING_QUANTITY, tile.getQuantity());
        tile.updateTile();
        tile.updateTile();
        tile.updateTile();
        tile.updateTile();
        assertEquals(SourceTile.STARTING_QUANTITY + 4, tile.getQuantity());
        tile.collect();
        assertEquals(0, tile.getQuantity());
    }

    @Test
    void repeatedCollect() {
        final SourceTile tile = new SourceTile(Material.SILK, 8);
        //the tile should be active as soon as it's created
        assertTrue(tile.isActive());
        //players walks up to the source tile
        assertEquals(SourceTile.STARTING_QUANTITY, tile.collect());
        assertFalse(tile.isActive());
        //simulate nothing happening for 5 turns
        final int emptyTurns = 5;
        for (int i = 0; i < emptyTurns; i++) {
            tile.updateTile();
        }
        //it should still be inactive
        assertFalse(tile.isActive());
        //player 2 wants to collect materials, but it can´t
        assertEquals(0, tile.collect());
        //the cooldown should not have been reset by this action
        for (int i = 0; i < 3; i++) {
            tile.updateTile();
        }
        assertFalse(tile.isActive());
        tile.updateTile();
        assertTrue(tile.isActive());
        assertEquals(SourceTile.MIN_QUANTITY, tile.getQuantity());
    }

    @Test
    void testWithPlayerObject() {
        final PlayerImpl p = new PlayerImpl();
        final SourceTile tile = new SourceTile(Material.COAL, 3);
        assertEquals(0, p.getQuantityMaterial(Material.COAL));
        tile.updateTile();
        tile.updateTile();
        tile.updateTile();
        //simulate player going over the source tile and leaving
        tile.onEnter(p);
        tile.updateTile();
        tile.onExit(p);
        final int newQuantity = p.getQuantityMaterial(Material.COAL);
        assertTrue(newQuantity > 0);
        //tile should be blocked now
        tile.onEnter(p);
        tile.updateTile();
        tile.onExit(p);
        assertEquals(newQuantity, p.getQuantityMaterial(Material.COAL));
    }
}
