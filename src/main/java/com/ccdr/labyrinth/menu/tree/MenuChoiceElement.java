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
    private int lastChoiceIndex;
    private int choosingIndex;
    private final List<T> values;

    /**
     * @param name Entry name shown when it appears on a list.
     * @param values Immutable list of possible values that has all the possible choices.
     */
    public MenuChoiceElement(final String name, final List<T> values) {
        super(name);
        //Yes, i do realise this might be unnecessary and expensive.
        this.values = List.copyOf(values);
    }

    /**
     * sets the starting index (meant to be used while constructing the object in a fluent way).
     * @param defaultIndex numeric value of the index
     * @return this instance
     */
    public MenuChoiceElement<T> defaultIndex(final int defaultIndex) {
        if (0 <= defaultIndex && defaultIndex < this.values.size()) {
            this.lastChoiceIndex = defaultIndex;
            this.choosingIndex = defaultIndex;
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
        if (this.choosingIndex > 0) {
            this.choosingIndex--;
        }
    }

    @Override
    public void down() {
        if (this.choosingIndex + 1 < this.values.size()) {
            this.choosingIndex++;
        }
    }

    @Override
    public MenuElement nextState() {
        if (this.action != null) {
            this.action.accept(values.get(this.choosingIndex));
            this.lastChoiceIndex = this.choosingIndex;
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
        return this.choosingIndex;
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.values.get(this.lastChoiceIndex).toString();
    }

    //MenuChoiceElement does not require to do anything immediate
    @Override
    public void immediate() { }
}
