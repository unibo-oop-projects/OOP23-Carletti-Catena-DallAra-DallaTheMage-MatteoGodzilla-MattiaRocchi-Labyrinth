package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.JFXStage;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public class MenuJFXView implements MenuView {
    private Scene scene;

    //the stage must be created outside of this view,
    public MenuJFXView(){
        this.scene = new Scene(new Group(), Color.AQUA);
    }

    @Override
    public void onEnable() {
        Platform.runLater(()->{
            System.out.println("ENABLING MENU");
            JFXStage.getStage().setScene(this.scene);
        });
    }

    @Override
    public void draw() {

    }

    //this is left empty, for now
    @Override
    public void onDisable() {}

}
