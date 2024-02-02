package com.ccdr.labyrinth.menu.tree;

import java.util.*;
import java.util.function.*;

public class MenuChoiceElement<T> extends MenuElement{

    private Consumer<T> action;
    private int chosenIndex;
    private List<T> values;


    public MenuChoiceElement(String name, int defaultIndex, List<T> values, Consumer<T> action) {
        super(name);
        this.action = action;
        this.values = values;
        this.chosenIndex = defaultIndex;
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
