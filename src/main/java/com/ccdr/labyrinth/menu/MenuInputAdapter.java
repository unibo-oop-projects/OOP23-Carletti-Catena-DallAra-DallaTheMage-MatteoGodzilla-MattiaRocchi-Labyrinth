package com.ccdr.labyrinth.menu;

import javafx.scene.input.KeyEvent;

public class MenuInputAdapter {
    private MenuController controller;

    public MenuInputAdapter(MenuController controller){
        this.controller = controller;
    }

    public void onKeyPressed(KeyEvent event){
        switch(event.getCode()){
            case SPACE:
                controller.switchToGame();
                break;
            default:
                break;
        }
    }

    public void onKeyReleased(KeyEvent event){

    }
}
