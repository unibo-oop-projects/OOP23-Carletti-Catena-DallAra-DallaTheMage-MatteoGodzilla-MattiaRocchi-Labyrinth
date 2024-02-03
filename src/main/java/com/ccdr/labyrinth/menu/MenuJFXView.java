package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.TypeImagMENU;
import com.ccdr.labyrinth.jfx.AspectRatioCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;
import com.ccdr.labyrinth.menu.tree.MenuChoiceElement;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;
import com.ccdr.labyrinth.menu.tree.MenuRootElement;
import com.ccdr.labyrinth.menu.tree.MenuTextElement;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.Animation.Status;
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
import javafx.util.Duration;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public class MenuJFXView implements MenuView, JFXInputSource {
    private Scene scene;
    private AspectRatioCanvas canvas;
    private Animation indexArrow;
    // variables used for resizing text elements
    private double baseFontSize;
    private double listFontSize;
    private double descriptionFontSize;
    private double hintFontSize;
    private double headerFontSize;
    private double logoSize;
    private double padding;
    // variable used for animation
    private double startIndex = 0;
    private double endIndex = 0;
    private double interpolatedIndex;

    public MenuJFXView() {
        this.canvas = new AspectRatioCanvas(JFXStage.WINDOW_WIDTH, JFXStage.WINDOW_HEIGHT);
        var layout = new HBox(this.canvas);
        layout.setAlignment(Pos.CENTER);
        this.scene = new Scene(layout, Color.BLACK);
    }

    @Override
    public void onEnable() {
        Platform.runLater(() -> {
            JFXStage.getStage().setScene(this.scene);
            // index indicator transition
            this.indexArrow = new Transition() {
                {
                    setCycleDuration(Duration.seconds(0.1));
                    setCycleCount(1);
                    setInterpolator(Interpolator.EASE_BOTH);
                }

                @Override
                protected void interpolate(double frac) {
                    interpolatedIndex = frac * (endIndex - startIndex) + startIndex;
                }
            };

            this.indexArrow.play();
        });
    }

    @Override
    public void draw(MenuElement element) {
        Platform.runLater(() -> {
            GraphicsContext context = this.canvas.getGraphicsContext2D();
            recalculateFontSizes();
            context.setFill(Color.gray(0.1));
            context.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            drawHeader(context, element);
            if (element instanceof MenuListElement) {
                if (element instanceof MenuRootElement) {
                    drawLogo(context);
                }
                drawList(context, (MenuListElement) element);
            } else if (element instanceof MenuTextElement) {
                drawText(context, (MenuTextElement) element);
            } else if (element instanceof MenuChoiceElement) {
                drawChoice(context, (MenuChoiceElement<?>) element);
            }
            drawHint(context);
        });
    }

    @Override
    public void changed(MenuElement element) {
        if (element instanceof MenuListElement || element instanceof MenuChoiceElement) {
            this.startIndex = this.endIndex;
            if (element instanceof MenuListElement) {
                this.endIndex = ((MenuListElement) element).getIndex();
            } else if (element instanceof MenuChoiceElement) {
                this.endIndex = ((MenuChoiceElement<?>) element).getIndex();
            }
            this.indexArrow.playFromStart();
        }
    }

    // all these functions below are called from the JFX thread, so they don't need
    // Platform.runLater
    private void recalculateFontSizes() {
        this.baseFontSize = this.canvas.getHeight() / 10;
        this.logoSize = this.baseFontSize * 3 / 2;
        this.headerFontSize = this.baseFontSize;
        this.listFontSize = this.baseFontSize * 2 / 3;
        this.descriptionFontSize = this.baseFontSize / 2;
        this.hintFontSize = this.baseFontSize / 3;
        this.padding = this.baseFontSize * 0.1;
    }

    private void drawLogo(GraphicsContext context) {
        Image image = TypeImagMENU.LOGO.getImage();
        double logoWidth = this.logoSize * image.getWidth() / image.getHeight();
        double xPos = this.canvas.getWidth() / 2 - logoWidth / 2;
        xPos -= logoWidth * 0.1;
        context.drawImage(image, xPos, this.padding, logoWidth, this.logoSize);
    }

    private void drawHeader(GraphicsContext context, MenuElement element) {
        context.setFont(Font.font(this.headerFontSize));
        context.setTextBaseline(VPos.TOP);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFill(Color.valueOf("#bbbbbb"));
        context.fillText(element.getName(), this.canvas.getWidth() / 2, 0);
    }

    private void drawList(GraphicsContext context, MenuListElement listElement) {
        // draw the list elements below (only the name, not everything else)
        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(Font.font(listFontSize));
        context.setFill(Color.valueOf("#bbbbbb"));
        double startY = this.headerFontSize + this.padding;
        double y = startY;
        if (listElement instanceof MenuRootElement) {
            startY = this.canvas.getHeight() - listElement.getElements().size() * this.listFontSize - this.hintFontSize
                    - this.padding * 2;
            y = startY;
        }
        for (MenuElement child : listElement.getElements()) {
            context.fillText(child.toString(), this.listFontSize + this.padding, y);
            y += this.listFontSize;
        }
        context.fillText(">", this.padding, startY + interpolatedIndex * this.listFontSize);
    }

    private void drawChoice(GraphicsContext context, MenuChoiceElement<?> choiceElement) {
        // draw choices like as if they were in a list like MenuListElement
        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(Font.font(this.listFontSize));
        context.setFill(Color.valueOf("#bbbbbb"));
        double y = this.headerFontSize + this.padding;
        for (Object choice : choiceElement.getChoices()) {
            context.fillText(choice.toString(), this.listFontSize + this.padding, y);
            y += this.listFontSize;
        }
        context.fillText(">", this.padding, this.headerFontSize + this.padding + interpolatedIndex * this.listFontSize);
    }

    private void drawText(GraphicsContext context, MenuTextElement textElement) {
        // draw additional description at the bottom
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.TOP);
        context.setFont(Font.font(descriptionFontSize));
        context.setFill(Color.valueOf("#bbbbbb"));
        context.fillText(textElement.getDescription(), this.canvas.getWidth() / 2, this.headerFontSize + this.padding);
    }

    private void drawHint(GraphicsContext context) {
        // draw tooltip at the bottom
        context.setFont(Font.font(this.hintFontSize));
        context.setTextBaseline(VPos.BOTTOM);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFill(Color.valueOf("#bbbbbb"));
        context.fillText("Enter: Confirm | Up/Down: Move cursor | Esc/Backspace: Go back", this.canvas.getWidth() / 2,
                this.canvas.getHeight());
    }

    // this is left empty, for now
    @Override
    public void onDisable() {
    }

    @Override
    public void routeKeyboardEvents(Receiver adapter) {
        this.scene.setOnKeyPressed(adapter::onKeyPressed);
        this.scene.setOnKeyReleased(adapter::onKeyReleased);
    }

}
