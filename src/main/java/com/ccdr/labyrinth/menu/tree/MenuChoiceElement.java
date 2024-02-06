package com.ccdr.labyrinth.menu.tree;

import java.util.*;
import java.util.function.*;

/**
 * This class behaves just like {@link MenuListElement}, but its main purpose
 * is to show a list of arbitrary objects that the player can choose.
 * This class is not meant to have children deeper than two levels
 */
public class MenuChoiceElement<T> extends MenuElement{

    private Consumer<T> action;
    private int chosenIndex;
    private List<T> values;


    public MenuChoiceElement(String name, List<T> values) {
        super(name);
        this.values = values;
    }

    public MenuChoiceElement<T> setDefaultIndex(int defaultIndex) {
        this.chosenIndex = defaultIndex;
        return this;
    }

    public MenuChoiceElement<T> setAction(Consumer<T> action) {
        this.action = action;
        return this;
    }

    @Override
    public void up() {
        if(this.chosenIndex > 0){
            this.chosenIndex--;
        }
    }

    @Override
    public void down() {
        if(this.chosenIndex+1 < values.size()){
            this.chosenIndex++;
        }
    }

    @Override
    public MenuElement nextState() {
        if(this.action != null){
            this.action.accept(values.get(chosenIndex));
        }
        return getParent();
    }

    public T getChoice(){
        return this.values.get(chosenIndex);
    }

    public List<T> getChoices(){
        return Collections.unmodifiableList(values);
    }

    public int getIndex(){
        return this.chosenIndex;
    }

    @Override
    public String toString() {
        return this.getName() + ":" + this.values.get(chosenIndex).toString();
    }

    //MenuChoiceElement does not require to do anything immediate
    @Override
    public void immediate() {}
}
