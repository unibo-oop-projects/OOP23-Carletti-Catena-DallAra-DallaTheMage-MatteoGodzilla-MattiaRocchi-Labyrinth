package com.ccdr.labyrinth.menu.tree;

public class MenuButtonElement extends MenuElement {

    private Runnable action;

    public MenuButtonElement(String name, Runnable action) {
        super(name);
        this.action = action;
    }

    @Override
    public void up() {
        throw new IllegalStateException("MenuButton does not support up");
    }

    @Override
    public void down() {
        throw new IllegalStateException("MenuButton does not support down");
    }

    @Override
    public MenuElement nextState() {
        throw new IllegalStateException("MenuButton cannot be the currently selected element");
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public void immediate() {
        this.action.run();
    }

}
