package com.ccdr.labyrinth.menu.tree;

/**
 * This abstract class represents a generic MenuElement.
 * This class is meant to be extended with different types of menu elements, each with their own behaviour.
 */
public abstract class MenuElement {
    private MenuElement parent;
    private String name;

    /**
     * @param name initial name of this MenuElement object
     */
    public MenuElement(final String name) {
        setName(name);
    }

    /**
     * This method is called when:
     * - this MenuElement is currently in focus in the menu
     * - the player pressed the up key.
     */
    public abstract void up();

    /**
     * This method is called when:
     * - this MenuElement is currently in focus in the menu
     * - the player pressed the down key.
     */
    public abstract void down();

    /**
     * This method is called when:
     * - this MenuElement is currently in focus in the menu
     * - the player presses the select key.
     * @return the next element where the menu should be positioned
     * (most likely the parent )
     */
    public abstract MenuElement nextState();

    /**
     * This method is called as soon as this MenuElement becomes the currently shown element in the menu.
     */
    public abstract void immediate();

    //Getters and Setters
    /**
     * @param parent reference to parent object
     */
    public final void setParent(final MenuElement parent) {
        this.parent = parent;
    }

    /**
     * @return reference to parent object
     */
    public final MenuElement getParent() {
        return this.parent;
    }

    /**
     * @param name new name of this MenuElement object
     */
    protected final void setName(final String name) {
        this.name = name;
    }

    /**
     * @return name of this MenuElement object
     */
    public final String getName() {
        return this.name;
    }
}
