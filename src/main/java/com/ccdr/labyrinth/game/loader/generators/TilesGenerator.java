package com.ccdr.labyrinth.game.loader.generators;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.tiles.Tile;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Direction;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.StandardTile;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Optional;

public class TilesGenerator {
    private final Random seed;
    private final GameConfig configuration;
    private final CoordinatesGenerator placer;
    private final int MIN_PATTERNS = 1, MAX_PATTERNS = 5;
    private final int MIN_ROTATIONS = 0, MAX_ROTATIONS = 4;
    private Map<Coordinate, Tile> tiles;


    public TilesGenerator (GameConfig configuration) {
        this.configuration = configuration;
        this.placer = new CoordinatesGenerator(configuration);
        this.seed = new Random();
        this.tiles = new HashMap<>();
    }

    public Map<Coordinate, Tile> generateTiles() {
        //Parameters that depend on the config
        final int GUILD_NUM = 1;
        final Coordinate CENTER = new Coordinate(this.configuration.getLabyrinthHeight() /2, this.configuration.getLabyrinthWidth()/2);
        int normalQuantity = this.configuration.getLabyrinthHeight() * this.configuration.getLabyrinthWidth() - this.configuration.getSourceTiles() - GUILD_NUM;
        //Guild tile
        GuildTile guild = new GuildTile(this.configuration.getPlayerCount());
        guild.setPattern(selectPattern(4));
        tiles.put(CENTER, guild);
        //Source Tiles
        final List<Optional<Material>> bonuses = new ArrayList<>(this.setupBonusList(guild.getMaterialPresents()));
        final List<Coordinate> sourceCoordinates = new ArrayList<>(this.placer.calculateSourcesCoordinates(CENTER, tiles));
        int index = sourceCoordinates.size()-1;
        for (Material m : setupMaterialsList(guild.getMaterialPresents())) {
            tiles.put(sourceCoordinates.remove(index--), generateSource(m, this.configuration.getPlayerCount()));
        }
        //Normal and bonus tiles 
        while (normalQuantity-- > 0) {
            Coordinate generatedCoordinate = this.placer.generateRandomCoordinate(tiles);
            Optional<Material> sourcesMaterial = this.pickMaterial(bonuses);
            Tile generatedTile = sourcesMaterial.isEmpty() ? new StandardTile() : new StandardTile(sourcesMaterial.get(), 1);
            generatedTile.setPattern(generateRandomPattern().getPattern());
            tiles.put(generatedCoordinate, generatedTile);
        }
        return tiles;
    }

    private Optional<Material> pickMaterial(List<Optional<Material>> bonuses) {
        return bonuses.size() > 0 ? bonuses.get(seed.nextInt(0, bonuses.size())) : Optional.empty();
    }

    private List<Optional<Material>> setupBonusList(List<Material> materialPresents) {
        List<Optional<Material>> bonuses = new ArrayList<>();
        if(materialPresents.size() > 0) {
            int percentage = Math.round(materialPresents.size() / 3);
            for(Material m : materialPresents) {
                bonuses.add(Optional.of(m));
            }
            while(percentage-- > 0) {
                bonuses.add(Optional.empty());
            }
            return bonuses;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Tile generateSource(final Material material, final int playerCount) {
        Tile generatedTile = new SourceTile(material, playerCount);
        generatedTile.setPattern(generateRandomPattern().getPattern());
        return generatedTile;
    }

    private Tile generateRandomPattern() {
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
        if(presents.size() > 0) {
            int sourceEach = this.configuration.getSourceTiles() / presents.size();
            for (int i = sourceEach; i > 0; i--) {
                for (Material m : presents) {
                    materials.add(m);
                }
            }
            return materials;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}