package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.TypeImag;
import com.ccdr.labyrinth.jfx.ExpandCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;
import com.ccdr.labyrinth.menu.tree.MenuChoiceElement;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;
import com.ccdr.labyrinth.menu.tree.MenuTextElement;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Main implementation of the MenuView interface, done using JavaFX.
 */
public final class MenuJFXView implements MenuView, JFXInputSource {
    private final Scene scene;
    private final ExpandCanvas canvas;
    private Animation indexArrow;
    // variables used for resizing text elements
    private double listFontSize;
    private double descriptionFontSize;
    private double hintFontSize;
    private double headerFontSize;
    private double logoSize;
    private double padding;
    // variable used for animation
    private static final double ANIM_DURATION = 0.1;
    private double startIndex;
    private double endIndex;
    private double interpolatedIndex;
    // variables used for general rendering
    private static final Color MENU_BASE_COLOR = Color.gray(0.1);
    private static final Color TEXT_FILL = Color.valueOf("#bbbbbb");

    /**
     *
     */
    public MenuJFXView() {
        this.canvas = new ExpandCanvas();
        this.scene = new Scene(new Group(this.canvas), MENU_BASE_COLOR);
        this.canvas.bind(this.scene);
    }

    @Override
    public void onEnable() {
        Platform.runLater(() -> {
            JFXStage.getStage().setScene(this.scene);
            // index indicator transition
            this.indexArrow = new Transition() {
                {
                    setCycleDuration(Duration.seconds(ANIM_DURATION));
                    setCycleCount(1);
                    setInterpolator(Interpolator.EASE_BOTH);
                }

                @Override
                protected void interpolate(final double frac) {
                    interpolatedIndex = frac * (endIndex - startIndex) + startIndex;
                }
            };

            this.indexArrow.play();
        });
    }

    @Override
    public void draw(final MenuElement element) {
        Platform.runLater(() -> {
            final GraphicsContext context = this.canvas.getGraphicsContext2D();
            recalculateFontSizes();
            context.setFill(MENU_BASE_COLOR);
            context.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            if (element.getParent() != null) {
                drawHeader(context, element);
            }
            if (element instanceof MenuListElement) {
                if (element.getParent() == null) {
                    drawLogo(context);
                    //offset the main menu list so it's below in the screen
                    final MenuListElement listElement = (MenuListElement) element;
                    final double sizeForElements = listElement.getElements().size() * this.listFontSize;
                    final double startY = this.canvas.getHeight() - sizeForElements - this.hintFontSize - this.padding;
                    drawList(context, (MenuListElement) element, startY);
                } else {
                    drawList(context, (MenuListElement) element, this.headerFontSize + this.padding);
                }
            } else if (element instanceof MenuTextElement) {
                drawText(context, (MenuTextElement) element);
            } else if (element instanceof MenuChoiceElement) {
                drawChoice(context, (MenuChoiceElement<?>) element);
            }
            drawHint(context);
        });
    }

    @Override
    public void changed(final MenuElement element) {
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

    // this is left empty, for now
    @Override
    public void onDisable() { }

    @Override
    public void routeKeyboardEvents(final Receiver adapter) {
        this.scene.setOnKeyPressed(adapter::onKeyPressed);
        this.scene.setOnKeyReleased(adapter::onKeyReleased);
    }

    // all these functions below are called from the JFX thread, so they don't need Platform.runLater
    private void recalculateFontSizes() {
        //aspect ratio of the menu is in 4/3
        final double referenceHeight = Math.min(this.canvas.getHeight(), this.canvas.getWidth() * 3 / 4);
        final double baseFontSize = referenceHeight / 10;
        this.logoSize = baseFontSize * 2;
        this.headerFontSize = baseFontSize;
        this.listFontSize = baseFontSize * 2 / 3;
        this.descriptionFontSize = baseFontSize / 2;
        this.hintFontSize = baseFontSize / 3;
        this.padding = baseFontSize / 10;
    }

    private void drawLogo(final GraphicsContext context) {
        final Image image = TypeImag.LOGO.getImage();
        final double logoWidth = this.logoSize * image.getWidth() / image.getHeight();
        final double xPos = this.canvas.getWidth() / 2 - logoWidth / 2;
        context.drawImage(image, xPos, this.padding, logoWidth, this.logoSize);
    }

    private void drawHeader(final GraphicsContext context, final MenuElement element) {
        context.setFont(Font.font(this.headerFontSize));
        context.setTextBaseline(VPos.TOP);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFill(TEXT_FILL);
        context.fillText(element.getName(), this.canvas.getWidth() / 2, 0);
    }

    private void drawList(final GraphicsContext context, final MenuListElement listElement, final double startY) {
        // draw the list elements below (only the name, not everything else)
        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(Font.font(listFontSize));
        context.setTextBaseline(VPos.TOP);
        context.setFill(TEXT_FILL);
        double y = startY;
        for (final MenuElement child : listElement.getElements()) {
            context.fillText(child.toString(), this.listFontSize + this.padding, y);
            y += this.listFontSize;
        }
        context.fillText(">", this.padding, startY + interpolatedIndex * this.listFontSize);
    }

    private void drawChoice(final GraphicsContext context, final MenuChoiceElement<?> choiceElement) {
        // draw choices like as if they were in a list like MenuListElement
        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(Font.font(this.listFontSize));
        context.setFill(TEXT_FILL);
        double y = this.headerFontSize + this.padding;
        for (final Object choice : choiceElement.getChoices()) {
            context.fillText(choice.toString(), this.listFontSize + this.padding, y);
            y += this.listFontSize;
        }
        context.fillText(">", this.padding, this.headerFontSize + this.padding + interpolatedIndex * this.listFontSize);
    }

    private void drawText(final GraphicsContext context, final MenuTextElement textElement) {
        // draw additional description at the bottom
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.TOP);
        context.setFont(Font.font(descriptionFontSize));
        context.setFill(TEXT_FILL);
        context.fillText(textElement.getDescription(), this.canvas.getWidth() / 2, this.headerFontSize + this.padding);
    }

    private void drawHint(final GraphicsContext context) {
        // draw tooltip at the bottom
        context.setFont(Font.font(this.hintFontSize));
        context.setTextBaseline(VPos.BOTTOM);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFill(TEXT_FILL);
        context.fillText("Enter: Confirm | Up/Down: Move cursor | Esc/Backspace: Go back",
            this.canvas.getWidth() / 2, this.canvas.getHeight());
    }

}
