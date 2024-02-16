package com.ccdr.labyrinth.game;

import java.util.List;

import com.ccdr.labyrinth.ImageLoader;
import com.ccdr.labyrinth.game.context.Context;
import com.ccdr.labyrinth.game.context.GuildContext;
import com.ccdr.labyrinth.game.context.LabyrinthContext;
import com.ccdr.labyrinth.game.context.PlayersContext;
import com.ccdr.labyrinth.game.context.UpdateBoardContext;
import com.ccdr.labyrinth.game.context.PlayersContext.Subphase;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.tiles.GuildTile;
import com.ccdr.labyrinth.game.tiles.SourceTile;
import com.ccdr.labyrinth.game.tiles.StandardTile;
import com.ccdr.labyrinth.game.tiles.Tile;
import com.ccdr.labyrinth.game.util.Category;
import com.ccdr.labyrinth.game.util.Coordinate;
import com.ccdr.labyrinth.game.util.Direction;
import com.ccdr.labyrinth.game.util.Item;
import com.ccdr.labyrinth.game.util.Material;
import com.ccdr.labyrinth.jfx.JFXExpandCanvas;
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
import javafx.scene.text.TextAlignment;

/**
 * Main implementation of the GameView interface, done using JavaFX.
 */
public final class GameJFXView implements GameView, JFXInputSource {

    //Reference constants that are used to set the layout of the game
    private static final double OBJECTIVE_REGION_WIDTH = 1.0 / 6;
    private static final double LABYRINTH_REGION_WIDTH = 4.0 / 6;
    // private static final double PLAYER_STATS_REGION_WIDTH = 1.0 / 6;

    //Runtime calculated values with dimentions in pixels
    private double objectiveRegionWidth;
    private double labyrinthRegionWidth;
    private double labyrinthRegionX;
    private double playerStatsRegionX;
    private double step;

    //labyrinth specific values
    private static final double TILE_MIDDLE_WIDTH = 2.0 / 3;
    private double labyrinthTopLeftX;
    private double labyrinthTopLeftY;
    private double labyrinthSize; //assumed to be a square
    private double tileWidth;
    private double tileHeight;
    private double imageDim;
    private double lineMissionsX;
    private double lineMissionsY;
    private double spaceMissionsX;
    private double spacePointX;
    private double textLegendX;


    //Images
    private static final Image WALL = ImageLoader.WALL.getImage();
    private static final Image PATH_CENTER = ImageLoader.PATH.getImage();
    private static final Image PATH_VERTICAL = ImageLoader.PATH.getImage();
    private static final Image PATH_HORIZONTAL = ImageLoader.PATH.getImage();
    private static final Image PATH_GUILD = ImageLoader.GUILD.getImage();
    private static final Color BASE_COLOR = Color.gray(0.3);

    private final Scene scene;
    private final JFXExpandCanvas canvas;
    private double i = 10;
    //Variable used for resizing header elements
    private double headerFontSize;
    private double desFontSize;

