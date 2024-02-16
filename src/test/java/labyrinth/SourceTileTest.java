package labyrinth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.player.PlayerImpl;
import com.ccdr.labyrinth.game.tiles.SourceTile;
import com.ccdr.labyrinth.game.util.Material;

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
    void testSingleCollect() {
        final SourceTile tile = new SourceTile(Material.WOOD, 3);
        final Player p = new PlayerImpl();
        tile.updateTile();
        tile.updateTile();
        tile.updateTile();
        assertEquals(SourceTile.STARTING_QUANTITY + 3, tile.getQuantity());
        tile.onEnter(p);
        tile.onExit(p);
        assertEquals(0, tile.getQuantity());
    }

    @Test
    void testMultiplePlayersCollecting() {
        final SourceTile tile = new SourceTile(Material.SILK, 8);
        final Player p = new PlayerImpl();
        final Player p2 = new PlayerImpl();
        //the tile should be active as soon as it's created
        assertTrue(tile.isActive());
        //players walks up to the source tile
        assertEquals(SourceTile.STARTING_QUANTITY, tile.getQuantity());
        tile.onEnter(p);
        tile.onExit(p);
        assertFalse(tile.isActive());
        //simulate nothing happening for 5 turns
        final int emptyTurns = 5;
        for (int i = 0; i < emptyTurns; i++) {
            tile.updateTile();
        }
        //it should still be inactive
        assertFalse(tile.isActive());
        tile.onEnter(p2);
        tile.onExit(p2);
        //player 2 wants to collect materials, but it can´t
        assertEquals(0, p2.getQuantityMaterial(Material.SILK));
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
    void testSamePlayerCollectingMultipleTimes() {
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
