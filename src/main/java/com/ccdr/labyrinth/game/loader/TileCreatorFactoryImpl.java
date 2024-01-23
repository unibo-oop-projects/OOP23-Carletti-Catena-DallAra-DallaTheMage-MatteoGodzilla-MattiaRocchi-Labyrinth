package com.ccdr.labyrinth.game.loader;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import com.ccdr.labyrinth.Material;


public class TileCreatorFactoryImpl implements TileCreatorFactory {

    @Override
    public TileCreator normal() {
        return new TileCreator() {
            @Override
            public Map<Integer, Tile> generateMany(int quantity) {
                Map<Integer, Tile> tiles = new HashMap<>();
                while(quantity-- > 0) {
                    tiles.put(quantity, this.generateOne());
                }
                return Map.copyOf(tiles);
            }

            @Override
            public Tile generateOne() {
                Tile generatedTile = new StandardTile();
                generatedTile.setType(TileType.NORMAL);
                generatedTile.setPattern(generateRandomPattern().getPattern());
                return generatedTile;
            }

            @Override
            public Tile generateRandomPattern() {
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
        }; 
    }


    /* TODO: FIND A WAY TO CREATE EXACT NUMBER OF SOURCE FOR EACH MATERIAL
     * NEXT COMPLETE source() method.
     */
    @Override
    public TileCreator source() {
        return new TileCreator() {
            @Override
            public Map<Integer, Tile> generateMany(int quantity) {
                Map<Integer, Tile> tiles = new HashMap<>();
                while(quantity-- > 0) {
                    Tile generated = this.generateOne();
                    ((SourceTile)generated).setAssignedMaterial(this.pickMaterial(quantity));
                    tiles.put(quantity, this.generateOne());
                }
                return Map.copyOf(tiles);
            }
            @Override
            public Tile generateOne() {
                Tile generatedTile = new SourceTile();
                generatedTile.setType(TileType.SOURCE);
                generatedTile.setPattern(generateRandomPattern().getPattern());
                return generatedTile;
            }
            
            private Material pickMaterial(int sourceRequested) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'pickMaterial'");
            }

            @Override
            public Tile generateRandomPattern() {
                Random seed = new Random(); 
                int ways = seed.nextInt(1, 5);
                int rotations = seed.nextInt(0, 4);
                Tile pattern = new SourceTile();
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
        };
    }

    @Override
    public Tile guild() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guild'");
    }
    
}
