package labyrinth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.player.PlayerImpl;

/**
 * A test class for the implementation of a player.
 * It's final because the class is not designed for extension.
 */
public final class PlayerTest {

    private Player player;
    private GameConfig gameConfig = new GameConfig();

    /**
     * Method to initialize the field's player.
     */
    @BeforeEach
    public void init() {
        this.player = new PlayerImpl();
    }

    /**
     * Test the functioning of the moveUp() method.
     */
    @Test
    public void testMoveUp() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveUp();
        assertNotEquals(new Coordinate(-1, 0), player1.getCoord());
        assertEquals(new Coordinate(0, 0), player1.getCoord());
    }

    /**
     * Test the functioning of the moveRight() method.
     */
    @Test
    public void testMoveRight() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveRight();
        assertEquals(new Coordinate(0, 1), player1.getCoord());
        for (int i = 0; i < this.gameConfig.getLabyrinthWidth(); i++) {
            player1.moveRight();
        }
        assertNotEquals(new Coordinate(0, this.gameConfig.getLabyrinthWidth()), player1.getCoord());
        assertEquals(new Coordinate(0, this.gameConfig.getLabyrinthWidth() - 1), player1.getCoord());
    }

    /**
     * Test the functioning of the moveLeft() method.
     */
    @Test
    public void testMoveLeft() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveLeft();
        assertNotEquals(new Coordinate(0, -1), player1.getCoord());
        assertEquals(new Coordinate(0, 0), player1.getCoord());
    }

    /**
     * Test the functioning of the moveDown() method.
     */
    @Test
    public void testMoveDown() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveDown();
        assertEquals(new Coordinate(1, 0), player1.getCoord());
        for (int i = 0; i < this.gameConfig.getLabyrinthHeight(); i++) {
            player1.moveDown();
        }
        assertNotEquals(new Coordinate(this.gameConfig.getLabyrinthHeight(), 0), player1.getCoord());
        assertEquals(new Coordinate(this.gameConfig.getLabyrinthHeight() - 1, 0), player1.getCoord());
    }

    /**
     * Test the points management methods.
     */
    @Test
    public void testPoints() {
        var player1 = this.player;
        assertEquals(0, player1.getPoints());
        player1.increasePoints(4);
        assertEquals(4, player1.getPoints());
        player1.increasePoints(4);
        assertEquals(8, player1.getPoints());
    }

    /**
     * Test the inventory management methods.
     */
    @Test
    public void testInventory() {
        var player1 = this.player;
        assertEquals(0, player1.getQuantityMaterial(Material.WOOD));
        assertEquals(0, player1.getQuantityMaterial(Material.IRON));
        player1.increaseQuantityMaterial(Material.WOOD, 10);
        assertEquals(10, player1.getQuantityMaterial(Material.WOOD));
        player1.increaseQuantityMaterial(Material.IRON, 4);
        assertEquals(4, player1.getQuantityMaterial(Material.IRON));
        player1.decreaseQuantityMaterial(Material.WOOD, 8);
        assertEquals(2, player1.getQuantityMaterial(Material.WOOD));
        player1.decreaseQuantityMaterial(Material.IRON, 3);
        assertEquals(1, player1.getQuantityMaterial(Material.IRON));
    }
}
