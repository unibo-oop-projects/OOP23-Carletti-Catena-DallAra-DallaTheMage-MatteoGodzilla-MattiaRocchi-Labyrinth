package com.ccdr.labyrinth.game;

// TODO - OBJECTIVES MANAGEMENT COMPONENT
//import com.ccdr.labyrinth.game.loader.Objective;
import com.ccdr.labyrinth.game.loader.TileCreator;
import com.ccdr.labyrinth.game.loader.GameBoard;

public class GameLoader {
    public static Board generateTiles(final GameConfig config) {
        Board gameBoard = new GameBoard();
        gameBoard.setHeight(config.getLabyrinthHeight());
        gameBoard.setWidth(config.getLabyrinthWidth());
        gameBoard.setMap(TileCreator.generateTiles(config));
        return gameBoard;
    }
} 
