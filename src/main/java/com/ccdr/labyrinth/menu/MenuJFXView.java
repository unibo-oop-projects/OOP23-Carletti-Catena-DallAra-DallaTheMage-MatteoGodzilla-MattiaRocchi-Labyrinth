package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.jfx.AspectRatioCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public class MenuJFXView implements MenuView, JFXInputSource {
    private Scene scene;
    private AspectRatioCanvas canvas;

    public MenuJFXView(){
        this.canvas = new AspectRatioCanvas(JFXStage.WINDOW_WIDTH, JFXStage.WINDOW_HEIGHT);
        var layout = new HBox(this.canvas);
        layout.setAlignment(Pos.TOP_LEFT);
        this.scene = new Scene(layout, Color.BLACK);
    }

    @Override
    public void onEnable() {
        Platform.runLater(()->{
            System.out.println("ENABLING MENU");
            JFXStage.getStage().setScene(this.scene);
        });
    }

    @Override
    public void draw(MenuElement element) {
        Platform.runLater(()->{
            var context = this.canvas.getGraphicsContext2D();
            double y = 0;
            double fontSize = this.canvas.getHeight() / 10;
            context.setFont(Font.font(fontSize));
            context.setFill(Color.gray(0.1));
            context.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            context.setTextBaseline(VPos.TOP);
            context.setFill(Color.WHITESMOKE);
            context.fillText(element.getName(), 0, 0);
            y += fontSize;
            if(element instanceof MenuListElement){
                MenuListElement listElement = (MenuListElement)element;
                for (MenuElement child : listElement) {
                    context.fillText(child.getName(), fontSize, y);
                    y += fontSize;
                }
                context.fillText(">", 0, fontSize + (listElement.getIndex()) * fontSize);
            }
        });
    }

    //this is left empty, for now
    @Override
    public void onDisable() {}

    @Override
    public void routeKeyboardEvents(Receiver adapter){
        this.scene.setOnKeyPressed(adapter::onKeyPressed);
        this.scene.setOnKeyReleased(adapter::onKeyReleased);
    }

}
