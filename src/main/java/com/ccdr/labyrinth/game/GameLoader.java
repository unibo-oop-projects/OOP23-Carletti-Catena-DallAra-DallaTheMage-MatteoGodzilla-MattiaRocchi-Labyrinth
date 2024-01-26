package com.ccdr.labyrinth.game;

// TODO - OBJECTIVES MANAGEMENT COMPONENT
//import com.ccdr.labyrinth.game.loader.Objective;
import com.ccdr.labyrinth.game.loader.TileCreatorImpl;
import com.ccdr.labyrinth.game.loader.GameBoard;

public class GameLoader {
    public static Board generateTiles(GameConfig config) {
        Board gameBoard = new GameBoard();
        int toGenerate = config.getLabyrinthHeight()*config.getLabyrinthWidth()-config.getSourceTiles()-config.getGuildNum();
        gameBoard.setHeight(config.getLabyrinthHeight());
        gameBoard.setWidth(config.getLabyrinthWidth());
        gameBoard.remap(new TileCreatorImpl().generateTiles(toGenerate, config.getSourceTiles()));
        return gameBoard;
    }
}
