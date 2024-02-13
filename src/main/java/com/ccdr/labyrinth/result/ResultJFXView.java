package com.ccdr.labyrinth.result;

import com.ccdr.labyrinth.jfx.ExpandCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ResultJFXView implements ResultView, JFXInputSource{
    //same ones as the menu
    private static final Color BASE_COLOR = Color.gray(0.1);
    private static final Color TEXT_FILL = Color.valueOf("#bbbbbb");

    private final Scene scene;
    private final ExpandCanvas canvas;
    private double headerFontSize;
    private double padding;
    private double hintFontSize;

    public ResultJFXView(){
        this.canvas = new ExpandCanvas();
        this.scene = new Scene(new Group(this.canvas), BASE_COLOR);
        this.canvas.bind(this.scene);
    }

    @Override
    public void onEnable() {
        Platform.runLater(() -> {
            System.out.println("ENABLED");
            JFXStage.getStage().setScene(this.scene);
            //Force resize of canvas so it fills the entire stage
            JFXStage.getStage().setWidth(JFXStage.getStage().getWidth());
            JFXStage.getStage().setHeight(JFXStage.getStage().getHeight());
        });
    }

    @Override
    public void draw() {
        Platform.runLater(() -> {
            GraphicsContext context = this.canvas.getGraphicsContext2D();
            context.setFill(BASE_COLOR);
            context.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            recalculateFontSizes();

            //draw header
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.TOP);
            context.setFill(TEXT_FILL);
            context.setFont(Font.font(this.headerFontSize));
            context.fillText("Results", this.canvas.getWidth()/2, this.padding);

            //draw hint at the bottom
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.BOTTOM);
            context.setFill(TEXT_FILL);
            context.setFont(Font.font(this.hintFontSize));
            context.fillText("Press Enter to close", this.canvas.getWidth()/2, this.canvas.getHeight() - this.padding);
        });
    }

    @Override
    public void routeKeyboardEvents(Receiver receiver) {
        this.scene.setOnKeyPressed(receiver::onKeyPressed);
    }

    private void recalculateFontSizes() {
        final double referenceHeight = Math.min(this.canvas.getHeight(), this.canvas.getWidth() * 3 / 4);
        final double baseFontSize = referenceHeight / 10;
        // this.logoSize = baseFontSize * 2;
        this.headerFontSize = baseFontSize;
        // this.listFontSize = baseFontSize * 2 / 3;
        // this.descriptionFontSize = baseFontSize / 2;
        this.hintFontSize = baseFontSize / 3;
        this.padding = baseFontSize / 10;
    }

}
