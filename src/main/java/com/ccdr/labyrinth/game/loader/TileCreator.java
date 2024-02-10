package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.StandardTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TileCreator {
    public static Map<Coordinate, Tile> generateTiles(final GameConfig config) {
        Map<Coordinate, Tile> tiles = new HashMap<>();
        //Parameters that depend on the config
        int sourceQuantity = config.getSourceTiles();
        int guildQuantity = config.getGuildNum();
        int width = config.getLabyrinthWidth();
        int height = config.getLabyrinthHeight();
        int normalQuantity = height * width - sourceQuantity - guildQuantity;
        //Guild tile
        while (guildQuantity-- > 0) {
            //FIXME:CHANGE TO PREDETERMINED POSITION
            tiles.put(generateRandomCoordinate(tiles, height, width), generateGuild());
        }
        //Source Tiles
        for (Material m : setupMaterialsList(config)) {
            //FIXME:CHANGE TO PREDETERMINED POSITION
            tiles.put(generateRandomCoordinate(tiles, height, width), generateSource(m, config.playerCount));
        }
        //Normal tiles
        while (normalQuantity-- > 0) {
            Tile generatedTile = new StandardTile();
            generatedTile.setPattern(generateRandomPattern().getPattern());
            tiles.put(generateRandomCoordinate(tiles, height, width), generatedTile);
        }
        //TODO: bonus tiles

        //The map is already created inside this function, there is no need to make another copy
        //return Map.copyOf(tiles);
        return tiles;
    }

    private static Tile generateGuild() {
        Tile generatedTile = new StandardTile();
        //FIXME:generatedTile.setType(TileType.GUILD);
        return generatedTile;
    }

    private static Tile generateSource(final Material material, final int playerCount) {
        Tile generatedTile = new SourceTile(material, playerCount);
        generatedTile.setPattern(generateRandomPattern().getPattern());
        return generatedTile;
    }

    private static Coordinate generateRandomCoordinate(final Map<Coordinate, Tile> board, final int height, final int width) {
        Random rng = new Random();
        Coordinate coordinate;
        do {
            coordinate = new Coordinate(rng.nextInt(0, height), rng.nextInt(0, width));
        } while (board.containsKey(coordinate));
        return coordinate;
    }

    private static Tile generateRandomPattern() {
        final int MIN_PATTERNS = 1, MAX_PATTERNS = 5;
        final int MIN_ROTATIONS = 0, MAX_ROTATIONS = 4;
        Random seed = new Random();
        int ways = seed.nextInt(MIN_PATTERNS, MAX_PATTERNS);
        int rotations = seed.nextInt(MIN_ROTATIONS, MAX_ROTATIONS);
        Tile pattern = new StandardTile();
        /* PREDETERMINED TILE PATTERNS SELECTOR */
        switch (ways) {
            /* straight two-way pattern */
            case 1:
                pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, false, Direction.DOWN, true,
                        Direction.LEFT, false));
                break;
            /* 90 degree two way pattern */
            case 2:
                pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, false,
                        Direction.LEFT, false));
                break;
            /* three-way pattern */
            case 3:
                pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true,
                        Direction.LEFT, false));
                break;
            /* four-way pattern */
            case 4:
                pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true,
                        Direction.LEFT, true));
                break;
            default:
                pattern.setPattern(Map.of(Direction.UP, false, Direction.RIGHT, false, Direction.DOWN, false,
                        Direction.LEFT, false));
        }
        /* the random number of rotations allows to have all possible tile patterns given the predetermined ones */
        while (rotations-- > 0) {
            pattern.rotate();
        }
        return pattern;
    }

    /*FIXME: temporary, waiting for get mission implementation.*/
    /**
     * Generates a list of materials that will be used to place the source tiles, in order, in the map
     * @param config game config to get how many materials to place
     * @return list of materials to add
     */
    public static List<Material> setupMaterialsList(final GameConfig config) {
        List<Material> materials = new ArrayList<>();
        int sourceEach = config.getSourceTiles() / Material.values().length;
        for (int i = sourceEach; i > 0; i--) {
            for (Material m : Material.values()) {
                materials.add(m);
            }
        }
        return materials;
    }
}