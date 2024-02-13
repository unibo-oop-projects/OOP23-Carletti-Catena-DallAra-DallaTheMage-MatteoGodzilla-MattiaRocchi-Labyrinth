package com.ccdr.labyrinth.result;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

public class ResultInputAdapter implements JFXInputSource.Receiver{

    private ResultInputs controller;

    public ResultInputAdapter(final ResultInputs controller){
        this.controller = controller;
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        switch(event.getCode()){
            case ENTER:
            case SPACE:
                this.controller.close();
                break;
            default:
                break;
        }
    }

}
