package com.ccdr.labyrinth.game.loader;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import com.ccdr.labyrinth.Material;

/*TODO: IT MAY BE A GOOD IDEA MAKE TILECREATOR AN UTILITY CLASS WITH STATIC METHOD
* POSSIBLE REFACTORIZATION IN TILE GENERATION STRATEGY.
*/
public class TileCreatorImpl implements TileCreator {

        private Map<Integer, Material> materials = new HashMap<>();

        @Override
        public Map<Integer, Tile> generateTiles(int normalQuantity, int sourceQuantity) {
            Map<Integer, Tile> tiles = new HashMap<>();
            sourceQuantity += normalQuantity;
            this.setupMaterialsList(sourceQuantity);
            tiles.put(0, this.generateGuild());
            while(sourceQuantity-- > normalQuantity) {
                tiles.put(sourceQuantity, this.generateSource());
            }
            while(normalQuantity-- > 0) {
                tiles.put(normalQuantity, this.generateNormal());
            }
            return Map.copyOf(tiles);
        }

        private Tile generateNormal() {
            Tile generatedTile = new StandardTile();
            generatedTile.setPattern(this.generateRandomPattern().getPattern());
            return generatedTile;
        }

        private Tile generateGuild() {
            Tile generatedTile = new StandardTile();
            //generatedTile.setType(TileType.GUILD);
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