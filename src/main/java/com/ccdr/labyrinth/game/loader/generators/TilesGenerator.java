package com.ccdr.labyrinth.game.loader.generators;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.Board;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.Direction;
import com.ccdr.labyrinth.game.loader.GameBoard;
import com.ccdr.labyrinth.game.loader.tiles.Tile;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.loader.tiles.GuildTile;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.StandardTile;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Optional;

public class TilesGenerator {
    private final Random SEED;
    private final GameConfig CONFIGURATION;
    private final CoordinatesGenerator PLACER;
    private final int MIN_PATTERNS = 1, MAX_PATTERNS = 5;
    private final int MIN_ROTATIONS = 0, MAX_ROTATIONS = 4;
    private final List<Coordinate> PLAYER_LOCATIONS;
    private final Coordinate player1, player2, player3, player4;
    private List<Material> materials;
    private List<Optional<Material>> bonuses;
 

    public TilesGenerator (GameConfig configuration, List<Item> missions, List<Material> materials) {
        this.CONFIGURATION = configuration;
        this.player1 = new Coordinate(0, 0);
        this.player2 = new Coordinate(0, this.CONFIGURATION.getLabyrinthWidth()-1);
        this.player3 = new Coordinate(this.CONFIGURATION.getLabyrinthHeight()-1, 0);
        this.player4 =  new Coordinate(this.CONFIGURATION.getLabyrinthHeight()-1, this.CONFIGURATION.getLabyrinthWidth()-1);
        this.PLAYER_LOCATIONS = List.of(player1, player2, player3, player4 );
        this.PLACER = new CoordinatesGenerator(configuration);
        this.SEED = new Random();
        this.materials = setupMaterialsList(materials);
        this.bonuses = this.setupBonusList(materials);
    }

    public Board generateTiles(final int maxPoints) {
        //Parameters that depend on the config
        final Board tiles = new GameBoard();
        final int GUILD_NUM = 1;
        final Coordinate CENTER = new Coordinate(this.CONFIGURATION.getLabyrinthHeight() / 2,
        this.CONFIGURATION.getLabyrinthWidth() / 2);
        int normalQuantity = this.CONFIGURATION.getLabyrinthHeight() *
        this.CONFIGURATION.getLabyrinthWidth() - this.CONFIGURATION.getSourceTiles() - GUILD_NUM;
        //Guild tile
        GuildTile guild = new GuildTile(maxPoints);
        guild.setPattern(selectPattern(4));
        tiles.insertTile(CENTER, guild);
        tiles.addBlocked(CENTER);
        //Source Tiles
        final List<Coordinate> sourceCoordinates = new ArrayList<>(this.PLACER.calculateSourcesCoordinates(CENTER, tiles.getMap()));
        int index = sourceCoordinates.size() - 1;
        Coordinate sourceGeneratedCoordinate;
        for (Material m : this.materials) {
            sourceGeneratedCoordinate = sourceCoordinates.remove(index--);
            tiles.addBlocked(sourceGeneratedCoordinate);
            tiles.insertTile(sourceGeneratedCoordinate, generateSource(m, this.CONFIGURATION.getPlayerCount()));
        }
        //Normal and bonus tiles
        while (normalQuantity-- > 0) {
            Coordinate generatedCoordinate = this.PLACER.generateRandomCoordinate(tiles.getMap());
            Optional<Material> sourcesMaterial = this.pickMaterial(this.bonuses);
            Tile generatedTile = sourcesMaterial.isEmpty() || this.PLAYER_LOCATIONS.contains(generatedCoordinate) ? new StandardTile() : new StandardTile(sourcesMaterial.get(), 1);
            generatedTile.setPattern(generateRandomPattern().getPattern());
            tiles.insertTile(generatedCoordinate, generatedTile);
        }
        return tiles;
    }

    private Optional<Material> pickMaterial(final List<Optional<Material>> bonuses) {
        return bonuses.size() > 0 ? bonuses.get(SEED.nextInt(0, bonuses.size())) : Optional.empty();
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
        int rotations = SEED.nextInt(MIN_ROTATIONS, MAX_ROTATIONS);
        Tile pattern = new StandardTile();
        pattern.setPattern(selectPattern(SEED.nextInt(MIN_PATTERNS, MAX_PATTERNS)));
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
            int sourceEach = this.CONFIGURATION.getSourceTiles() / presents.size();
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
