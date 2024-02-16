package labyrinth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.game.generator.BoardGenerator;
import com.ccdr.labyrinth.game.util.Coordinate;
import com.ccdr.labyrinth.game.util.Material;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.tiles.GuildTile;
import com.ccdr.labyrinth.game.tiles.StandardTile;
import com.ccdr.labyrinth.game.tiles.SourceTile;

import java.util.List;

class BoardGenerationTest {
    private static final int HEIGHT = 15;
    private static final int WIDTH = 15;
    private static final int SOURCES = 8;
    private static final int PLAYERS = 2;
    private static final int MAX_POINTS = 10;
    private static final List<Material> MATERIALS = List.of(Material.COAL, Material.WOOD, Material.IRON, Material.SILK);
    private final Board maze = new BoardGenerator(HEIGHT, WIDTH, SOURCES, PLAYERS, MATERIALS).generate(MAX_POINTS);

    @Test
    void standardTilesGeneration() {
        final int correctNumber = HEIGHT * WIDTH - SOURCES - 1;
        final long generated = maze.getMap().entrySet()
            .stream()
            .filter(entry -> entry.getValue() instanceof StandardTile)
            .count();
        Assertions.assertEquals(correctNumber, generated);
    }

    @Test
    void sourceTileGeneration() {
        final long generated = maze.getMap().entrySet()
        .stream()
        .filter(entry -> entry.getValue() instanceof SourceTile)
        .count();
        Assertions.assertEquals(SOURCES, generated);
    }

    @Test
    void guildLocation() {
        final Coordinate correctLocation = new Coordinate(HEIGHT / 2, WIDTH / 2);
        Coordinate generatedLocation = new Coordinate(HEIGHT, WIDTH);
        for (final var e : maze.getMap().entrySet()) {
            if (e.getValue() instanceof GuildTile) {
                generatedLocation = e.getKey();
            }
        }
        Assertions.assertEquals(correctLocation, generatedLocation);
    }

    @Test
    void boardGenerated() {
        final int correctNumber = HEIGHT * WIDTH;
        final long generated = maze.getMap().entrySet()
        .stream()
        .count();
        Assertions.assertEquals(correctNumber, generated);
    }
}
