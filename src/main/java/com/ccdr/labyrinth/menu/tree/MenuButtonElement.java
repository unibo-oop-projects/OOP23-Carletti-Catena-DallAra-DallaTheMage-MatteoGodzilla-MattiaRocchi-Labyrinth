package com.ccdr.labyrinth.menu.tree;

/**
 * This class represents a button that activates as soon as it's selected.
 */
public final class MenuButtonElement extends MenuElement {

    private Runnable action;

    /**
     * @param name name of this MenuElement
     * @param action callback to run as soon as this element is selected
     */
    public MenuButtonElement(final String name, final Runnable action) {
        super(name);
        this.action = action;
    }

    @Override
    public void up() {
        throw new IllegalCallerException("MenuButton cannot be focused. It does not support 'up' event ");
    }

    @Override
    public void down() {
        throw new IllegalCallerException("MenuButton cannot be focused. It does not support 'down' event ");
    }

    @Override
    public MenuElement nextState() {
        throw new IllegalCallerException("MenuButton cannot be focused. It does not have a 'next state'");
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
