package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.tiles.Tile;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.StandardTile;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TileCreator {
    private final GameConfig configuration;
    private final CoordinatesManager coordinatesGenerator;
    private List<Material> presentMaterials;
    private Map<Coordinate, Tile> tiles;


    public TileCreator (GameConfig configuration) {
        this.coordinatesGenerator = new CoordinatesManager(configuration);
        this.configuration = configuration;
        this.tiles = new HashMap<>();
    }

    public Map<Coordinate, Tile> generateTiles() {
        //Parameters that depend on the config
        final int GUILD_NUM = 1;
        int normalQuantity = configuration.getLabyrinthHeight() * configuration.getLabyrinthWidth() - configuration.getSourceTiles() - GUILD_NUM;
        final Coordinate CENTER = new Coordinate(Math.round(configuration.getLabyrinthHeight() /2), Math.round(configuration.getLabyrinthWidth()/2));
        //Guild tile
        GuildTile guild = new GuildTile(configuration.getPlayerCount());
        guild.setPattern(selectPattern(4));
        this.presentMaterials.addAll(guild.getMaterialPresents());
        tiles.put(CENTER, guild);
        //Source Tiles
        for (Material m : setupMaterialsList(presentMaterials)) {
            tiles.put(this.coordinatesGenerator.generateCoordinateNearCenter(CENTER, tiles), generateSource(m, configuration.getPlayerCount()));
        }
        //Normal tiles
        while (normalQuantity-- > 0) {
            Tile generatedTile = new StandardTile();
            generatedTile.setPattern(generateRandomPattern().getPattern());
            tiles.put(this.coordinatesGenerator.generateRandomCoordinate(tiles), generatedTile);
        }
        //TODO: bonus tiles
        return tiles;
    }  

    private Tile generateSource(final Material material, final int playerCount) {
        Tile generatedTile = new SourceTile(material, playerCount);
        generatedTile.setPattern(generateRandomPattern().getPattern());
        return generatedTile;
    }

    private Tile generateRandomPattern() {
        final int MIN_PATTERNS = 1, MAX_PATTERNS = 5;
        final int MIN_ROTATIONS = 0, MAX_ROTATIONS = 4;
        final Random seed = new Random();
        int rotations = seed.nextInt(MIN_ROTATIONS, MAX_ROTATIONS);
        Tile pattern = new StandardTile();
        pattern.setPattern(selectPattern(seed.nextInt(MIN_PATTERNS, MAX_PATTERNS)));
        /* the random number of rotations allows to have all possible tile patterns given the predetermined ones */
        while (rotations-- > 0) {
            pattern.rotate();
        }
        return pattern;
    }

    private Map<Direction, Boolean> selectPattern(int selected) {
        /* PREDETERMINED TILE PATTERNS SELECTOR */
        switch (selected) {
            /* straight two-way pattern */
            case 1:
                return Map.of(Direction.UP, true, Direction.RIGHT, false, Direction.DOWN, true, Direction.LEFT, false);
            /* 90 degree two way pattern */
            case 2:
                return Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, false, Direction.LEFT, false);
            /* three-way pattern */
            case 3:
                return Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true, Direction.LEFT, false);
            /* four-way pattern */
            case 4:
                return Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true, Direction.LEFT, true);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Generates a list of materials that will be used to place the source tiles in the map
     * @param presents list of mission related materials.
     * @return redundant list of materials repeated n times where n is the number of sources per material.
     */
    public List<Material> setupMaterialsList(List<Material> presents) {
        List<Material> materials = new ArrayList<>();
        int sourceEach = configuration.getSourceTiles() / presents.size();
        for (int i = sourceEach; i > 0; i--) {
            for (Material m : presents) {
                materials.add(m);
            }
        }
        return materials;
    }
}