package com.ccdr.labyrinth.game;

import java.util.List;

import com.ccdr.labyrinth.TypeImag;
import com.ccdr.labyrinth.game.loader.Coordinate;
import com.ccdr.labyrinth.game.loader.Direction;
import com.ccdr.labyrinth.game.loader.Item;
import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.jfx.ExpandCanvas;
import com.ccdr.labyrinth.jfx.JFXInputSource;
import com.ccdr.labyrinth.jfx.JFXStage;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public final class GameJFXView implements GameView, JFXInputSource {

    private static final int PIXEL_FOR_PLAYER = 33 - 15;

    //Reference constants that are used to set the layout of the game
    private static final double OBJECTIVE_REGION_WIDTH = 1.0/6;
    private static final double LABYRINTH_REGION_WIDTH = 4.0/6;
    private static final double PLAYER_STATS_REGION_WIDTH = 1.0/6;

    //Runtime calculated values with dimentions in pixels
    private double objectiveRegionWidth;
    private double labyrinthRegionWidth;
    private double playerStatsRegionWidth;
    private double objectiveRegionX;
    private double labyrinthRegionX;
    private double playerStatsRegionX;

    //labyrinth specific values
    private static final double TILE_MIDDLE_WIDTH = 2.0/3;
    private double labyrinthTopLeftX;
    private double labyrinthTopLeftY;
    private double labyrinthSize; //assumed to be a square
    private double tileWidth;
    private double tileheight;

    //Images
    private static final Image WALL = TypeImag.WALL.getImage();
    private static final Image PATH_CENTER = TypeImag.PATH.getImage();
    private static final Image PATH_VERTICAL = TypeImag.PATH.getImage();
    private static final Image PATH_HORIZONTAL = TypeImag.PATH.getImage();
    private static final Image PATH_GUILD = TypeImag.GUILD.getImage();

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
            //eh, this is not the best place to put it
            recalculateLayout();
        });
    }

    @Override
    public void drawBoard(Board board) {
        Platform.runLater(()->{
            var context2d = this.canvas.getGraphicsContext2D();
            //DEBUG
            context2d.setFill(Color.BLUE);
            context2d.fillRect(labyrinthRegionX, 0, labyrinthRegionWidth, this.canvas.getHeight());

            this.tileWidth = labyrinthSize / board.getWidth();
            this.tileheight = labyrinthSize / board.getHeight();
            //reference points in the tile
            final double tileMiddleSize = this.tileWidth * TILE_MIDDLE_WIDTH;
            final double border = (tileWidth - tileMiddleSize) / 2;
            final double rightSplit = border + tileMiddleSize;
            final double bottomSplit = border + tileMiddleSize;

            context2d.setFill(Color.GRAY);
            for(var entry : board.getMap().entrySet()){
                final Coordinate c = entry.getKey();
                final Tile tile = entry.getValue();
                final double x = labyrinthTopLeftX + c.column() * tileWidth;
                final double y = labyrinthTopLeftY + c.row() * tileheight;

                //corners are always a wall
                //top left
                context2d.drawImage(WALL, x, y, border, border);
                //top right
                context2d.drawImage(WALL, x + rightSplit, y, border, border);
                //bottom left
                context2d.drawImage(WALL, x, y + bottomSplit, border, border);
                //bottom right
                context2d.drawImage(WALL, x + rightSplit, y + bottomSplit, border, border);
                //center is always walkable
                context2d.drawImage(PATH_CENTER, x + border, y + border, tileMiddleSize, tileMiddleSize);

                //vertical paths
                if(tile.isOpen(Direction.UP)){
                    context2d.drawImage(PATH_VERTICAL, x + border, y, tileMiddleSize, border);
                } else {
                    context2d.drawImage(WALL, x + border, y, tileMiddleSize, border);
                }
                if(tile.isOpen(Direction.DOWN)){
                    context2d.drawImage(PATH_VERTICAL, x + border, y + bottomSplit, tileMiddleSize, border);
                } else {
                    context2d.drawImage(WALL, x + border, y + bottomSplit, tileMiddleSize, border);
                }

                //horizontal paths
                if(tile.isOpen(Direction.LEFT)){
                    context2d.drawImage(PATH_HORIZONTAL, x, y + border, border, tileMiddleSize);
                } else {
                    context2d.drawImage(WALL, x, y + border, border, tileMiddleSize);
                }
                if(tile.isOpen(Direction.RIGHT)){
                    context2d.drawImage(PATH_HORIZONTAL, x + rightSplit, y + border, border, tileMiddleSize);
                } else {
                    context2d.drawImage(WALL, x + rightSplit, y + border, border, tileMiddleSize);
                }

                decorateTile(context2d, tile, x, y);
            }
        });
    }

    //Used for those tiles that require additional graphics on top of the standard path rendering
    private void decorateTile(GraphicsContext context2d, Tile tile, double x, double y) {
        if(tile instanceof SourceTile){
            context2d.setStroke(Color.GREENYELLOW);
            final double tileMiddleSize = this.tileWidth * TILE_MIDDLE_WIDTH;
            final double border = (tileWidth - tileMiddleSize) / 2;
            context2d.strokeOval(x + border, y + border, tileMiddleSize, tileMiddleSize);
        }
    }

    @Override
    public void drawPlayersOnBoard(List<Player> players) {
        Platform.runLater(() -> {
            //TODO: insert code to draw players on board
            var context2d = this.canvas.getGraphicsContext2D();
            final double regionWidth = this.canvas.getWidth() * 2 / 3;
            final double regionXStart = this.canvas.getWidth() / 6;

            double playerY;
            double playerX;

            //Player1
            playerY = (players.get(0).getCoord().row() * 33) + regionXStart + 102;
            playerX = players.get(0).getCoord().column() * 33;
            context2d.setFill(Color.RED);
            context2d.fillOval(playerY, playerX, PIXEL_FOR_PLAYER, PIXEL_FOR_PLAYER);

            //Player2
            playerY = (players.get(1).getCoord().row() * 33) + regionWidth + 93;
            playerX = players.get(1).getCoord().column() * 33;
            context2d.setFill(Color.BLUE);
            context2d.fillOval(playerY, playerX, PIXEL_FOR_PLAYER, PIXEL_FOR_PLAYER);

            //Player3
            playerY = (players.get(2).getCoord().row() * 33) + regionWidth + 93;
            playerX = (players.get(2).getCoord().column() * 33) + regionXStart + 418;
            context2d.setFill(Color.GREEN);
            context2d.fillOval(playerY, playerX, PIXEL_FOR_PLAYER, PIXEL_FOR_PLAYER);

            //Player4
            playerY = (players.get(3).getCoord().row() * 33) + regionXStart + 102;
            playerX = (players.get(3).getCoord().column() * 33) + regionXStart + 418;
            context2d.setFill(Color.YELLOW);
            context2d.fillOval(playerY, playerX, PIXEL_FOR_PLAYER, PIXEL_FOR_PLAYER);
        });
    }

    @Override
    public void drawPlayersStats(List<Player> players) {
        Platform.runLater(() -> {
            //TODO: insert code to draw players statistics
        });
    }

    public void drawMissions(List<Item> missions){
        Image Armor = TypeImag.ARMOR.getImage();
        Platform.runLater(()->{
            var context2d = this.canvas.getGraphicsContext2D();
            context2d.setFill(Color.BLACK);
            context2d.setTextBaseline(VPos.TOP);
            context2d.fillText("Missions", 15, 0);
            for (Item item : missions) {
                context2d.fillText(""+ item.getCategory() + "\t" + item.getMaterial() + "\t" + item.getQuantity(), 0, i * 10);
                context2d.drawImage(Armor, 0, i * 10, 25 , 25);
                i++;
            }
            i=1;
        });
    }

    private void recalculateLayout(){
        final double canvasWidth = this.canvas.getWidth();
        this.objectiveRegionWidth = canvasWidth * OBJECTIVE_REGION_WIDTH;
        this.labyrinthRegionWidth = canvasWidth * LABYRINTH_REGION_WIDTH;
        this.playerStatsRegionWidth = canvasWidth * PLAYER_STATS_REGION_WIDTH;
        this.objectiveRegionX = 0;
        this.labyrinthRegionX = this.objectiveRegionWidth;
        this.playerStatsRegionX = this.objectiveRegionWidth + this.playerStatsRegionWidth;

        //the available region of space is labyrinthRegionWidth*canvas.getHeight
        //the labyrinth must fit inside the allocated region
        this.labyrinthSize = Math.min(labyrinthRegionWidth, this.canvas.getHeight());
        this.labyrinthTopLeftX = (labyrinthRegionWidth - labyrinthSize) / 2 + labyrinthRegionX;
        this.labyrinthTopLeftY = (this.canvas.getHeight() - labyrinthSize) / 2;
    }

    @Override
    public void routeKeyboardEvents(Receiver adapter){
        this.scene.setOnKeyPressed(adapter::onKeyPressed);
    }

}
