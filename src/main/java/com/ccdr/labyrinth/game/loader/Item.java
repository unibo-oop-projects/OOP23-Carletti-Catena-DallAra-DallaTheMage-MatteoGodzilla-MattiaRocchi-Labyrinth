package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

import java.util.Set;

public class Item {
    private Category category;
    private Material material;
    private int quantity;

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
