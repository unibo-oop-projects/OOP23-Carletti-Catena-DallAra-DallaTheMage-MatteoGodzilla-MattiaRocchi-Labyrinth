package com.ccdr.labyrinth.menu.tree;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class behaves just like {@link MenuListElement}, but its main purpose
 * is to show a list of arbitrary objects that the player can choose.
 * This class is not meant to have children deeper than two levels
 * @param <T> T:type of elements that compose the list of possible choices
 */
public final class MenuChoiceElement<T> extends MenuElement {

    private Consumer<T> action;
    private int chosenIndex;
    private final List<T> values;

    /**
     * @param name Entry name shown when it appears on a list.
     * @param values Immutable list of possible values that has all the possible choices.
     */
    public MenuChoiceElement(final String name, final List<T> values) {
        super(name);
        this.values = values;
    }

    /**
     * sets the starting index (meant to be used while constructing the object in a fluent way).
     * @param defaultIndex numeric value of the index
     * @return this instance
     */
    public MenuChoiceElement<T> defaultIndex(final int defaultIndex) {
        if (0 <= defaultIndex && defaultIndex < this.values.size()) {
            this.chosenIndex = defaultIndex;
            return this;
        } else {
            throw new IllegalStateException("default index must be a valid index in the possible choices");
        }
    }

    /**
     * @param action callback that should be called once the user has finally chosen an option
     * @return this instance
     */
    public MenuChoiceElement<T> action(final Consumer<T> action) {
        this.action = action;
        return this;
    }

    @Override
    public void up() {
        if (this.chosenIndex > 0) {
            this.chosenIndex--;
        }
    }

    @Override
    public void down() {
        if (this.chosenIndex + 1 < values.size()) {
            this.chosenIndex++;
        }
    }

    @Override
    public MenuElement nextState() {
        if (this.action != null) {
            this.action.accept(values.get(chosenIndex));
        }
        return getParent();
    }

    /**
     * @return the list of possible choices (used to show them to the user)
     */
    public List<T> getChoices() {
        return Collections.unmodifiableList(values);
    }

    /**
     * @return the index of the entry while the user is choosing an option.
     */
    public int getIndex() {
        return this.chosenIndex;
    }

    @Override
    public String toString() {
        return this.getName() + ":" + this.values.get(chosenIndex).toString();
    }

    //MenuChoiceElement does not require to do anything immediate
    @Override
    public void immediate() { }
}
