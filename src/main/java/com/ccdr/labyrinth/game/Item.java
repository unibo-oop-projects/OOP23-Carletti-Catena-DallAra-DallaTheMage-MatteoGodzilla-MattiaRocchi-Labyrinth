package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

import java.io.InputStream;
import java.util.Set;


public class Item implements Loadable<Item> {
    
    private String name;
    private Category category;
    private Set<Material> requiredMaterialTypes;
    private MaterialBag requiredMaterials;

    public MaterialBag getRequiredMaterials() {
        return requiredMaterials;
    }

    @Override
    public Item LoadOne(InputStream file) {
        // TODO: Implementation of loading one JSON object from file. Waiting to know the formatting of the file.
        throw new UnsupportedOperationException("Unimplemented method 'LoadOne'");
    }

    @Override
    public Set<Item> LoadMany(InputStream file) {
        //TODO: Implementation of loading JSON objects from file. Waiting to know the formatting of the file. 
        throw new UnsupportedOperationException("Unimplemented method 'LoadMany'");
    }
    
}