    /**
     *
     */
    public GameJFXView() {
        this.canvas = new JFXExpandCanvas();
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
    public void clear() {
        Platform.runLater(() -> {
            final GraphicsContext context2d = this.canvas.getGraphicsContext2D();
            //Maybe change this to fillRect(black);
            context2d.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            //eh, this is not the best place to put it
            recalculateLayout();
        });
    }

    @Override
    public void drawBoard(final Board board) {
        Platform.runLater(() -> {
            final GraphicsContext context2d = this.canvas.getGraphicsContext2D();
            context2d.save();
            context2d.setFill(Color.BLACK);
            context2d.fillRect(labyrinthTopLeftX, labyrinthTopLeftY, labyrinthSize, labyrinthSize);

            this.tileWidth = labyrinthSize / board.getWidth();
            this.tileHeight = labyrinthSize / board.getHeight();

            context2d.setFill(Color.GRAY);
            for (final var entry : board.getMap().entrySet()) {
                final Coordinate c = entry.getKey();
                final Tile tile = entry.getValue();
                if (tile.isDiscovered()) {
                    drawTile(context2d, c, tile);
                }
            }
            context2d.restore();
        });
    }

    private void drawTile(final GraphicsContext context2d, final Coordinate c, final Tile tile) {
        //reference points in the tile
        final double tileMiddleSize = this.tileWidth * TILE_MIDDLE_WIDTH;
        final double border = (tileWidth - tileMiddleSize) / 2;
        final double rightSplit = border + tileMiddleSize;
        final double bottomSplit = border + tileMiddleSize;
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
        if (tile.isOpen(Direction.UP)) {
            context2d.drawImage(PATH_VERTICAL, x + border, y, tileMiddleSize, border);
        } else {
            context2d.drawImage(WALL, x + border, y, tileMiddleSize, border);
        }
        if (tile.isOpen(Direction.DOWN)) {
            context2d.drawImage(PATH_VERTICAL, x + border, y + bottomSplit, tileMiddleSize, border);
        } else {
            context2d.drawImage(WALL, x + border, y + bottomSplit, tileMiddleSize, border);
        }

        //horizontal paths
        if (tile.isOpen(Direction.LEFT)) {
            context2d.drawImage(PATH_HORIZONTAL, x, y + border, border, tileMiddleSize);
        } else {
            context2d.drawImage(WALL, x, y + border, border, tileMiddleSize);
        }
        if (tile.isOpen(Direction.RIGHT)) {
            context2d.drawImage(PATH_HORIZONTAL, x + rightSplit, y + border, border, tileMiddleSize);
        } else {
            context2d.drawImage(WALL, x + rightSplit, y + border, border, tileMiddleSize);
        }

        decorateTile(context2d, tile, x, y);
    }

    //Used for those tiles that require additional graphics on top of the standard path rendering
    private void decorateTile(final GraphicsContext context2d, final Tile tile, final double x, final double y) {
        final double tileMiddleSize = this.tileWidth * TILE_MIDDLE_WIDTH;
        final double border = (tileWidth - tileMiddleSize) / 2;
        if (tile instanceof SourceTile) {
            final SourceTile sourceTile = (SourceTile) tile;
            if (sourceTile.isActive()) {
                context2d.setStroke(Color.GREENYELLOW);
            } else {
                context2d.setStroke(Color.RED);
            }
            context2d.strokeOval(x + border, y + border, tileMiddleSize, tileMiddleSize);
            //draw material inside
            final Image material = materialToImage(sourceTile.getMaterialType());
            if (material != null) {
                context2d.drawImage(material, x + border, y + border, tileMiddleSize, tileMiddleSize);
            }
        } else if (tile instanceof GuildTile) {
            //GuildTile guild = (GuildTile) tile;
            context2d.drawImage(PATH_GUILD, x, y, tileWidth, tileHeight);
        } else if (tile instanceof StandardTile) {
            final StandardTile standard = (StandardTile) tile;
            if (standard.getBonusMaterial().isPresent()) {
                final Image material = materialToImage(standard.getBonusMaterial().get());
                if (material != null) {
                    context2d.drawImage(material, x + border, y + border, tileMiddleSize, tileMiddleSize);
                }
            }
        }
    }

    @Override
    public void drawPlayersOnBoard(final List<Player> players) {
        Platform.runLater(() -> {
            final GraphicsContext context2d = this.canvas.getGraphicsContext2D();
            final double tileMiddleSize = this.tileWidth * TILE_MIDDLE_WIDTH;
            final double border = (tileWidth - tileMiddleSize) / 2;
            context2d.save();
            for (int i = 0; i < players.size(); i++) {
                if (i == 0) {
                    //Player1
                    final double playerY = players.get(i).getCoord().row() * this.tileHeight
                    + this.labyrinthTopLeftY;
                    final double playerX = players.get(i).getCoord().column() * this.tileWidth
                    + this.labyrinthTopLeftX;
                    context2d.setFill(Color.RED);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                } else if (i == 1) {
                    //Player2
                    final double playerY = players.get(i).getCoord().row() * this.tileHeight
                    + this.labyrinthTopLeftY;
                    final double playerX = players.get(i).getCoord().column() * this.tileWidth
                    + this.labyrinthTopLeftX;
                    context2d.setFill(Color.BLUE);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                } else if (i == 2) {
                    //Player3
                    final double playerY = players.get(i).getCoord().row() * this.tileHeight
                    + this.labyrinthTopLeftY;
                    final double playerX = players.get(i).getCoord().column() * this.tileWidth
                    + this.labyrinthTopLeftX;
                    context2d.setFill(Color.GREEN);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                } else if (i == 3) {
                    //Player4
                    final double playerY = players.get(i).getCoord().row() * this.tileHeight
                    + this.labyrinthTopLeftY;
                    final double playerX = players.get(i).getCoord().column() * this.tileWidth
                    + this.labyrinthTopLeftX;
                    context2d.setFill(Color.YELLOW);
                    context2d.fillOval(playerX + border, playerY + border, tileMiddleSize, tileMiddleSize);
                }
            }
            context2d.restore();
        });
    }

    @Override
    public void drawPlayersStats(final PlayersContext playersManager, final List<Material> materialPresent) {
        Platform.runLater(() -> {
            final var context2d = this.canvas.getGraphicsContext2D();
            this.recalculateFontSizes();
            final double tileMiddleSize = this.tileWidth * TILE_MIDDLE_WIDTH;
            final double border = (tileWidth - tileMiddleSize) / 2 + 10;

            context2d.save();
            context2d.setTextBaseline(VPos.TOP);
            context2d.setFill(Color.BLACK);
            context2d.setFont(Font.font(this.headerFontSize));
            this.step = this.headerFontSize * 3 / 2;
            context2d.setTextAlign(TextAlignment.CENTER);
            final double headerPos = this.labyrinthTopLeftX + this.labyrinthSize;
            context2d.fillText("Players Statistics",
            headerPos + ((this.canvas.getWidth() - headerPos) / 2), 0);
            context2d.setTextAlign(TextAlignment.LEFT);
            context2d.setFont(Font.font(this.desFontSize));
            this.step = this.desFontSize * 3 / 2;

            for (int i = 0; i < playersManager.getPlayers().size(); i++) {
                if (i == 0) {
                    //Player1
                    context2d.setFill(Color.RED);
                    context2d.fillOval(this.playerStatsRegionX + border, border + step,
                    this.desFontSize, this.desFontSize);
                    if (i != playersManager.getActivePlayerIndex()) {
                        context2d.setFill(Color.BLACK);
                    }
                    context2d.fillText("Player1", this.playerStatsRegionX + border + step, border + step);
                    context2d.setFill(Color.BLACK);
                    //draw the player's points
                    context2d.fillText("Points = " + playersManager.getPlayers().get(i).getPoints(),
                    this.playerStatsRegionX + border, border + step * 2);
                    for (int j = 0; j < materialPresent.size(); j++) {
                        final var material = materialPresent.get(j);
                        context2d.fillText(material.name() + "  " + playersManager.getPlayers()
                        .get(i).getQuantityMaterial(material),
                        this.playerStatsRegionX + border, border + step * 3 + step * j);
                    }
                } else if (i == 1) {
                    //Player2
                    final double newStartPosY = border + step * 7;
                    context2d.setFill(Color.BLUE);
                    context2d.fillOval(this.playerStatsRegionX + border, newStartPosY,
                    this.desFontSize, this.desFontSize);
                    if (i != playersManager.getActivePlayerIndex()) {
                        context2d.setFill(Color.BLACK);
                    }
                    context2d.fillText("Player2", this.playerStatsRegionX + border + step, newStartPosY);
                    context2d.setFill(Color.BLACK);
                    //draw the player's points
                    context2d.fillText("Points = " + playersManager.getPlayers().get(i).getPoints(),
                    this.playerStatsRegionX + border, newStartPosY + step);
                    for (int j = 0; j < materialPresent.size(); j++) {
                        final var material = materialPresent.get(j);
                        context2d.fillText(material.name() + "  " + playersManager.getPlayers()
                        .get(i).getQuantityMaterial(material),
                        this.playerStatsRegionX + border, newStartPosY + step * 2 + step * j);
                    }
                } else if (i == 2) {
                    //Player3
                    final double newStartPosY = border + step * 13;
                    context2d.setFill(Color.GREEN);
                    context2d.fillOval(this.playerStatsRegionX + border, newStartPosY,
                    this.desFontSize, this.desFontSize);
                    if (i != playersManager.getActivePlayerIndex()) {
                        context2d.setFill(Color.BLACK);
                    }
                    context2d.fillText("Player3", this.playerStatsRegionX + border + step, newStartPosY);
                    context2d.setFill(Color.BLACK);
                    //draw the player's points
                    context2d.fillText("Points = " + playersManager.getPlayers().get(i).getPoints(),
                    this.playerStatsRegionX + border, newStartPosY + step);
                    for (int j = 0; j < materialPresent.size(); j++) {
                        final var material = materialPresent.get(j);
                        context2d.fillText(material.name() + "  " + playersManager.getPlayers()
                        .get(i).getQuantityMaterial(material),
                        this.playerStatsRegionX + border, newStartPosY + step * 2 + step * j);
                    }
                } else if (i == 3) {
                    //Player4
                    final double newStartPosY = border + step * 19;
                    context2d.setFill(Color.YELLOW);
                    context2d.fillOval(this.playerStatsRegionX + border, newStartPosY,
                    this.desFontSize, this.desFontSize);
                    if (i != playersManager.getActivePlayerIndex()) {
                        context2d.setFill(Color.BLACK);
                    }
                    context2d.fillText("Player4", this.playerStatsRegionX + border + step, newStartPosY);
                    context2d.setFill(Color.BLACK);
                    //draw the player's points
                    context2d.fillText("Points = " + playersManager.getPlayers().get(i).getPoints(),
                    this.playerStatsRegionX + border, newStartPosY + step);
                    for (int j = 0; j < materialPresent.size(); j++) {
                        final var material = materialPresent.get(j);
                        context2d.fillText(material.name() + "  " + playersManager.getPlayers()
                        .get(i).getQuantityMaterial(material),
                        this.playerStatsRegionX + border, newStartPosY + step * 2 + step * j);
                    }
                }
            }

            //Mostro il diceVal
            final double newStartPosY = this.canvas.getHeight() - step;
            context2d.fillText("Moves remaining: " + playersManager.getDiceValue(),
            this.playerStatsRegionX + border, newStartPosY);
            context2d.setFont(Font.getDefault());
            context2d.restore();
        });
    }
    /**
     * Draw the info of guild: missions, legends and missions completed.
     *  @param missions
     *  @param missionsCompleted
     */
    @Override
    public void drawGuildinfo(final List<Item> missions, final List<Item> missionsCompleted) {
        final Image point = ImageLoader.POINT.getImage();
        Platform.runLater(() -> {
            final var context2d = this.canvas.getGraphicsContext2D();
            context2d.save();
            this.recalculateFontSizes();
            context2d.setFill(Color.BLACK);
            context2d.setTextBaseline(VPos.TOP);
            context2d.setFont(Font.font(this.headerFontSize));
            context2d.fillText("Missions", labyrinthRegionX / 2, 0);
            context2d.setFont(Font.getDefault());
            drawMissions(missions, context2d, point);
            this.recalculateFontSizes();
            context2d.setFill(Color.BLACK);
            context2d.setTextBaseline(VPos.TOP);
            context2d.setFont(Font.font(this.headerFontSize));
            context2d.setTextAlign(TextAlignment.CENTER);
            context2d.fillText("Missions Completed", labyrinthTopLeftX / 2, lineMissionsY);
            context2d.setFont(Font.getDefault());
            i = i + 2;
            drawMissions(missionsCompleted, context2d, point);
            drawLegend(context2d);
            context2d.restore();
            i = 2;
        });
    }
    /**
     * Draw the legend of image-icon.
     * @param context2d
     */
    public void drawLegend(final GraphicsContext context2d) {
            final double height = this.canvas.getHeight();
            context2d.setFont(Font.font(this.headerFontSize));
            context2d.fillText(" LEGEND ", labyrinthRegionX / 2, height - imageDim * (3 * 2 + 1));
            context2d.setFont(Font.font(this.desFontSize));
            context2d.drawImage(categoryToImage(Category.ARMOR), lineMissionsX, height - imageDim * (3 + 2), imageDim, imageDim);
            context2d.fillText(" -> Type Armor", textLegendX, height - imageDim * (3 + 2));
            context2d.drawImage(categoryToImage(Category.CLOTHING), lineMissionsX, height - imageDim * 4, imageDim, imageDim);
            context2d.fillText(" -> Type Clothing", textLegendX, height - imageDim * 4);
            context2d.drawImage(categoryToImage(Category.WEAPON), lineMissionsX, height - imageDim * 3, imageDim, imageDim);
            context2d.fillText(" -> Type Weapon", textLegendX, height - imageDim * 3);
            context2d.drawImage(categoryToImage(Category.TOOL), lineMissionsX, height - imageDim * 2, imageDim, imageDim);
            context2d.fillText(" -> Type Tool", textLegendX, height - imageDim * 2);
            context2d.drawImage(categoryToImage(Category.JEWEL), lineMissionsX, height - imageDim, imageDim, imageDim);
            context2d.fillText(" -> Type Jewel", textLegendX, height - imageDim);
            context2d.restore();
            context2d.setFont(Font.getDefault());
    }
    /**
     * Method to draw the missions with the image.
     * All the implementations are recalculated dimensions adapted to the window
     * @param list
     * @param context2d
     * @param point
     */
    public void drawMissions(final List<Item> list, final GraphicsContext context2d, final Image point) {
        context2d.setTextAlign(TextAlignment.LEFT);
        for (final Item item : list) {
            this.recalculateFontSizes();
            context2d.setFont(Font.font(this.desFontSize));
            context2d.drawImage(categoryToImage(item.getCategory()), lineMissionsX, lineMissionsY, imageDim, imageDim);
            final double x = lineMissionsX + spaceMissionsX;
            context2d.drawImage(materialToImage(item.getMaterial()), x, lineMissionsY, imageDim, imageDim);
            context2d.fillText("\t" + "x" + item.getQuantity(), x, lineMissionsY);
            context2d.fillText(" " + item.getPoints(), lineMissionsX + spacePointX, lineMissionsY);
            context2d.drawImage(point, lineMissionsX + spacePointX + desFontSize + imageDim / (2 * 2), lineMissionsY, imageDim, imageDim);
            i++;
        }
        context2d.setFont(Font.getDefault());
    }

    @Override
    public void drawContext(final Context context) {
        Platform.runLater(() -> {
            final GraphicsContext context2d = this.canvas.getGraphicsContext2D();
            context2d.save();
            context2d.setFill(Color.BLACK);
            if (context instanceof UpdateBoardContext) {
                final UpdateBoardContext update = (UpdateBoardContext) context;

                context2d.setStroke(Color.WHITESMOKE);
                context2d.setFill(BASE_COLOR);
                final double popupWidth = this.labyrinthSize / 2;
                final double popupHeight = this.headerFontSize + this.desFontSize * 3;
                final double x = (this.canvas.getWidth() - popupWidth) / 2;
                final double y = (this.canvas.getHeight() - popupHeight) / 2;
                context2d.fillRect(x, y, popupWidth, popupHeight);
                context2d.strokeRect(x, y, popupWidth, popupHeight);
                context2d.setFill(playerIndexToColor(update.getVisualPlayerIndex()));
                context2d.setTextAlign(TextAlignment.CENTER);
                context2d.setTextBaseline(VPos.TOP);
                context2d.setFont(Font.font(this.headerFontSize));
                context2d.fillText("Player " + (update.getVisualPlayerIndex() + 1) + "'s turn", this.canvas.getWidth() / 2, y);

                //footer
                context2d.setFont(Font.font(this.desFontSize));
                context2d.setTextBaseline(VPos.BOTTOM);
                context2d.setFill(Color.BLACK);
                context2d.fillText("Press Enter/Space to dismiss", this.canvas.getWidth() / 2, y + popupHeight);
            }
            if (context instanceof LabyrinthContext) {
                for (final Coordinate t : ((LabyrinthContext) context).getSelected()) {
                    context2d.setStroke(Color.WHITESMOKE);
                    final double y = labyrinthTopLeftY + tileHeight * t.row();
                    context2d.strokeRect(labyrinthTopLeftX + tileWidth * t.column(), y, tileWidth, tileHeight);
                }
            }
            if (context instanceof PlayersContext && ((PlayersContext) context).getTurnSubphase() == Subphase.DICE) {
                context2d.setStroke(Color.WHITESMOKE);
                context2d.setFill(BASE_COLOR);
                final double popupWidth = this.labyrinthSize / 2;
                final double popupHeight = this.headerFontSize + this.desFontSize * 3;
                final double x = (this.canvas.getWidth() - popupWidth) / 2;
                final double y = (this.canvas.getHeight() - popupHeight) / 2;
                context2d.fillRect(x, y, popupWidth, popupHeight);
                context2d.strokeRect(x, y, popupWidth, popupHeight);
                context2d.setFill(Color.WHITESMOKE);
                context2d.setTextAlign(TextAlignment.CENTER);
                context2d.setTextBaseline(VPos.TOP);
                context2d.setFont(Font.font(this.headerFontSize));
                context2d.fillText("Press ENTER/SPACE to roll the dice", this.canvas.getWidth() / 2, y);

                //footer
                context2d.setFont(Font.font(this.desFontSize));
                context2d.setTextBaseline(VPos.BOTTOM);
                context2d.setFill(Color.BLACK);
                context2d.fillText("Press Enter/Space to dismiss",
                this.canvas.getWidth() / 2, y + popupHeight);
            }
            if (context instanceof GuildContext) {
                final GuildContext guild = (GuildContext) context;
                context2d.setFont(Font.font(this.desFontSize));
                i = 2;
                recalculateFontSizes();
                context2d.setTextBaseline(VPos.CENTER);
                final double dim = imageDim * guild.getMenuIndex();
                context2d.fillText(">", lineMissionsX / 2, lineMissionsY + dim + imageDim / 2);

            }
            context2d.restore();
        });
    }

    private Image materialToImage(final Material material) {
        switch (material) {
            case COAL:
                return ImageLoader.COAL.getImage();
            case COPPER:
                return ImageLoader.COPPER.getImage();
            case DIAMOND:
                return ImageLoader.DIAMOND.getImage();
            case IRON:
                return ImageLoader.IRON.getImage();
            case SILK:
                return ImageLoader.SILK.getImage();
            case WOOD:
                return ImageLoader.WOOD.getImage();
            default:
                return null;
        }
    }

    private Image categoryToImage(final Category category) {
        switch (category) {
            case ARMOR:
                return ImageLoader.ARMOR.getImage();
            case CLOTHING:
                return ImageLoader.CLOTHING.getImage();
            case JEWEL:
                return ImageLoader.JEWEL.getImage();
            case WEAPON:
                return ImageLoader.WEAPON.getImage();
            case TOOL:
                return ImageLoader.TOOL.getImage();
            default:
                return null;
        }
    }

    private Color playerIndexToColor(final int index) {
        switch (index) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            default:
                return null;
        }
    }

