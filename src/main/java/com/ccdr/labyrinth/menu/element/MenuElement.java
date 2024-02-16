package com.ccdr.labyrinth.menu.element;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This abstract class represents a generic MenuElement.
 * This class is meant to be extended with different types of menu elements, each with their own behaviour.
 */
public abstract class MenuElement {
    /**
     * Spotbugs warns us that the parent field "may expose internal representation by returning a reference to a mutable object".
     * The normal way to fix this warning would be to return a copy of an internal state instead of the reference itself.
     * For this class, that 'fix' would not be correct for the following reasons:
     * - 'parent' as a field is only used in the derived classes as a value that is returned by `nextState()`.
     *   It is never used inside the derived classes for their inner behaviour.
     * - Classes extending MenuElement are designed to also contain extra data to function, that means the parent reference
     *   must be mutable in some way.
     * - Creating a copy of the parent would mean doing a recursive deep copy of the entire menu tree,
     *   which is both not necessary and expensive to do.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
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
     * @return the next element where the menu should be positioned (most likely the parent)
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
