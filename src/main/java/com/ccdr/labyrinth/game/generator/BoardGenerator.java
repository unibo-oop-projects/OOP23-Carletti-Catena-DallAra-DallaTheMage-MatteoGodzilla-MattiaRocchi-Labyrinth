package com.ccdr.labyrinth.game.generator;

import com.ccdr.labyrinth.game.GameBoard;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.tiles.GuildTile;
import com.ccdr.labyrinth.game.tiles.SourceTile;
import com.ccdr.labyrinth.game.tiles.StandardTile;
import com.ccdr.labyrinth.game.tiles.Tile;
import com.ccdr.labyrinth.game.util.Coordinate;
import com.ccdr.labyrinth.game.util.Direction;
import com.ccdr.labyrinth.game.util.Item;
import com.ccdr.labyrinth.game.util.Material;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;
import java.util.Optional;

public final class BoardGenerator {
    private static final int MIN_PATTERNS = 1, MAX_PATTERNS = 5;
    private static final int MIN_ROTATIONS = 0, MAX_ROTATIONS = 4;
    private final Random seed;
    private final GameConfig configuration;
    private final CoordinateGenerator placer;
    private final Set<Coordinate> playersLocation;
    private List<Material> materials;
    private List<Optional<Material>> bonuses;


    public BoardGenerator(final GameConfig configuration, final List<Item> missions, final List<Material> materials) {
        this.configuration = configuration;
        this.playersLocation = Set.of(
            new Coordinate(0, 0),
            new Coordinate(0, this.configuration.getLabyrinthWidth() - 1),
            new Coordinate(this.configuration.getLabyrinthHeight() - 1, 0),
            new Coordinate(this.configuration.getLabyrinthHeight() - 1, this.configuration.getLabyrinthWidth() - 1));
        this.placer = new CoordinateGenerator(configuration);
        this.seed = new Random();
        this.materials = setupMaterialsList(materials);
        this.bonuses = this.setupBonusList(materials);
    }

    public Board generate(final int maxPoints) {
        //Parameters that depend on the config
        final Board tiles = new GameBoard();
        final int height = this.configuration.getLabyrinthHeight();
        final int width = this.configuration.getLabyrinthWidth();
        final Coordinate center = new Coordinate(height / 2, width / 2);
        int normalQuantity = height * width - this.configuration.getSourceTiles() - 1;
        //Guild tile generation
        GuildTile guild = new GuildTile(maxPoints);
        guild.setPattern(selectPattern(4));
        tiles.insertTile(center, guild);
        tiles.addBlocked(center);
        //Source Tiles generation
        final List<Coordinate> sourceCoordinates = new ArrayList<>();
        sourceCoordinates.addAll(this.placer.calculateSourcesCoordinates(center, tiles.getMap()));
        int index = sourceCoordinates.size() - 1;
        Coordinate sourceGeneratedCoordinate;
        for (Material m : this.materials) {
            sourceGeneratedCoordinate = sourceCoordinates.remove(index--);
            tiles.addBlocked(sourceGeneratedCoordinate);
            tiles.insertTile(sourceGeneratedCoordinate, generateSource(m, this.configuration.getPlayerCountOptions()));
        }
        //Normal and bonus tiles generation
        while (normalQuantity-- > 0) {
            Coordinate generatedCoordinate = this.placer.generateRandomCoordinate(tiles.getMap());
            Optional<Material> sourcesMaterial = this.pickMaterial(this.bonuses);
            Tile generatedTile = this.generateStandardTile(sourcesMaterial, generatedCoordinate);
            generatedTile.setPattern(generateRandomPattern().getPattern());
            tiles.insertTile(generatedCoordinate, generatedTile);
        }
        return tiles;
    }

    private StandardTile generateStandardTile(final Optional<Material> bonus, final Coordinate coordinates) {
        if (bonus.isEmpty() || this.playersLocation.contains(coordinates)) {
            return new StandardTile();
        } else {
            return new StandardTile(bonus.get(), 1);
        }
    }

    private Optional<Material> pickMaterial(final List<Optional<Material>> bonuses) {
        if (bonuses.size() > 0) {
            return bonuses.get(seed.nextInt(0, bonuses.size()));
        } else {
            return Optional.empty();
        }
    }

    private List<Optional<Material>> setupBonusList(final List<Material> materialPresents) {
        List<Optional<Material>> bonuses = new ArrayList<>();
        if (materialPresents.size() > 0) {
            int percentage = materialPresents.size() * 4;
            for (Material m : materialPresents) {
                bonuses.add(Optional.of(m));
            }
            while (percentage-- > 0) {
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
            pattern.rotate((e) -> e.next());
        }
        return pattern;
    }

    private Map<Direction, Boolean> selectPattern(final int selected) {
        /* PREDETERMINED TILE PATTERNS SELECTOR */
        switch (selected) {
            /* straight two-way pattern */
            case 1:
                return Map.of(Direction.UP, true, Direction.RIGHT, false,
                Direction.DOWN, true, Direction.LEFT, false);
            /* 90 degree two way pattern */
            case 2:
                return Map.of(Direction.UP, true, Direction.RIGHT, true,
                Direction.DOWN, false, Direction.LEFT, false);
            /* three-way pattern */
            case 3:
                return Map.of(Direction.UP, true, Direction.RIGHT, true,
                Direction.DOWN, true, Direction.LEFT, false);
            /* four-way pattern */
            case 4:
                return Map.of(Direction.UP, true, Direction.RIGHT, true,
                Direction.DOWN, true, Direction.LEFT, true);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Generates a list of materials that will be used to place the source tiles in the map
     * @param presents list of mission related materials.
     * @return redundant list of materials repeated n times where n is the number of sources per material.
     */
    public List<Material> setupMaterialsList(final List<Material> presents) {
        List<Material> materials = new ArrayList<>();
        if (presents.size() > 0) {
            int sourceEach = this.configuration.getSourceTiles() / presents.size();
            for (int i = sourceEach; i > 0; i--) {
                for (Material m : presents) {
                    materials.add(m);
                }
            }
            return materials;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
