package com.ccdr.labyrinth.jfx;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class ExpandCanvas extends Canvas {
    public ExpandCanvas() {}

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
