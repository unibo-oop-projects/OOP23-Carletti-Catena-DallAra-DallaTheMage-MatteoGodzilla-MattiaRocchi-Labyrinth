package com.ccdr.labyrinth.jfx;

import javafx.scene.canvas.Canvas;

//this custom component expands to occupy as much space as possible from the stage
public class AspectRatioCanvas extends Canvas {

    private double ratio;

    public AspectRatioCanvas(double targetWidth, double targetHeight){
        this.ratio = targetWidth/targetHeight;
        JFXStage.getStage().widthProperty().addListener((observable, oldVal, newVal) -> handleChange());
        JFXStage.getStage().heightProperty().addListener((observable, oldVal, newVal) -> handleChange());
    }

    private void handleChange() {
        double stageWidth = JFXStage.getStage().getWidth();
        double stageHeight = JFXStage.getStage().getHeight();

        double canvasWidth = Math.min(stageWidth, stageHeight*ratio);
        double canvasHeight = Math.min(stageHeight, stageWidth/ratio);
        setWidth(canvasWidth);
        setHeight(canvasHeight);
    }
}
