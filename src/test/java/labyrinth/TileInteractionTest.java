package labyrinth;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.GameBoard;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.tiles.SourceTile;
import com.ccdr.labyrinth.game.tiles.StandardTile;
import com.ccdr.labyrinth.game.tiles.Tile;
import com.ccdr.labyrinth.game.util.Coordinate;
import com.ccdr.labyrinth.game.util.Direction;
import com.ccdr.labyrinth.game.util.Material;

class TileInteractionTest {
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;

    @Test
    void rotateTileTest() {
        final Tile tile = new StandardTile();
        final Map<Direction, Boolean> rotated;
        tile.setPattern(Map.of(
            Direction.UP, true,
            Direction.RIGHT, true,
            Direction.DOWN, false,
            Direction.LEFT, false)
        );
        rotated = Map.of(
            Direction.UP, false,
            Direction.RIGHT, true,
            Direction.DOWN, true,
            Direction.LEFT, false
        );
        tile.rotate(true);
        Assertions.assertEquals(tile.getPattern(), rotated);
        tile.rotate(true);
        tile.rotate(false);
        Assertions.assertEquals(tile.getPattern(), rotated);
    }

    private Coordinate obtainShiftedRow(final Board maze, final int rowToCheck) {
        return maze.getMap().entrySet().stream()
        .filter(entry -> entry.getKey().row() == rowToCheck)
        .filter(entry -> entry.getValue() instanceof SourceTile)
        .findAny()
        .get()
        .getKey();
    }

    @Test
    void shiftRowTest() {
        final Board maze = new GameBoard();
        Coordinate coord;
        final Coordinate starting = new Coordinate(0, 0);
        final Coordinate after = new Coordinate(0, WIDTH - 1);
        Coordinate shifted;
        Tile tile;
        maze.setWidth(WIDTH);
        for (int y = 0; y < WIDTH; y++) {
            coord = new Coordinate(0, y);
            if (y == 0) {
                tile = new SourceTile(Material.COAL, 1);
                maze.insertTile(coord, tile);
            } else {
                tile = new StandardTile();
                maze.insertTile(coord, tile);
            }
        }
        maze.shiftRow(0, false);
        shifted = obtainShiftedRow(maze, 0);
        Assertions.assertEquals(after, shifted);
        maze.shiftRow(0, true);
        shifted = obtainShiftedRow(maze, 0);
        Assertions.assertEquals(starting, shifted);
    }

    private Coordinate obtainShiftedColumn(final Board maze, final int columnToCheck) {
        return maze.getMap().entrySet().stream()
        .filter(entry -> entry.getKey().column() == columnToCheck)
        .filter(entry -> entry.getValue() instanceof SourceTile)
        .findAny()
        .get()
        .getKey();
    }

    @Test
    void shiftColumnTest() {
        final Board maze = new GameBoard();
        Coordinate coord;
        final Coordinate starting = new Coordinate(0, 0);
        final Coordinate after = new Coordinate(HEIGHT - 1, 0);
        Coordinate shifted;
        Tile tile;
        maze.setHeight(HEIGHT);
        for (int i = 0; i < HEIGHT; i++) {
                coord = new Coordinate(i, 0);
                if (i == 0) {
                    tile = new SourceTile(Material.COAL, 1);
                    maze.insertTile(coord, tile);
                } else {
                    tile = new StandardTile();
                    maze.insertTile(coord, tile);
                }
        }
        maze.shiftColumn(0, false);
        shifted = obtainShiftedColumn(maze, 0);
        Assertions.assertEquals(after, shifted);
        maze.shiftColumn(0, true);
        shifted = obtainShiftedColumn(maze, 0);
        Assertions.assertEquals(starting, shifted);
    }
}
