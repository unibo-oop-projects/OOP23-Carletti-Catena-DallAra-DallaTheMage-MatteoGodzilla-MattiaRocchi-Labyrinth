package com.ccdr.labyrinth.menu.tree;

public abstract class MenuElement {
    private MenuElement parent;
    private String name;

    public MenuElement(String name){
        setName(name);
    }

    public void setParent(MenuElement parent){
        this.parent = parent;
    }
    public MenuElement getParent(){
        return this.parent;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public abstract void up();
    public abstract void down();
    public abstract MenuElement nextState();
}
