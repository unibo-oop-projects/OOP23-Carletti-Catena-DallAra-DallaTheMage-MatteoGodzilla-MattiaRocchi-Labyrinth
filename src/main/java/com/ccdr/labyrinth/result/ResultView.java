package com.ccdr.labyrinth.result;

public interface ResultView {
    /**
     * This method gets called first, when the menu must be shown in the first place.
     */
    void onEnable();

    /**
     * Generic draw method, for now
     */
    void draw();
}
