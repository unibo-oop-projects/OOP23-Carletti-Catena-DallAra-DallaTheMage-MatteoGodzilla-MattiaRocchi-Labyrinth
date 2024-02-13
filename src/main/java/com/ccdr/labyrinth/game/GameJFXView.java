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
import javafx.scene.text.Font;

//This class is invoked from the controller thread, so *every* draw call MUST be wrapped into
//a `Platform.runLater` call.
public final class GameJFXView implements GameView, JFXInputSource {

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
    private double tileHeight;

    //Images
    private static final Image WALL = TypeImag.WALL.getImage();
    private static final Image PATH_CENTER = TypeImag.PATH.getImage();
    private static final Image PATH_VERTICAL = TypeImag.PATH.getImage();
    private static final Image PATH_HORIZONTAL = TypeImag.PATH.getImage();
    private static final Image PATH_GUILD = TypeImag.GUILD.getImage();

    private Scene scene;
    private ExpandCanvas canvas;
    private int i = 3;

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
            //context2d.setFill(Color.BLUE);
            //context2d.fillRect(labyrinthRegionX, 0, labyrinthRegionWidth, this.canvas.getHeight());

            this.tileWidth = labyrinthSize / board.getWidth();
            this.tileHeight = labyrinthSize / board.getHeight();
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
                final double y = labyrinthTopLeftY + c.row() * tileHeight;

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
            var context2d = this.canvas.getGraphicsContext2D();
            final double tileMiddleSize = this.tileWidth * TILE_MIDDLE_WIDTH;
            final double border = (tileWidth - tileMiddleSize) / 2;

            for (int i = 0; i < players.size(); i++) {
                if (i == 0) {
                    //Player1
                    final double playerY = (players.get(i).getCoord().row() * this.tileHeight) +
                    this.labyrinthTopLeftY;
                    final double playerX = (players.get(i).getCoord().column() * this.tileWidth) +
                    this.labyrinthTopLeftX;
                    context2d.setFill(Color.RED);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                }
                else if (i == 1) {
                    //Player2
                    final double playerY = (players.get(i).getCoord().row() * this.tileHeight) +
                    this.labyrinthTopLeftY;
                    final double playerX = (players.get(i).getCoord().column() * this.tileWidth) +
                    this.labyrinthTopLeftX + this.labyrinthSize - this.tileWidth;
                    context2d.setFill(Color.BLUE);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                }
                else if (i == 2) {
                    //Player3
                    final double playerY = (players.get(i).getCoord().row() * this.tileHeight) +
                    this.labyrinthTopLeftY + this.labyrinthSize - this.tileHeight;
                    final double playerX = (players.get(i).getCoord().column() * this.tileWidth) +
                    this.labyrinthTopLeftX;
                    context2d.setFill(Color.GREEN);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                }
                else if (i == 3) {
                    //Player4
                    final double playerY = (players.get(i).getCoord().row() * this.tileHeight) +
                    this.labyrinthTopLeftY + this.labyrinthSize - this.tileHeight;
                    final double playerX = (players.get(i).getCoord().column() * this.tileWidth) +
                    this.labyrinthTopLeftX + this.labyrinthSize - this.tileWidth;
                    context2d.setFill(Color.YELLOW);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                }
            }
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
        Image Clothing = TypeImag.CLOTHING.getImage();
        Image Jewel = TypeImag.JEWEL.getImage();
        Image Weapon = TypeImag.WEAPON.getImage();
        Image Tool = TypeImag.TOOL.getImage();
        Platform.runLater(()->{
            var context2d = this.canvas.getGraphicsContext2D();
            context2d.setFill(Color.BLACK);
            context2d.setTextBaseline(VPos.TOP);
            context2d.setFont(Font.font(25));
            context2d.fillText("Missions", 15, 0);
            context2d.setFont(Font.getDefault());
            for (Item item : missions) {
                context2d.fillText(""+ item.getCategory()  + " " +"\t   " + item.getMaterial() + "\t " + item.getQuantity(), 0,i * 10);
                i++;
            }
            i=3;

            context2d.setFont(Font.font(25));
            context2d.fillText(" LEGEND ", (labyrinthRegionX / 2) - (labyrinthRegionX / 5), this.canvas.getHeight() - 170, 200);
            context2d.setFont(Font.getDefault());
            context2d.drawImage(Armor,10, this.canvas.getHeight() -130, 25 , 25);
            context2d.fillText(" -> Type Armor", 36 ,this.canvas.getHeight() -126);
            context2d.drawImage(Clothing,7, this.canvas.getHeight() -108, 30 , 30);
            context2d.fillText(" -> Type Clothing", 36 ,this.canvas.getHeight() -103);
            context2d.drawImage(Weapon,10, this.canvas.getHeight() -82, 25 , 25);
            context2d.fillText(" -> Type Weapon", 36 ,this.canvas.getHeight() -80);
            context2d.drawImage(Tool,10, this.canvas.getHeight() -56, 19 , 19);
            context2d.fillText(" -> Type Tool", 36 ,this.canvas.getHeight() -57);
            context2d.drawImage(Jewel,10, this.canvas.getHeight() -34, 20 , 20);
            context2d.fillText(" -> Type Jewel", 36 ,this.canvas.getHeight() -34);
        });
    }

    private void recalculateLayout(){
        final double canvasWidth = this.canvas.getWidth();
        this.objectiveRegionWidth = canvasWidth * OBJECTIVE_REGION_WIDTH;
        this.labyrinthRegionWidth = canvasWidth * LABYRINTH_REGION_WIDTH;
        this.playerStatsRegionWidth = canvasWidth * PLAYER_STATS_REGION_WIDTH;
        this.objectiveRegionX = 0;
        this.labyrinthRegionX = this.objectiveRegionWidth;
        this.playerStatsRegionX = this.objectiveRegionWidth + this.labyrinthRegionWidth;

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
