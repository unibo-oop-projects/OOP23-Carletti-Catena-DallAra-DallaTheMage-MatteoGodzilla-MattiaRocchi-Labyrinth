package com.ccdr.labyrinth.engine;

public interface Executor {
    public enum ID {
        MENU,
        GAME
    }

    void onEnable(Engine engine);
    void update(double deltaTime);
    void onDisable();
}
