package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.TypeImag_MENU;
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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public class MenuJFXView implements MenuView, JFXInputSource {
    private Scene scene;
    private AspectRatioCanvas canvas;
    //variables used for resizing text elements
    private double baseFontSize;
    private double listFontSize;
    private double descriptionFontSize;
    private double hintFontSize;
    private double headerFontSize;
    private double padding;

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
            GraphicsContext context = this.canvas.getGraphicsContext2D();
            recalculateFontSizes();
            context.setFill(Color.gray(0.1));
            context.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            drawHeader(context, element);
            if(element instanceof MenuListElement){
                if(element instanceof MenuRootElement){
                    drawLogo(context);
                }
                drawList(context,(MenuListElement)element);
            } else if(element instanceof MenuTextElement){
                drawText(context,(MenuTextElement)element);
            } else if(element instanceof MenuChoiceElement){
                drawChoice(context, (MenuChoiceElement)element);
            }
            drawHint(context);
        });
    }

    //all these functions below are called from the JFX thread, so they don't need Platform.runLater
    private void recalculateFontSizes() {
        this.baseFontSize = this.canvas.getHeight() / 10;
        this.headerFontSize = this.baseFontSize;
        this.listFontSize = this.baseFontSize*2/3;
        this.descriptionFontSize = this.baseFontSize/2;
        this.hintFontSize = this.baseFontSize/3;
        this.padding = this.baseFontSize*0.1;
    }

    private void drawLogo(GraphicsContext context) {
        Image image = TypeImag_MENU.ICON.getImage();
        context.drawImage(image, 0, 0);
    }

    private void drawHeader(GraphicsContext context,MenuElement element){
        context.setFont(Font.font(this.headerFontSize));
        context.setTextBaseline(VPos.TOP);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFill(Color.WHITESMOKE);
        context.fillText(element.getName(), this.canvas.getWidth()/2, 0);
    }

    private void drawList(GraphicsContext context, MenuListElement listElement){
        // draw the list elements below (only the name, not everything else)
        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(Font.font(listFontSize));
        double startY = this.headerFontSize + this.padding;
        double y = startY;
        if(listElement instanceof MenuRootElement){
            startY = this.canvas.getHeight() - listElement.getElements().size() * this.listFontSize - this.hintFontSize - this.padding * 2;
            y = startY;
        }
        for (MenuElement child : listElement.getElements()) {
            context.fillText(child.toString(), this.listFontSize + this.padding , y);
            y += this.listFontSize;
        }
        context.fillText(">", this.padding, startY + listElement.getIndex() * this.listFontSize);
    }

    private void drawChoice(GraphicsContext context, MenuChoiceElement choiceElement){
        //draw choices like as if they were in a list like MenuListElement
        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(Font.font(this.listFontSize));
        double y = this.headerFontSize + this.padding;
        for(Object choice : choiceElement.getChoices()){
            context.fillText(choice.toString(), this.listFontSize + this.padding, y);
            y += this.listFontSize;
        }
        context.fillText(">", this.padding, this.headerFontSize + choiceElement.getIndex()*this.listFontSize);
    }

    private void drawText(GraphicsContext context, MenuTextElement textElement){
        //draw additional description at the bottom
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.TOP);
        context.setFont(Font.font(descriptionFontSize));
        context.fillText(textElement.getDescription(), this.canvas.getWidth()/2, this.headerFontSize + this.padding);
    }

    private void drawHint(GraphicsContext context){
        //draw tooltip at the bottom
        context.setFont(Font.font(this.hintFontSize));
        context.setTextBaseline(VPos.BOTTOM);
        context.setTextAlign(TextAlignment.CENTER);
        context.fillText("Enter: Confirm | Up/Down: Move cursor | Esc/Backspace: Go back", this.canvas.getWidth()/2, this.canvas.getHeight());
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
