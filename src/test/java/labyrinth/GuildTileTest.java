package labyrinth;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.tiles.GuildTile;
import com.ccdr.labyrinth.game.util.Item;
import com.ccdr.labyrinth.game.util.Material;

import com.ccdr.labyrinth.game.context.GuildContext;
import com.ccdr.labyrinth.TypeImag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
class GuildTileTest {

    GuildContext guildContext = new GuildContext(2);

    @Test
    void testMissions() {
        final List<Item> missions = guildContext.getListOfMissions();
        for (Item mission : missions) {
            assertEquals(missions.get(guildContext.getMenuIndex()).getMaterial(), guildContext.getListOfMissions().get(guildContext.getMenuIndex()).getMaterial());
            guildContext.down();
        }

    }

    @Test
    void testImage() {

    }
}
