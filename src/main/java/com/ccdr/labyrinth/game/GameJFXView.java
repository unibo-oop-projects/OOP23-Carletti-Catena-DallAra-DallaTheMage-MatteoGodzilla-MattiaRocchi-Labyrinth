package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.JFXStage;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public class GameJFXView implements GameView {
    private Scene scene;
    private Canvas canvas;

    public GameJFXView(){
        this.canvas = new Canvas(JFXStage.WINDOW_WIDTH,JFXStage.WINDOW_HEIGHT);
        this.scene = new Scene(new Group(this.canvas), Color.GREEN);
    }

    @Override
    public void onEnable() {
        Platform.runLater(()->{
            System.out.println("ENABLED GAME");
            JFXStage.getStage().setScene(this.scene);
        });
    }

    @Override
    public void draw(double framerate) {
        Platform.runLater(()->{
            var context2d = this.canvas.getGraphicsContext2D();
            context2d.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            context2d.setFill(Color.BLACK);
            context2d.setTextBaseline(VPos.TOP);
            context2d.fillText("FPS:"+framerate, 0, 0);
        });
    }

    //Left empty, for now
    @Override
    public void onDisable() {

    }

}
