package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.TileCreator;
import com.ccdr.labyrinth.game.loader.GameBoard;

public class GameLoader {
    public static Board generateTiles(final GameConfig config) {
        Board gameBoard = new GameBoard();
        gameBoard.setHeight(config.getLabyrinthHeight());
        gameBoard.setWidth(config.getLabyrinthWidth());
        gameBoard.setMap(new TileCreator(config).generateTiles());
        return gameBoard;
    }
}
