package com.ccdr.labyrinth.game;

// TODO - OBJECTIVES MANAGEMENT COMPONENT
//import com.ccdr.labyrinth.game.loader.Objective;
import com.ccdr.labyrinth.game.loader.TileCreatorFactoryImpl;
import com.ccdr.labyrinth.game.loader.Board;
import com.ccdr.labyrinth.game.loader.GameBoard;

public class GameLoader {
    public static Board generateTiles(GameConfig config) {
        Board gameBoard = new GameBoard();
        int toGenerate = config.getLabyrinthHeight()*config.getLabyrinthWidth()-config.getSourceTiles()-config.getGuildNum();
        gameBoard.setHeight(config.getLabyrinthHeight());
        gameBoard.setWidth(config.getLabyrinthWidth());
        gameBoard.remap(new TileCreatorFactoryImpl().source().generateMany(config.getSourceTiles()));
        gameBoard.remap(new TileCreatorFactoryImpl().normal().generateMany(toGenerate));
        return gameBoard;
    }
}
