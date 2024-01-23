package com.ccdr.labyrinth.game.loader;

public interface TileCreatorFactory {
    public TileCreator normal();
    public TileCreator source();
    public Tile guild();
      /* TODO: UNIQUE TILES IMPLEMENTATIONS
     * public Tile guild();
     * public Tile player(); OPTIONAL
     */
    
}
