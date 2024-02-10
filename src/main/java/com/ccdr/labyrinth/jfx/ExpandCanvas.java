package com.ccdr.labyrinth.jfx;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class ExpandCanvas extends Canvas {
    public ExpandCanvas(final Stage stage) {
        stage.widthProperty().addListener((observable, oldVal, newVal) -> {
            this.setWidth(newVal.doubleValue());
        });
        stage.heightProperty().addListener((observable, oldVal, newVal) -> {
            this.setHeight(newVal.doubleValue());
        });
    }
}
