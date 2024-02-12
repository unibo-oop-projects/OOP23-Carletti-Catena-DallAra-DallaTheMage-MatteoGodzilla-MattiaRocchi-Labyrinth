package com.ccdr.labyrinth.game;

import java.util.List;

import com.ccdr.labyrinth.TypeImag;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Direction;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.loader.tiles.Tile;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.jfx.ExpandCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public class GameJFXView implements GameView, JFXInputSource {
    private Scene scene;
    private ExpandCanvas canvas;
    private int i = 1;

    public GameJFXView(){
        this.canvas = new ExpandCanvas();
        this.scene = new Scene(new Group(this.canvas), Color.GREEN);
        this.canvas.bind(this.scene);
    }

    @Override
    public void onEnable() {
        Platform.runLater(()->{
            JFXStage.getStage().setScene(this.scene);
            //Force resize of canvas so it fills the entire stage
            JFXStage.getStage().setWidth(JFXStage.getStage().getWidth());
            JFXStage.getStage().setHeight(JFXStage.getStage().getHeight());
        });
    }

    @Override
    public void clear() {
        Platform.runLater(()->{
            var context2d = this.canvas.getGraphicsContext2D();
            //Maybe change this to fillRect(black);
            context2d.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        });
    }

    @Override
    public void drawBoard(Board board) {
        Platform.runLater(()->{
            var context2d = this.canvas.getGraphicsContext2D();
            //TODO: change this so it doesn't conflict with the other stuff to draw
            final double regionWidth = this.canvas.getWidth() * 2 / 3;
            final double regionXStart = this.canvas.getWidth() / 6;
            //DEBUG
            context2d.setFill(Color.BLUE);
            context2d.fillRect(regionXStart, 0, regionWidth, this.canvas.getHeight());
            //the available region of space is regionWidth*canvas.getHeight
            //the labyrinth must fit inside this space
            final double labyrinthSize = Math.min(regionWidth, this.canvas.getHeight());
            final double topLeftX = (regionWidth - labyrinthSize) / 2 + regionXStart;
            final double topLeftY = (this.canvas.getHeight() - labyrinthSize) / 2;

            final double tileWidth = labyrinthSize / board.getWidth();
            final double tileheight = labyrinthSize / board.getHeight();
            final double sliceWidth = tileWidth/3;
            final double sliceHeight = tileheight/3;
            context2d.setFill(Color.GRAY);
            Image wall = TypeImag.WALL.getImage();
            Image pathCenter = TypeImag.PATH_CENTRAL.getImage();
            Image pathVertical = TypeImag.PATH_VERTICAL.getImage();
            Image pathHorizontal = TypeImag.PATH_HORIZONTAL.getImage();
            for(var entry : board.getMap().entrySet()){
                final Coordinate c = entry.getKey();
                final Tile tile = entry.getValue();
                final double x = topLeftX + c.column() * tileWidth;
                final double y = topLeftY + c.row() * tileheight;

                //corners are always a wall
                //top left
                context2d.drawImage(wall, x, y, sliceWidth, sliceHeight);
                //top right
                context2d.drawImage(wall, x + sliceWidth * 2, y, sliceWidth, sliceHeight);
                //bottom left
                context2d.drawImage(wall, x, y + sliceHeight * 2, sliceWidth, sliceHeight);
                //bottom left
                context2d.drawImage(wall, x + sliceWidth * 2, y + sliceHeight * 2, sliceWidth, sliceHeight);
                //center is always walkable
                context2d.drawImage(pathCenter, x + sliceWidth, y + sliceHeight, sliceWidth, sliceHeight);

                //vertical paths
                //top
                if(tile.isOpen(Direction.UP)){
                    context2d.drawImage(pathVertical, x + sliceWidth, y, sliceWidth, sliceHeight);
                } else {
                    context2d.drawImage(wall, x + sliceWidth, y, sliceWidth, sliceHeight);
                }
                if(tile.isOpen(Direction.DOWN)){
                    context2d.drawImage(pathVertical, x + sliceWidth, y + sliceHeight * 2, sliceWidth, sliceHeight);
                } else {
                    context2d.drawImage(wall, x + sliceWidth, y + sliceHeight * 2, sliceWidth, sliceHeight);
                }

                //horizontal paths
                if(tile.isOpen(Direction.LEFT)){
                    context2d.drawImage(pathHorizontal, x, y + sliceHeight, sliceWidth, sliceHeight);
                } else {
                    context2d.drawImage(wall, x, y + sliceHeight, sliceWidth, sliceHeight);
                }
                if(tile.isOpen(Direction.DOWN)){
                    context2d.drawImage(pathHorizontal, x + sliceWidth * 2, y + sliceHeight, sliceWidth, sliceHeight);
                } else {
                    context2d.drawImage(wall, x + sliceWidth * 2, y + sliceHeight, sliceWidth, sliceHeight);
                }
            }
        });
    }

    @Override
    public void drawPlayersOnBoard(List<Player> players) {
        Platform.runLater(() -> {
            //TODO: insert code to draw players on board
        });
    }

    @Override
    public void drawPlayersStats(List<Player> players) {
        Platform.runLater(() -> {
            //TODO: insert code to draw players statistics
        });
    }

    public void drawMissions(List<Item> missions){
        Platform.runLater(()->{
            var context2d = this.canvas.getGraphicsContext2D();
            context2d.setFill(Color.BLACK);
            context2d.setTextBaseline(VPos.TOP);
            context2d.fillText("Missions", 15, 0);
            for (Item item : missions) {
                context2d.fillText(""+ item.getCategory() + "\t" + item.getMaterial() + "\t" + item.getQuantity(), 0, i * 10);
                i++;
            }
            i=1;
        });
    }

    //Left empty, for now
    @Override
    public void onDisable() {}

    @Override
    public void routeKeyboardEvents(Receiver adapter){
        this.scene.setOnKeyPressed(adapter::onKeyPressed);
    }

}
