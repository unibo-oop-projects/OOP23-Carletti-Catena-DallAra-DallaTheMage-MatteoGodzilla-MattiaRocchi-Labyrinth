package com.ccdr.labyrinth.jfx;

import javafx.scene.canvas.Canvas;

/**
 * This custom component is a canvas that tries to occupy as much space as possible from the stage.
 */
public class AspectRatioCanvas extends Canvas {

    private final double ratio;

    /**
     * @param targetWidth initial width of the canvas
     * @param targetHeight initial height of the canvas
     */
    public AspectRatioCanvas(final double targetWidth, final double targetHeight) {
        super(targetWidth, targetHeight);
        this.ratio = targetWidth / targetHeight;
        JFXStage.getStage().widthProperty().addListener((observable, oldVal, newVal) -> handleChange());
        JFXStage.getStage().heightProperty().addListener((observable, oldVal, newVal) -> handleChange());
    }

    private void handleChange() {
        final double stageWidth = JFXStage.getStage().getWidth();
        final double stageHeight = JFXStage.getStage().getHeight();

        final double canvasWidth = Math.min(stageWidth, stageHeight * ratio);
        final double canvasHeight = Math.min(stageHeight, stageWidth / ratio);
        setWidth(canvasWidth);
        setHeight(canvasHeight);
    }
}
