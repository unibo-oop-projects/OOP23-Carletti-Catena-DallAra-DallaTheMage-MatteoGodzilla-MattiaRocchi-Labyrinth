import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.player.PlayerImpl;

public class PlayerTest {
    
    private Player player;
    private GameConfig gameConfig = new GameConfig();

    @BeforeEach
    public void init() {
        this.player = new PlayerImpl();
    }

    @Test
    public void testMoveUp() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveUp();
        assertNotEquals(new Coordinate(-1, 0), player1.getCoord());
        assertEquals(new Coordinate(0, 0), player1.getCoord());
    }

    @Test
    public void testMoveRight() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveRight();
        assertEquals(new Coordinate(0, 1), player1.getCoord());
        for(int i = 0; i < this.gameConfig.getLabyrinthWidth(); i++) {
            player1.moveRight();
        }
        assertNotEquals(new Coordinate(0, 30), player1.getCoord());
        assertEquals(new Coordinate(0, 29), player1.getCoord());
    }

    @Test
    public void testMoveLeft() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveLeft();
        assertNotEquals(new Coordinate(0, -1), player1.getCoord());
        assertEquals(new Coordinate(0, 0), player1.getCoord());
    }

    @Test
    public void testMoveDown() {
        var player1 = this.player;
        assertEquals(new Coordinate(0, 0), player1.getCoord());
        player1.moveDown();
        assertEquals(new Coordinate(1, 0), player1.getCoord());
        for(int i = 0; i < this.gameConfig.getLabyrinthHeight(); i++) {
            player1.moveDown();
        }
        assertNotEquals(new Coordinate(30, 0), player1.getCoord());
        assertEquals(new Coordinate(29, 0), player1.getCoord());
    }

    @Test
    public void testPoints() {
        var player1 = this.player;
        assertEquals(0, player1.getPoints());
        player1.increasePoints(10);
        assertEquals(10, player1.getPoints());
        player1.increasePoints(6);
        assertEquals(16, player1.getPoints());
    }

    @Test
    public void testInventory() {
        var player1 = this.player;
        assertEquals(0, player1.getQuantityMaterial(Material.WOOD));
        assertEquals(0, player1.getQuantityMaterial(Material.IRON));
        player1.increaseQuantityMaterial(Material.WOOD, 5);
        assertEquals(5, player1.getQuantityMaterial(Material.WOOD));
        player1.increaseQuantityMaterial(Material.IRON, 18);
        assertEquals(18, player1.getQuantityMaterial(Material.IRON));
        player1.decreaseQuantityMaterial(Material.WOOD, 10);
        assertEquals(5, player1.getQuantityMaterial(Material.WOOD));
        player1.decreaseQuantityMaterial(Material.IRON, 3);
        assertEquals(15, player1.getQuantityMaterial(Material.IRON));
    }
}
