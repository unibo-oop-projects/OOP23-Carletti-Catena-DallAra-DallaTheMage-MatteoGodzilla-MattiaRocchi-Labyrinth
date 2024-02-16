package com.ccdr.labyrinth.menu.element;

/**
 * menu element that only displays text. Meant to be used only as a leaf.
 * it runs the runnable provided in the constructor once selected
 */
public final class MenuTextElement extends MenuElement {
    private Runnable action;
    private final String description;

    /**
     * @param name name of this MenuElement
     * @param description Description to show when this object is in focus, can be null
     */
    public MenuTextElement(final String name, final String description) {
        super(name);
        this.description = description;
    }

    /**
     * @param action callback to run once this object is not in focus anymore
     * @return this instance
     */
    public MenuTextElement onAction(final Runnable action) {
        this.action = action;
        return this;
    }

    //This object can be focused by the user, so up and down should silently fail
    //Instead of throwing an exception when the user presses up/down
    @Override
    public void up() { }

    @Override
    public void down() { }

    @Override
    public MenuElement nextState() {
        if (this.action != null) {
            this.action.run();
        }
        return getParent();
    }

    @Override
    public String toString() {
        return getName();
    }

    //MenuTextElement does not need to do anything immediate
    @Override
    public void immediate() { }

    //Getters
    /**
     * @return description of this object
     */
    public String getDescription() {
        return this.description;
    }
}
