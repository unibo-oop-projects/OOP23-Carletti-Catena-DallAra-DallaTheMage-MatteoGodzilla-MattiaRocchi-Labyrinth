package labyrinth;

import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.Material;

import java.util.List;

public class GuildTileTest {
    @Test
    void TestGetMissions(){
        GuildTile guild = new GuildTile(4);
        List<Item> missions = guild.returnListOfMissions();
        List<Material> materials = guild.getMaterialPresents();
        for (Item item : missions) {

            System.out.println("" + item.getPoints() + item.getQuantity() + item.getMaterial() + item.getCategory());
        }

        for (Material mat : materials){
            System.out.println("" + mat);
        }


    }
}
