package com.ccdr.labyrinth;

import java.util.PrimitiveIterator;

import javafx.scene.image.Image;

public enum TypeImag_GAME {
    WALL("file:Images/Game/Wall.png"),
    PATHVERTICAL("file:Images/Game/Path_Vertical.png"),
    PATHHORIZONTAL("file:Images/Game/Path_Horizontal.png");

    private String path;
    private Image picture;
    
    private TypeImag_GAME(String path){
        this.path = path;
        this.picture = new Image(path, 0, 0, true, true);
    }

    public Image getImage(){
        return picture;
    }
}