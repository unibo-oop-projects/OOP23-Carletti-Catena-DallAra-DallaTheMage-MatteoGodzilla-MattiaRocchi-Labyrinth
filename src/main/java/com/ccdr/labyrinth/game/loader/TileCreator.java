package com.ccdr.labyrinth.game.loader;

import java.util.Map;

public interface TileCreator {
    public Map<Integer, Tile> generateMany(int quantity);

    public Tile generateOne();

    public Tile generateRandomPattern();
}
