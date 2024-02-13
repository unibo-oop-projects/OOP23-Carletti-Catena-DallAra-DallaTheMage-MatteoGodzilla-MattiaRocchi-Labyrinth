package com.ccdr.labyrinth.result;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

/**
 * Class responsible for routing the events from Javafx to the ResultController.
 */
public final class ResultInputAdapter implements JFXInputSource.Receiver {

    private ResultInputs controller;

    /**
     * @param controller indirect reference to ResultController
     */
    public ResultInputAdapter(final ResultInputs controller) {
        this.controller = controller;
    }

    @Override
    public void onKeyPressed(final KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
            case SPACE:
                this.controller.close();
                break;
            default:
                break;
        }
    }

}
