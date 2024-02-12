package com.ccdr.labyrinth.engine;

/**
 * @see Engine
 */
public interface Executor {
    /**
     * IDs used to differentiate executors in the engine class.
     */
    enum ID {
        /**
         * id used for the main menu.
         */
        MENU,
        /**
         * id used for the gameplay.
         */
        GAME
    }

    /**
     * This function gets called by the engine when this executor switches from inactive to active.
     * @param engine reference to the engine object
     */
    void onEnable();
    /**
     * This function is called every frame from the engine.
     * @param deltaTime time between frames, measured in seconds.
     */
    void update(double deltaTime);
}
