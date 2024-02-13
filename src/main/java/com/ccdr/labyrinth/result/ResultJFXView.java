package com.ccdr.labyrinth.result;

import com.ccdr.labyrinth.jfx.ExpandCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.Material;

import java.util.List;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Main implementation of ResultView, done with JavaFX.
 */
public final class ResultJFXView implements ResultView, JFXInputSource {
    //same ones as the menu
    private static final Color BASE_COLOR = Color.gray(0.1);
    private static final Color TEXT_FILL = Color.valueOf("#bbbbbb");

    private final Scene scene;
    private final ExpandCanvas canvas;
    private double headerFontSize;
    private double padding;
    private double hintFontSize;
    private double playerSize;

    /**
     *
     */
    public ResultJFXView() {
        this.canvas = new ExpandCanvas();
        this.scene = new Scene(new Group(this.canvas), BASE_COLOR);
        this.canvas.bind(this.scene);
    }

    @Override
    public void onEnable() {
        Platform.runLater(() -> {
            JFXStage.getStage().setScene(this.scene);
            //Force resize of canvas so it fills the entire stage
            JFXStage.getStage().setWidth(JFXStage.getStage().getWidth());
            JFXStage.getStage().setHeight(JFXStage.getStage().getHeight());
        });
    }

    @Override
    public void draw(final List<Player> players) {
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
            context.fillText("Results", this.canvas.getWidth() / 2, this.padding);

            if (players != null && players.size() > 0) {
                drawScores(context, players);
            }

            //draw hint at the bottom
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.BOTTOM);
            context.setFill(TEXT_FILL);
            context.setFont(Font.font(this.hintFontSize));
            context.fillText("Press Enter/Space to close", this.canvas.getWidth() / 2, this.canvas.getHeight() - this.padding);
        });
    }

    private void drawScores(final GraphicsContext context, final List<Player> players) {
        context.setFill(TEXT_FILL);

        for (int i = 0; i < players.size(); i++) {
            final double y = this.headerFontSize + this.padding * 2 + (this.playerSize + this.hintFontSize + this.padding) * i;
            final String entry = new StringBuilder()
                .append("Player ")
                .append(String.valueOf(i + 1))
                .append(": ")
                .append(players.get(i).getPoints())
                .append(" points")
                .toString();
            context.setFont(Font.font(playerSize));
            context.fillText(entry, this.canvas.getWidth() / 2, y);

            var entry2 = new StringBuilder().append('(');
            Material[] allMaterials = Material.values();
            for (int j = 0; j < allMaterials.length; j++) {
                entry2.append(allMaterials[j])
                    .append(": ")
                    .append(players.get(i).getQuantityMaterial(allMaterials[j]));
                if (j + 1 < allMaterials.length) {
                    entry2.append(", ");
                }
            }
            entry2.append(')');
            context.setFont(Font.font(this.hintFontSize));
            context.fillText(entry2.toString(), this.canvas.getWidth() / 2, y + this.playerSize + this.padding);
        }
    }

    private void recalculateFontSizes() {
        final double referenceHeight = Math.min(this.canvas.getHeight(), this.canvas.getWidth() * 3 / 4);
        final double baseFontSize = referenceHeight / 10;
        // this.logoSize = baseFontSize * 2;
        this.headerFontSize = baseFontSize;
        this.playerSize = baseFontSize * 2 / 3;
        // this.descriptionFontSize = baseFontSize / 2;
        this.hintFontSize = baseFontSize / 3;
        this.padding = baseFontSize / 10;
    }

    @Override
    public void routeKeyboardEvents(final Receiver receiver) {
        this.scene.setOnKeyPressed(receiver::onKeyPressed);
    }
}
