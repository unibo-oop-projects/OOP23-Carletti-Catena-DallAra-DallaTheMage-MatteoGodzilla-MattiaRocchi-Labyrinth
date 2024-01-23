package com.ccdr.labyrinth.game;

public class GameConfig {
    private final int GUILD_NUMBER = 1;
    private int playerCount;
    private int objectivesNum= 10;
    private int labyrinthHeight = 30;
    private int labyrinthWidth = 30;
    private int sourceTiles = 8; // change to percentage of source based on labyrinth dimensions
       
    public int getGuildNum() {
        return GUILD_NUMBER;
    }
 
    public int getObjectivesNum() {
        return objectivesNum;
    }

    public void setObjectivesNum(int objectivesNum) {
        this.objectivesNum = objectivesNum;
    }

    public int getLabyrinthHeight() {
        return labyrinthHeight;
    }

    public void setLabyrinthHeight(int labyrinthHeight) {
        this.labyrinthHeight = labyrinthHeight;
    }

    public int getLabyrinthWidth() {
        return labyrinthWidth;
    }

    public void setLabyrinthWidth(int labyrinthWidth) {
        this.labyrinthWidth = labyrinthWidth;
    }

    public int getSourceTiles() {
        return sourceTiles;
    }

    public void setSourceTiles(int sourceTiles) {
        this.sourceTiles = sourceTiles;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

}
