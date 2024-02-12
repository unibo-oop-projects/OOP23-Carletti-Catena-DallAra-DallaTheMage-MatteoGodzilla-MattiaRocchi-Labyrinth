package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

import java.util.Random;
import java.util.Map;

public class CoordinatesManager {
    private final GameConfig configuration;
    private final Random randomGenerator;
    
    public CoordinatesManager (GameConfig configuration) {
        this.configuration = configuration;
        this.randomGenerator = new Random();
    }

    public Coordinate generateRandomCoordinate(final Map<Coordinate, Tile> board) {
        return getValidRandomCoordinate(board, 0, 0, this.configuration.getLabyrinthHeight(), this.configuration.getLabyrinthWidth());
    }

    public Coordinate generateCoordinateNearCenter(final Coordinate CENTER, final Map<Coordinate, Tile> board) {
        final int WIDHT_RADIUS = Math.round(this.configuration.getLabyrinthWidth() / 5);
        final int HEIGHT_RADIUS = Math.round(this.configuration.getLabyrinthHeight() / 5);
        return getValidRandomCoordinate(board, CENTER.row()-HEIGHT_RADIUS, CENTER.column()-WIDHT_RADIUS, CENTER.row()+HEIGHT_RADIUS+1, CENTER.column()+WIDHT_RADIUS+1);
    }

    private Coordinate getValidRandomCoordinate(Map<Coordinate, Tile> board, int minRow, int minColumn, int maxRow, int maxColumn) {
        Coordinate coordinate;
        int row, column;
        do {
            row = randomGenerator.nextInt(minRow, maxRow);
            column = randomGenerator.nextInt(minColumn, maxColumn);
            coordinate = new Coordinate(row , column);
        } while (board.containsKey(coordinate));
        return coordinate;
    }
}
