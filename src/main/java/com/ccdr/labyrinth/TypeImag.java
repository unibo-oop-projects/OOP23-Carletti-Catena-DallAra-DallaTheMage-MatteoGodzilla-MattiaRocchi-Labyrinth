package com.ccdr.labyrinth;

import java.util.PrimitiveIterator;

import javafx.scene.image.Image;

public enum TypeImag {
    //path is redirected through src/main/resources
    //if the file could not be found, jfx will throw an exception
    //(it doesn't do that when you use the 'file:' protocol)
    LOGO("/menu/Logo-v3.png"),
    //TEXT("/menu/Text.png");
    WALL("/Game/Wall.png"),
    PATHVERTICAL("/Game/PathVertical.png"),
    PATHHORIZONTAL("/Game/PathHorizontal.png");

    private String path;
    private Image picture;

    private TypeImag(String path){
        this.path = path;
        this.picture = new Image(path, 0, 0, true, true);
    }

    public Image getImage(){
        return picture;
    }
}
