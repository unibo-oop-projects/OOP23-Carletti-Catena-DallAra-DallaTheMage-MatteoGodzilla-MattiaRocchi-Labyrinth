package com.ccdr.labyrinth.game.generator;

import java.util.Random;

import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.game.tiles.Tile;
import com.ccdr.labyrinth.game.util.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class CoordinateGenerator {
    private final Random randomGenerator;
    private final GameConfig configuration;
    private final int centerWidth, centerHeight;
    private final int centerWidthRadius, centerHeightRadius;
    private static final int PARTIAL = 3;
    private static final int ENTIRE = 5;

    public CoordinateGenerator(final GameConfig configuration) {
        this.configuration = configuration;
        this.randomGenerator = new Random();
        this.centerWidth = Math.round(this.configuration.getLabyrinthWidth() * PARTIAL / ENTIRE);
        this.centerHeight = Math.round(this.configuration.getLabyrinthHeight() * PARTIAL / ENTIRE);
        this.centerWidthRadius = Math.floorDiv(centerWidth, 2);
        this.centerHeightRadius = Math.floorDiv(centerHeight, 2);
    }

    public Coordinate generateRandomCoordinate(final Map<Coordinate, Tile> board) {
        final int start = 0;
        Coordinate coordinate;
        int row, column;
        do {
            row = randomGenerator.nextInt(start, this.configuration.getLabyrinthHeight());
            column = randomGenerator.nextInt(start, this.configuration.getLabyrinthWidth());
            coordinate = new Coordinate(row, column);
        } while (board.containsKey(coordinate));
        return coordinate;
    }

    public List<Coordinate> calculateSourcesCoordinates(final Coordinate center, final Map<Coordinate, Tile> board) {
        double toPlace = this.configuration.getSourceTiles();
        List<Coordinate> valids = new ArrayList<>();
        final double slice = Math.PI * 2 / toPlace;
        double angle = slice;
        while (toPlace-- > 0) {
            valids.add(calculateCirclePoint(Math.min(centerHeightRadius, centerWidthRadius), angle, center));
            angle += slice;
        }

        return valids;
    }

    private Coordinate calculateCirclePoint(final int radius, final double angle, final Coordinate center) {
        double rowCoord = Math.round(center.row() + radius * Math.sin(angle));
        double columnCoord = Math.round(center.column() + radius * Math.cos(angle));
        return new Coordinate((int) rowCoord, (int) columnCoord);
    }
}
