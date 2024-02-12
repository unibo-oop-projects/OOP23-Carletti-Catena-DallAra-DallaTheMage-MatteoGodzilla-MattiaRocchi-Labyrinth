package com.ccdr.labyrinth.jfx;

import javafx.scene.input.KeyEvent;

/**
 * This interface should be used by view classes that generate the keyboard inputs.
 */
public interface JFXInputSource {
    /**
     * This inner interface should be used by other classes that wish to receive keyboard inputs.
     */
    @FunctionalInterface
    interface Receiver {
        /**
         * This function should be called when the user has pressed a key.
         * @param event Keyboard event to handle
         */
        void onKeyPressed(KeyEvent event);
    }

    /**
     * Adds the provided receiver so that the view class can call its handlers inside.
     * @param receiver receiver
     */
    void routeKeyboardEvents(Receiver receiver);
}
