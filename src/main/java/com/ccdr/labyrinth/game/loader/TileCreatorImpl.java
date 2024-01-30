package com.ccdr.labyrinth.game.loader;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import com.ccdr.labyrinth.Material;

/*TODO: IT MAY BE A GOOD IDEA MAKE TILECREATOR AN UTILITY CLASS WITH STATIC METHOD*/
public class TileCreatorImpl implements TileCreator {

        private Map<Integer, Material> materials = new HashMap<>();

        @Override
        public Map<Coordinate, Tile> generateTiles(int height, int width, int sourceQuantity) {
            Map<Coordinate, Tile> tiles = new HashMap<>();
            int normalQuantity = height*width-sourceQuantity-1;
            sourceQuantity += normalQuantity;
            this.setupMaterialsList(sourceQuantity);
            tiles.put(generateRandomCoordinate(tiles, height, width), this.generateGuild()); //FIXME:CHANGE TO PREDETERMINED POSITION
            while(sourceQuantity-- > normalQuantity) {
                tiles.put(generateRandomCoordinate(tiles, height, width), this.generateSource()); //FIXME:CHANGE TO PREDETERMINED POSITION
            }
            while(normalQuantity-- > 0) {
                Tile generatedTile = new StandardTile();
                generatedTile.setPattern(this.generateRandomPattern().getPattern());
                tiles.put(generateRandomCoordinate(tiles, height, width), generatedTile);
            }
            return Map.copyOf(tiles);
        }

        private Tile generateGuild() {
            Tile generatedTile = new StandardTile();
            //FIXME:generatedTile.setType(TileType.GUILD);
            return generatedTile;
        }

        private Tile generateSource() {
            Tile generatedTile = new SourceTile(materials.remove(materials.size()-1));
            generatedTile.setPattern(this.generateRandomPattern().getPattern());
            return generatedTile;
        }

        private void setupMaterialsList(int sourceQuantity) {
            int sourceEach = sourceQuantity / Material.values().length;
            int key = 0;
            for(Material m : Material.values()) {
                for(int i = sourceEach; i > 0; i--) {
                    materials.put(key, m);
                    key++;
                }
            }
        }

        private Coordinate generateRandomCoordinate(Map<Coordinate, Tile> board, int height, int width) {
            Random rng = new Random();
                Coordinate coordinate;
                do { 
                    coordinate = new Coordinate(rng.nextInt(0, height), rng.nextInt(0, width));
                } while(board.containsKey(coordinate));
                return coordinate;
        }
    
        private Tile generateRandomPattern() {
            Random seed = new Random();
            int ways = seed.nextInt(1, 5);
            int rotations = seed.nextInt(0, 4);
            Tile pattern = new StandardTile();
            switch(ways) {
                case 1:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, false, Direction.DOWN, true, Direction.LEFT, false));
                case 2:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, false, Direction.LEFT, false));
                case 3:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true, Direction.LEFT, false));
                case 4:
                    pattern.setPattern(Map.of(Direction.UP, true, Direction.RIGHT, true, Direction.DOWN, true, Direction.LEFT, true));
                default:
                    pattern.setPattern(Map.of(Direction.UP, false, Direction.RIGHT, false, Direction.DOWN, false, Direction.LEFT, false));
            }
            while(rotations-- > 0) {
                pattern.rotate();
            }
            return pattern;
        }
}