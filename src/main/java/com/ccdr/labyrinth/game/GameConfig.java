package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.Material;

/* Temporary imports */
import java.util.Map;
import java.util.HashMap;
/* end temporary imports */

public class GameConfig {
    private final int GUILDNUMBER = 1;
    private static int playerCount;
    private int objectivesNum= 10;
    private int labyrinthHeight = 30;
    private int labyrinthWidth = 30;
    private int sourceTiles = 8; // change to percentage of source based on labyrinth dimensions
       
    public int getGuildNum() {
        return GUILDNUMBER;
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

    public static int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /*FIXME: temporary, waiting for get mission implementation */
    public Map<Integer, Material> setupMaterialsList() {
        Map<Integer, Material> materials = new HashMap<>();
        int sourceEach = this.sourceTiles / Material.values().length;
        int key = 0;
        for(Material m : Material.values()) {
            for(int i = sourceEach; i > 0; i--) {
                materials.put(key, m);
                key++;
            }
        }
        return materials;
    }

}
