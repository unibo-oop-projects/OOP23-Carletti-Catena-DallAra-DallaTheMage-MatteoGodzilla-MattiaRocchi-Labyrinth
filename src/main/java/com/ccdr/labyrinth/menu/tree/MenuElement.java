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

    /**
     * This method is called when
     * this MenuElement is currently in focus in the menu
     * and the player pressed the up key.
     */
    public abstract void up();

    /**
     * This method is called when
     * this MenuElement is currently in focus in the menu
     * and the player pressed the down key.
     */
    public abstract void down();

    /**
     * This method is called when
     * this MenuElement is currently in focus in the menu
     * and the player presses the select key
     * @return the next element where the menu should be positioned
     * (most likely the parent )
     */
    public abstract MenuElement nextState();

    /**
     * This method is called as soon as this MenuElement becomes the currently
     * shown element in the menu
     */
    public abstract void immediate();
}
