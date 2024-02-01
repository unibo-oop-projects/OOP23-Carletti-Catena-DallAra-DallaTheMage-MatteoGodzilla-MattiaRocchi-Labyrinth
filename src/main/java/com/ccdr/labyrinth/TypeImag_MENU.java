package com.ccdr.labyrinth;

import java.util.PrimitiveIterator;

import javafx.scene.image.Image;

public enum TypeImag_MENU {
    ICON("file:Images/Menu/Icon.png"),
    TEXT("file:Images/Menu/Text.png");

    private String path;
    private Image picture;
    
    private TypeImag_MENU(String path){
        this.path = path;
        this.picture = new Image(path, 0, 0, true, true);
    }

    public Image getImage(){
        return picture;
    }
}