    /**
     * method to resize the text based on the window size.
     */
    private void recalculateFontSizes() {
        final double referenceHeight = Math.min(this.canvas.getHeight(), this.canvas.getWidth() * 1 / 6);
        final double baseFontSize = referenceHeight / 10;
        this.headerFontSize = baseFontSize;
        this.desFontSize = baseFontSize * 2 / 3;
        this.lineMissionsX = (baseFontSize * 2 / 3) * 2;
        this.lineMissionsY = (lineMissionsX / 2) * i * 3 / 2;
        this.imageDim = baseFontSize * 3 * (2 + 3) * 2 / (3 * 3 * 3 + 2);
        this.spaceMissionsX = lineMissionsX * 2;
        this.spacePointX = spaceMissionsX * 2 + lineMissionsX;
        this.textLegendX = lineMissionsX * 2;
    }

    private void recalculateLayout() {
        final double canvasWidth = this.canvas.getWidth();
        this.objectiveRegionWidth = canvasWidth * OBJECTIVE_REGION_WIDTH;
        this.labyrinthRegionWidth = canvasWidth * LABYRINTH_REGION_WIDTH;
        this.labyrinthRegionX = this.objectiveRegionWidth;

        //the available region of space is labyrinthRegionWidth*canvas.getHeight
        //the labyrinth must fit inside the allocated region
        this.labyrinthSize = Math.min(labyrinthRegionWidth, this.canvas.getHeight());
        this.labyrinthTopLeftX = (labyrinthRegionWidth - labyrinthSize) / 2 + labyrinthRegionX;
        this.labyrinthTopLeftY = (this.canvas.getHeight() - labyrinthSize) / 2;
        this.playerStatsRegionX = this.labyrinthTopLeftX + this.labyrinthSize;
    }

    @Override
    public void routeKeyboardEvents(final Receiver adapter) {
        this.scene.setOnKeyPressed(adapter::onKeyPressed);
    }

}
