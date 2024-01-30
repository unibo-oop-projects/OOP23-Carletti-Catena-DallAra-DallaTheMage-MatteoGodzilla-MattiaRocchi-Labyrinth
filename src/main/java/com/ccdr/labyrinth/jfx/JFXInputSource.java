package com.ccdr.labyrinth.jfx;

import javafx.scene.input.KeyEvent;


public interface JFXInputSource {
    public interface Receiver {
        void onKeyPressed(KeyEvent event);
        void onKeyReleased(KeyEvent event);
    }

    void routeKeyboardEvents(Receiver receiver);
}
