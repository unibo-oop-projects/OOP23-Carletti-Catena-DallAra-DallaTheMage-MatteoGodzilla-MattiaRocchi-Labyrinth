package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.menu.tree.MenuElement;

public interface MenuView {
    void onEnable();

    void draw(MenuElement element);

    void onDisable();
}
