package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.JFXStage;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public class MenuJFXView implements MenuView {
    private Scene scene;
    private Canvas canvas;

    //the stage must be created outside of this view,
    public MenuJFXView(){
        this.canvas = new Canvas(JFXStage.WINDOW_WIDTH, JFXStage.WINDOW_HEIGHT);
        this.scene = new Scene(new Group(this.canvas), Color.BLACK);
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
            double fontSize = context.getFont().getSize();
            context.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
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

    public void routeKeyboardEvents(MenuInputAdapter adapter){
        this.scene.setOnKeyPressed(adapter::onKeyPressed);
        this.scene.setOnKeyReleased(adapter::onKeyReleased);
    }

}
