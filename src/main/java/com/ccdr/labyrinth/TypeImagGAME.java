package com.ccdr.labyrinth;

import java.util.PrimitiveIterator;

import javafx.scene.image.Image;

public enum TypeImagGAME {
    WALL("/Game/Wall.png"),
    PATHVERTICAL("/Game/PathVertical.png"),
    PATHHORIZONTAL("/Game/PathHorizontal.png");

    private String path;
    private Image picture;

    private TypeImagGAME(String path){
        this.path = path;
        this.picture = new Image(path, 0, 0, true, true);
    }

    public Image getImage(){
        return picture;
    }
}