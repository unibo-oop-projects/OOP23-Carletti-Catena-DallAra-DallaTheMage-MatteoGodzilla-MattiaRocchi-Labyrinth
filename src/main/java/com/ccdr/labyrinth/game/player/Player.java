package com.ccdr.labyrinth.game.player;

import com.ccdr.labyrinth.game.loader.Coordinate;

public interface Player {
    void moveUp(Coordinate coord);

    void moveRight(Coordinate coord);

    void moveLeft(Coordinate coord);

    void moveDown(Coordinate coord);

    void drawPlayers();

    Player getPlayer();

    int getRow();

    int getColumn();
}
