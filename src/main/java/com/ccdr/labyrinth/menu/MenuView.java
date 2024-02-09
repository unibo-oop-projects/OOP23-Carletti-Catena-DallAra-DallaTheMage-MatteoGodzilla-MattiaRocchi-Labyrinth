package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.menu.tree.MenuElement;

/**
 * Interface that describes how a view for the main menu is structured.
 */
public interface MenuView {
    /**
     * This method gets called first, when the menu must be shown in the first place.
     */
    void onEnable();

    /**
     * This function should be called every frame in order to draw the menu.
     * @param element the menu element where the player is currently sitting
     */
    void draw(MenuElement element);

    /**
     * This function should be called every time the focused menu element changes in some way.
     * @param element element that changed (should be the same as the one used in the `draw` calls)
     */
    void changed(MenuElement element);

    /**
     * Opposite method of `onEnable`.
     * This method gets called last, when the menu must be closed/hidden.
     */
    void onDisable();
}
