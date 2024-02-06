package com.ccdr.labyrinth.menu.tree;

import java.util.*;

/**
 * This class is meant to contain a list of other MenuElement objects.
 * It's used to build the hierarchy of the menu tree available to the player
 */
public class MenuListElement extends MenuElement{
    private List<MenuElement> children = new ArrayList<>();
    private int index = 0;

    public MenuListElement(String name, MenuElement ...elm){
        super(name);
        for (MenuElement menuElement : elm) {
            this.add(menuElement);
        }
    }

    @Override
    public void up() {
        if(this.index > 0){
            this.index--;
        }
    }

    @Override
    public void down() {
        if(this.index + 1 < this.children.size()){
            this.index++;
        }
    }

    @Override
    public MenuElement nextState() {
        return this.children.get(this.index);
    }

    private void add(MenuElement menuElement) {
        this.children.add(menuElement);
        menuElement.setParent(this);
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public List<MenuElement> getElements(){
        return Collections.unmodifiableList(this.children);
    }

    //MenuListElement does not require to do anything immediate
    @Override
    public void immediate() {}
}
