package com.ccdr.labyrinth.game.loader;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import javax.xml.transform.Source;

import com.ccdr.labyrinth.Material;


public class TileCreatorImpl implements TileCreator{

        private Map<Integer, Material> materials = new HashMap<>();

        @Override
        public Tile generateNormal() {
            Tile generatedTile = new StandardTile();
            generatedTile.setType(TileType.NORMAL);
            generatedTile.setPattern(generateRandomPattern().getPattern());
            return generatedTile;
        }

        @Override
        public Tile generateSource() {
            Tile generatedTile = new SourceTile(materials.remove(materials.size()-1));
            generatedTile.setType(TileType.SOURCE);
            generatedTile.setPattern(generateRandomPattern().getPattern());
            return generatedTile;
        }

        @Override
        public Map<Integer, Tile> generateTiles(int normalQuantity, int sourceQuantity) {
            Map<Integer, Tile> tiles = new HashMap<>();
            setupMaterialsList(sourceQuantity);
            while(normalQuantity-- > 0) {
                tiles.put(normalQuantity, this.generateNormal());
            }
            while(sourceQuantity-- > 0) {
                tiles.put(sourceQuantity, this.generateSource());
            }
            return Map.copyOf(tiles);
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