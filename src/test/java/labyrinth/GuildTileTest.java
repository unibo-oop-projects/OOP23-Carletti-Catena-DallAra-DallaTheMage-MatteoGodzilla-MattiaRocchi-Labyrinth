package labyrinth;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.Material;

import java.util.List;

class GuildTileTest {
    @Test
    void testGetMissions() {
        final GuildTile guild = new GuildTile(4);
        final List<Item> missions = guild.returnListOfMissions();
        final List<Material> materials = guild.getMaterialPresents();
        for (final Item item : missions) {

            System.out.println("RES:" + item.getPoints() + item.getQuantity() + item.getMaterial() + item.getCategory());
        }

        for (final Material mat : materials) {
            System.out.println(mat);
        }


    }
}
