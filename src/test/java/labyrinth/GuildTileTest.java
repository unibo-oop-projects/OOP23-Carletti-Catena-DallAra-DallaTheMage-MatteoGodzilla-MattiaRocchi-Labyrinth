package labyrinth;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.tiles.GuildTile;
import com.ccdr.labyrinth.game.util.Item;
import com.ccdr.labyrinth.game.util.Material;
import com.ccdr.labyrinth.game.GameBoard;
import com.ccdr.labyrinth.game.context.GuildContext;
import com.ccdr.labyrinth.game.context.PlayersContext;
import com.ccdr.labyrinth.game.context.UpdateBoardContext;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.player.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
class GuildTileTest {

    private final GuildContext guildContext = new GuildContext(2);
    private final List<Item> missions = guildContext.getListOfMissions();

    /**
     * Mission generation test.
     */
    @Test
    void testMissionsLoading() {
        assertNotNull(missions);
    }

    /**
     * Testing on creating the list of completed missions, and currently it must be empty.
     */
    @Test
    void testMissionsCompleateNotLoading() {
        assertTrue(guildContext.getMissionCompl().isEmpty());
    }

    /**
     * Menu mission index movement test.
     */
    @Test
    void testMenu() {
        for (final Item x : missions) {
            final Material matMenu = guildContext.getListOfMissions().get(guildContext.getMenuIndex()).getMaterial();
            assertEquals(x.getMaterial(), matMenu);
            guildContext.down();
        }
        for (final Item x : missions) {
            final Material matMenu = guildContext.getListOfMissions().get(guildContext.getMenuIndex()).getMaterial();
            assertEquals(x.getMaterial(), matMenu);
            guildContext.up();
        }

    }

    /**
     * Test on the completion of a mission and also check that the mission is included in the list of completed missions.
     */
    @Test
    void testCompleteMiss() {
        final UpdateBoardContext upContext = new UpdateBoardContext(null);
        final PlayersContext players = new PlayersContext(1, new GameBoard(), upContext, guildContext);
        for (final Material mat : Material.values()) {
            players.getActivePlayer().increaseQuantityMaterial(mat, 10);
        }
        guildContext.setPlayerManager(players);
        guildContext.primary();
        assertEquals(players.getActivePlayer().getPoints(), guildContext.getMissionCompl().get(0).getPoints());
    }

    /**
     * Test the onEnter function of give bonus points at the turn player.
     */
    @Test
    void testPointsBonus() {
        final Player player = new PlayerImpl();
        final GuildTile guild = new GuildTile(10);

        guild.onEnter(player);
        //The value of points that are given is equal of maxPoint / 2
        //In this case 10 / 2 = 5 
        assertEquals(2 + 3, player.getPoints());
    }

    /**
     * Test to check exit from the guild menu and respective closure of the context.
     */
    @Test
    void testDone() {
        guildContext.back();
        assertTrue(guildContext.done());
    }







}
