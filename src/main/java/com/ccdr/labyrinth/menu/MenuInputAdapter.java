package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.game.GameController;

import javafx.scene.input.KeyEvent;

public class MenuInputAdapter {
    private MenuController menuController;
    private GameController gameController;

    public MenuInputAdapter(MenuController controller, GameController gameController){
        this.menuController = controller;
        this.gameController = gameController;
    }

    public void onKeyPressed(KeyEvent event){
        switch(event.getCode()){
            case SPACE:
                menuController.switchToGame();
                gameController.init(menuController.getConfig());
                break;
            default:
                break;
        }
    }

    public void onKeyReleased(KeyEvent event){

    }
}
