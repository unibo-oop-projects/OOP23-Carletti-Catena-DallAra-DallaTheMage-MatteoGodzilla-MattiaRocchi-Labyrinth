package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

public class GameInputAdapter implements JFXInputSource.Receiver {
    private GameInputs controller;

    public GameInputAdapter(final GameInputs controller){
        this.controller = controller;
    }

    public void onKeyPressed(KeyEvent event){
        switch(event.getCode()){
            case W:
            case UP:
                this.controller.up();
                break;
            case D:
            case RIGHT:
                this.controller.right();
                break;
            case A:
            case LEFT:
                this.controller.left();
                break;
            case S:
            case DOWN:
                this.controller.down();
                break;
            case ENTER:
            case SPACE:
                this.controller.primary();
                break;
            case TAB:
            case CONTROL:
                this.controller.secondary();
                break;
            case ESCAPE:
            case BACK_SPACE:
                this.controller.back();
                break;
            default:
                break;
        }
    }

    public void onKeyReleased(KeyEvent event){}
}
