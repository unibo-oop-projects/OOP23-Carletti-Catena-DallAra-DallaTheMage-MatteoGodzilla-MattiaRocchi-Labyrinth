package com.ccdr.labyrinth;

import javafx.scene.image.Image;

public enum TypeImag {
    //path is redirected through src/main/resources
    //if the file could not be found, jfx will throw an exception
    //(it doesn't do that when you use the 'file:' protocol)
    //MENU
    LOGO("/menu/Logo-v3.png"),
    //GAME
    WALL("/game/Wall.png"),
    PATH_VERTICAL("/game/PathVertical.png"),
    PATH_HORIZONTAL("/game/PathHorizontal.png"),
    PATH_CENTRAL("/game/PathCentral.png"),
    PATH("/game/Path.png"),
    GUILD("/game/Guild.png"),
    //MATERIALS
    COAL("/game/materials/Coal.png"),
    COPPER("/game/materials/Copper.png"),
    DIAMOND("/game/materials/Diamond.png"),
    IRON("/game/materials/Iron.png"),
    SILK("/game/materials/Silk.png"),
    WOOD("/game/materials/Wood.png"),
    //CATEGORYS
    ARMOR("/game/category/Armor.png"),
    CLOTHING("/game/category/Clothing.png"),
    JEWEL("/game/category/Jewel.png"),
    TOOL("/game/category/Tool.png"),
    WEAPON("/game/category/Weapon.png");

    //private String path;
    private Image picture;

    private TypeImag(String path){
        //this.path = path;
        this.picture = new Image(path, 0, 0, true, true);
    }

    public Image getImage(){
        return picture;
    }
}
