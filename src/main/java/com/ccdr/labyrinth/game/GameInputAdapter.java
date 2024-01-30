package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

public class GameInputAdapter implements JFXInputSource.Receiver {
    private GameController controller;

    public GameInputAdapter(GameController controller){
        this.controller = controller;
    }

    public void onKeyPressed(KeyEvent event){
        switch(event.getCode()){
            case UP:
                break;
            case DOWN:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case ENTER:
                break;
            case TAB:
                controller.switchToMenu();
                break;
            default:
                break;
        }
    }

    public void onKeyReleased(KeyEvent event){

    }
}
