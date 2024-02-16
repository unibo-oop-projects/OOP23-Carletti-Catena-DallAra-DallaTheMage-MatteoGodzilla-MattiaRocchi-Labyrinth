package com.ccdr.labyrinth.menu.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is meant to contain a list of other MenuElement objects.
 * It's used to build the hierarchy of the menu tree available to the player
 */
public final class MenuListElement extends MenuElement {
    private final List<MenuElement> children = new ArrayList<>();
    private int index;

    /**
     * @param name name of this menu element
     * @param elm immutable list of elements that contains all possible child elements
     */
    public MenuListElement(final String name, final MenuElement... elm) {
        super(name);
        for (final MenuElement menuElement : elm) {
            this.children.add(menuElement);
            menuElement.setParent(this);
        }
    }

    @Override
    public void up() {
        if (this.index > 0) {
            this.index--;
        }
    }

    @Override
    public void down() {
        if (this.index + 1 < this.children.size()) {
            this.index++;
        }
    }

    @Override
    public MenuElement nextState() {
        return this.children.get(this.index);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    //MenuListElement does not require to do anything immediate
    @Override
    public void immediate() { }

    //Getters

    /**
     * @return immutable list of menu elements contained inside this object
     */
    public List<MenuElement> getElements() {
        return Collections.unmodifiableList(this.children);
    }

    /**
     * @return index of the currently chosen menu object
     */
    public int getIndex() {
        return this.index;
    }
}
