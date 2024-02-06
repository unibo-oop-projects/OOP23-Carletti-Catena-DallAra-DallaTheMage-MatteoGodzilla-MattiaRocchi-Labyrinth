package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.menu.tree.MenuElement;

public interface MenuView {
    /**
     * This method gets called first, when the menu must be shown in the first place
     */
    void onEnable();

    /**
     * This function should be called every frame in order to draw the menu
     * @param element the menu element where the player is currently sitting.
     */
    void draw(MenuElement element);

    /**
     * This function should be called every time the menu element changes in some way
     * so that the menu view can react to this change
     * @param element element that changed (should be the same as the one used in the `draw` calls)
     */
    void changed(MenuElement element);

    /**
     * Opposite method of `onEnable`.
     * This method gets called last, when the menu must be closed/hidden.
     */
    void onDisable();
}
