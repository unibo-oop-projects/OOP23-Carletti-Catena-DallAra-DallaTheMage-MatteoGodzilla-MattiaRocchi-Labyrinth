package com.ccdr.labyrinth.jfx;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

/**
 * This custom component implements a canvas that automatically resizes in order to fill the entire stage.
 */
public final class ExpandCanvas extends Canvas {
    /**
     * @param scene reference scene where the canvas is placed in
     */
    public void bind(final Scene scene) {
        scene.widthProperty().addListener((observable, oldVal, newVal) -> {
            this.setWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((observable, oldVal, newVal) -> {
            this.setHeight(newVal.doubleValue());
        });
        this.setWidth(scene.getWidth());
        this.setHeight(scene.getHeight());
    }
}
