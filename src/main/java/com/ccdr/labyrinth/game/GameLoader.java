package com.ccdr.labyrinth.game;

// TODO - OBJECTIVES MANAGEMENT COMPONENT
//import com.ccdr.labyrinth.game.loader.Objective;
import com.ccdr.labyrinth.game.loader.TileCreatorImpl;
import com.ccdr.labyrinth.game.loader.GameBoard;

public class GameLoader {
    public static Board generateTiles(GameConfig config) {
        Board gameBoard = new GameBoard();
        gameBoard.setHeight(config.getLabyrinthHeight());
        gameBoard.setWidth(config.getLabyrinthWidth());
        gameBoard.setMap(new TileCreatorImpl().generateTiles(config.getLabyrinthHeight(), config.getLabyrinthWidth(), config.getSourceTiles()));
        return gameBoard;
    }
}
