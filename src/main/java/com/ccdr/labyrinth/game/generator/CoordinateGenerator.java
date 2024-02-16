package com.ccdr.labyrinth.game.generator;

import java.util.Random;

import com.ccdr.labyrinth.game.tiles.Tile;
import com.ccdr.labyrinth.game.util.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class CoordinateGenerator {
    private static final int PARTIAL = 3;
    private static final int ENTIRE = 5;
    private final Random randomGenerator;
    private final int labyrinthHeight, labyrinthWidth, sourceNumber;

    public CoordinateGenerator(final int labyrinthHeight, final int labyrinthWidth, final int sourceNumber) {
        this.labyrinthHeight = labyrinthHeight;
        this.labyrinthWidth = labyrinthWidth;
        this.sourceNumber = sourceNumber;
        this.randomGenerator = new Random();
    }

    public Coordinate generateRandomCoordinate(final Map<Coordinate, Tile> board) {
        final int start = 0;
        Coordinate coordinate;
        int row, column;
        do {
            row = randomGenerator.nextInt(start, this.labyrinthHeight);
            column = randomGenerator.nextInt(start, this.labyrinthWidth);
            coordinate = new Coordinate(row, column);
        } while (board.containsKey(coordinate));
        return coordinate;
    }

    public List<Coordinate> calculateSourcesCoordinates(final Coordinate center, final Map<Coordinate, Tile> board) {
        final int centerWidth, centerHeight;
        final int centerWidthRadius, centerHeightRadius;
        final List<Coordinate> valids = new ArrayList<>();
        double toPlace = this.sourceNumber;
        final double slice = Math.PI * 2 / toPlace;
        double angle = slice;
        centerWidth = this.labyrinthWidth * PARTIAL / ENTIRE;
        centerHeight = this.labyrinthHeight * PARTIAL / ENTIRE;
        centerWidthRadius = Math.floorDiv(centerWidth, 2);
        centerHeightRadius = Math.floorDiv(centerHeight, 2);
        while (toPlace > 0) {
            valids.add(calculateCirclePoint(Math.min(centerHeightRadius, centerWidthRadius), angle, center));
            angle += slice;
            toPlace--;
        }
        return valids;
    }

    private Coordinate calculateCirclePoint(final int radius, final double angle, final Coordinate center) {
        final double rowCoord = Math.round(center.row() + radius * Math.sin(angle));
        final double columnCoord = Math.round(center.column() + radius * Math.cos(angle));
        return new Coordinate((int) rowCoord, (int) columnCoord);
    }
}
