package com.ccdr.labyrinth.game.loader;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;


public class Item {
    
    private String name;
    private Category category;
    private Set<Material> requiredMaterialTypes;
    private MaterialBag requiredMaterials;

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setRequiredMaterialTypes(Set<Material> requiredMaterialTypes) {
        this.requiredMaterialTypes = requiredMaterialTypes;
    }

    public MaterialBag getRequiredMaterials() {
        return requiredMaterials;
    }
}