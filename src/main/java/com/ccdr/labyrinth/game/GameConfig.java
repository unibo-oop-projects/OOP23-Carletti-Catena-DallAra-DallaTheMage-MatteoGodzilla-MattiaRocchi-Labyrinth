package com.ccdr.labyrinth.game;

//TODO: change this to a record
public class GameConfig {
    //private final int guildNumber = 1;
    private int playerCount = 2;
    private int objectivesNum = 10;
    private int labyrinthHeight = 31;
    private int labyrinthWidth = 31;
    // change to percentage of source based on labyrinth dimensions
    private int sourceTiles = 8;

    //There's always only one guild
    /*
     *
     public int getGuildNum() {
         return guildNumber;
        }
    */

    //Getters
    public int getObjectivesNum() {
        return objectivesNum;
    }
    public int getLabyrinthWidth() {
        return labyrinthWidth;
    }
    public int getLabyrinthHeight() {
        return labyrinthHeight;
    }
    public int getSourceTiles() {
        return sourceTiles;
    }
    public int getPlayerCount() {
        return playerCount;
    }

    //Setters
    public void setObjectivesNum(int objectivesNum) {
        this.objectivesNum = objectivesNum;
    }
    public void setLabyrinthWidth(int labyrinthWidth) {
        this.labyrinthWidth = labyrinthWidth;
    }
    public void setLabyrinthHeight(int labyrinthHeight) {
        this.labyrinthHeight = labyrinthHeight;
    }
    public void setSourceTiles(int sourceTiles) {
        this.sourceTiles = sourceTiles;
    }
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

}
