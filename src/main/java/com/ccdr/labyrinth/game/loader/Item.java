package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

import java.util.Set;

public class Item {
    private Category category;
    private Material material;
    private int quantity;
    private int points;

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public Category getCategory() {
        return this.category;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getPoints(){
        return this.points;
    }
}
