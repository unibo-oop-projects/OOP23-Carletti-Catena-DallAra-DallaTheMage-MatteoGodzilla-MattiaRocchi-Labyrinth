package com.ccdr.labyrinth.game;

public class ContextManager{
    private Context activeContext;

    public ContextManager(){
        //construct the entire Context chain here
        //activeContext = new PlayersManager(4)
    }

    //these methods will be called from GameInputAdapter/GameController in some way
    public void up(){
        this.activeContext.up();
        switchContextIfNecessary();
    }

    public void down(){
        this.activeContext.down();
        switchContextIfNecessary();
    }

    public void left() {
        this.activeContext.left();
        switchContextIfNecessary();
    }

    public void right() {
        this.activeContext.right();
        switchContextIfNecessary();
    }

    public void primary() {
        this.activeContext.primary();
        switchContextIfNecessary();
    }

    public void secondary() {
        this.activeContext.secondary();
        switchContextIfNecessary();
    }

    public void tertiary() {
        this.activeContext.tertiary();
        switchContextIfNecessary();
    }

    //this should instead check continuously, not just when the player does something
    private void switchContextIfNecessary() {
        if(this.activeContext.done()){
            this.activeContext = this.activeContext.getNextContext();
        }
    }
}
