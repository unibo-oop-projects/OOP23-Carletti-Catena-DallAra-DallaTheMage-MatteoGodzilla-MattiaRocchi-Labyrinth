package com.ccdr.labyrinth.menu.tree;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class MenuListElement extends MenuElement implements Iterable<MenuElement>{
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

    public double getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        String result = "{List:";
        result += this.index;
        result += ", [";
        for (MenuElement child : children) {
            result += child.toString();
        }
        result += "]}";
        return result;
    }

    @Override
    public Iterator<MenuElement> iterator() {
        return this.children.iterator();
    }
}
