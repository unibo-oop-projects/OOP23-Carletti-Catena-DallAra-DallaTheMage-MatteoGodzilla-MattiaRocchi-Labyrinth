package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.jfx.AspectRatioCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;
import com.ccdr.labyrinth.menu.tree.MenuChoiceElement;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;
import com.ccdr.labyrinth.menu.tree.MenuRootElement;
import com.ccdr.labyrinth.menu.tree.MenuTextElement;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
        layout.setAlignment(Pos.CENTER);
        this.scene = new Scene(layout, Color.BLACK);
    }

    @Override
    public void onEnable() {
        Platform.runLater(()->{
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
            context.setFill(Color.BLACK);
            context.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            context.setTextBaseline(VPos.TOP);
            context.setTextAlign(TextAlignment.CENTER);
            context.setFill(Color.WHITESMOKE);
            context.fillText(element.getName(), this.canvas.getWidth()/2, 0);
            y += fontSize;

            if(element instanceof MenuListElement){
                // draw the list elements below (only the name, not everything else)
                MenuListElement listElement = (MenuListElement)element;
                double listFontSize = fontSize*2/3;
                context.setTextAlign(TextAlignment.LEFT);
                context.setFont(Font.font(listFontSize));
                double startY = y;
                if(element instanceof MenuRootElement){
                    startY = this.canvas.getHeight() - listElement.getElements().size() * listFontSize - fontSize;
                    y = startY;
                }
                for (MenuElement child : listElement.getElements()) {
                    context.fillText(child.toString(), fontSize, y);
                    y += listFontSize;
                }
                context.fillText(">", 0, startY + listElement.getIndex() * listFontSize);
            }
            else if(element instanceof MenuTextElement){
                //draw additional description at the bottom
                MenuTextElement textElement = (MenuTextElement)element;
                double descriptionFontSize = fontSize/2;
                context.setTextAlign(TextAlignment.CENTER);
                context.setFont(Font.font(descriptionFontSize));
                context.fillText(textElement.getDescription(), this.canvas.getWidth()/2, y+descriptionFontSize);
            } else if(element instanceof MenuChoiceElement){
                //draw choices like as if they were in a list like MenuListElement
                MenuChoiceElement choiceElement = (MenuChoiceElement)element;
                double listFontSize = fontSize*2/3;
                context.setTextAlign(TextAlignment.LEFT);
                context.setFont(Font.font(listFontSize));
                for(Object choice : choiceElement.getChoices()){
                    context.fillText(choice.toString(), fontSize, y);
                    y += listFontSize;
                }
                context.fillText(">", 0, fontSize + choiceElement.getIndex()*listFontSize);
            }
            //draw tooltip at the bottom
            context.setFont(Font.font(fontSize/3));
            context.setTextBaseline(VPos.BOTTOM);
            context.setTextAlign(TextAlignment.CENTER);
            context.fillText("Enter: Confirm | Up/Down: Move cursor | Esc/Backspace: Go back", this.canvas.getWidth()/2, this.canvas.getHeight());
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
