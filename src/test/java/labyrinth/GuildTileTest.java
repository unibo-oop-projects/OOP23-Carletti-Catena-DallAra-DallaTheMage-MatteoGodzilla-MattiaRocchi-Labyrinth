package labyrinth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.Item;

import javafx.beans.binding.ListBinding;
import javafx.scene.text.Text;

import java.util.List;

public class GuildTileTest {
    @Test
    void TestGetMissions(){
        GuildTile guild = new GuildTile(4);
        List<Item> missions = guild.returnListOfMissions();
        for (Item item : missions) {

            System.out.println("" + item.getPoints() + item.getQuantity() + item.getMaterial() + item.getCategory());
        }
    }
}
