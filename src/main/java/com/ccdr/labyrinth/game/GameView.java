package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Board;

public interface GameView {
    void onEnable();

    void draw(double fps);

    void drawBoard(Board board);

    void onDisable();
}
