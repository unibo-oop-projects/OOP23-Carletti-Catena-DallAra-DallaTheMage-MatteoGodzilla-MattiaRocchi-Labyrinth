package com.ccdr.labyrinth.game.loader.generators;

import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CoordinatesGenerator {
    private final GameConfig configuration;
    private final Random randomGenerator;
    private final int CENTER_WIDTH_RADIUS, CENTER_HEIGHT_RADIUS;
    private final int CENTER_WIDTH, CENTER_HEIGHT;


    public CoordinatesGenerator(GameConfig configuration) {
        this.configuration = configuration;
        this.randomGenerator = new Random();
        this.CENTER_WIDTH = Math.round(this.configuration.getLabyrinthWidth() * 3 / 5);
        this.CENTER_HEIGHT = Math.round(this.configuration.getLabyrinthHeight() * 3 / 5);
        this.CENTER_WIDTH_RADIUS = Math.floorDiv(CENTER_WIDTH, 2);
        this.CENTER_HEIGHT_RADIUS = Math.floorDiv(CENTER_HEIGHT, 2);
    }

    public Coordinate generateRandomCoordinate(final Map<Coordinate, Tile> board) {
        final int START = 0;
        return getValidRandomCoordinate(board, START, START, this.configuration.getLabyrinthHeight(),
        this.configuration.getLabyrinthWidth());
    }

    public List<Coordinate> calculateSourcesCoordinates(final Coordinate CENTER, final Map<Coordinate, Tile> board) {
        double toPlace = this.configuration.getSourceTiles();
        List<Coordinate> valids = new ArrayList<>();
        final double SLICE = Math.PI * 2 / toPlace;
        double angle = SLICE;
        while (toPlace-- > 0) {
            valids.add(calculateCirclePoint(Math.min(CENTER_HEIGHT_RADIUS,CENTER_WIDTH_RADIUS), angle, CENTER));
            angle += SLICE;
        }

        return valids;
    }

    private Coordinate calculateCirclePoint(final int radius, final double angle, final Coordinate center) {
        double rowCoord = Math.round(center.row() + radius * Math.sin(angle));
        double columnCoord = Math.round(center.column() + radius*Math.cos(angle));
        return new Coordinate((int)rowCoord, (int)columnCoord);
    }

    private Coordinate getValidRandomCoordinate(final Map<Coordinate, Tile> board,
        final int minRow, final int minColumn, final int maxRow, final int maxColumn
    ) {
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
