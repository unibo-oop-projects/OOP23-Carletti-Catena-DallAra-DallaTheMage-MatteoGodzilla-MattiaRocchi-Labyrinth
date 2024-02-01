package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.StandardTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class TileCreator {

        public static Map<Coordinate, Tile> generateTiles(GameConfig config) {
            Map<Coordinate, Tile> tiles = new HashMap<>();
            Map<Integer, Material> materials = config.setupMaterialsList();
            int sourceQuantity = config.getSourceTiles();
            int guildQuantity = config.getGuildNum();
            int normalQuantity = config.getLabyrinthHeight()*config.getLabyrinthWidth()-sourceQuantity-guildQuantity;
            while(guildQuantity-- > 0) {
                tiles.put(generateRandomCoordinate(tiles, config.getLabyrinthHeight(), config.getLabyrinthWidth()), generateGuild()); //FIXME:CHANGE TO PREDETERMINED POSITION
            }
            while(sourceQuantity-- > 0) {
                tiles.put(generateRandomCoordinate(tiles, config.getLabyrinthHeight(), config.getLabyrinthWidth()), generateSource(materials)); //FIXME:CHANGE TO PREDETERMINED POSITION
            }
            while(normalQuantity-- > 0) {
                Tile generatedTile = new StandardTile();
                generatedTile.setPattern(generateRandomPattern().getPattern());
                tiles.put(generateRandomCoordinate(tiles, config.getLabyrinthHeight(), config.getLabyrinthWidth()), generatedTile);
            }
            return Map.copyOf(tiles);
        }

        private static Tile generateGuild() {
            Tile generatedTile = new StandardTile();
            //FIXME:generatedTile.setType(TileType.GUILD);
            return generatedTile;
        }

        private static Tile generateSource(Map<Integer, Material> materials) {
            Tile generatedTile = new SourceTile(materials.remove(materials.size()-1));
            generatedTile.setPattern(generateRandomPattern().getPattern());
            return generatedTile;
        }

        private static Coordinate generateRandomCoordinate(Map<Coordinate, Tile> board, int height, int width) {
            Random rng = new Random();
                Coordinate coordinate;
                do { 
                    coordinate = new Coordinate(rng.nextInt(0, height), rng.nextInt(0, width));
                } while(board.containsKey(coordinate));
                return coordinate;
        }
    
        private static Tile generateRandomPattern() {
            Random seed = new Random();
            int ways = seed.nextInt(1, 5);
            int rotations = seed.nextInt(0, 4);
            Tile pattern = new StandardTile();
            /* PREDETERMINED TILE PATTERNS SELECTOR */
            switch(ways) {
                /* straight two-way pattern */
                case 1:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, false, Direction.DOWN, true, Direction.LEFT, false));
                    break;
                /* 90 degree two way pattern */
                case 2:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, false, Direction.LEFT, false));
                    break;
                /* three-way pattern */
                case 3:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true, Direction.LEFT, false));
                    break;
                /* four-way pattern */
                case 4:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true, Direction.LEFT, true));
                    break;
                default:
                    pattern.setPattern(Map.of(Direction.UP, false, Direction.RIGHT, false, Direction.DOWN, false, Direction.LEFT, false));
            }
            /* the random number of rotations allows to have all possible tile patterns given the predetermined ones */
            while(rotations-- > 0) {
                pattern.rotate();
            }
            return pattern;
        }
}