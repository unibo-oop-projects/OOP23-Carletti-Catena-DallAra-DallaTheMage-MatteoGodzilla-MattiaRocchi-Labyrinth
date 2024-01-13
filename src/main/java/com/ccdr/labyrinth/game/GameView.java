package com.ccdr.labyrinth.game;

public interface GameView {
    void onEnable();

    void draw(double fps);

    void onDisable();
}
