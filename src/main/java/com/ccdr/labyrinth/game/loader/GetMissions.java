package com.ccdr.labyrinth.game.loader;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

/*Class for creating missions belonging to the guild */
public class GetMissions {
    private final int MIN_REQUIRED = 5;
    private final int MAX_REQUIRED = 10;
    private int material_temp = 0;
    private int idCat = 0;
    private int idMat = 0;
    private Set<Category> category = new HashSet<>(Set.of(Category.values()));
    private List<Material> materialpresents = new ArrayList<>();
    private Set<Material> material = new HashSet<>(Set.of(Material.values()));
    private Random quantityGenerator = new Random();

    /*method for generating a mission considering that there must not be identical missions
      and that there must be two missions per material */
    public Item generateMission() {
        Item item = new Item(); /*creation of the new Item to be able to generate a list */

        /*Condition used to not repeat the same category with the same material */
        if (material_temp == 1 || material_temp == 0) {
            if (material_temp == 1) {
                category.remove(Category.values()[idCat]);
            }

            do {
                idCat = quantityGenerator.nextInt(0,5);
            } while(!category.contains(Category.values()[idCat]));
        }

        /*Condition used to not repeat the same material */
        if (material_temp == 2 || material_temp == 0) {
            if (material_temp == 2) {
                material.remove(Material.values()[idMat]);
                material_temp = 0;
            }

            do {
                idMat = quantityGenerator.nextInt(0,6);
            } while(!material.contains(Material.values()[idMat]));
        }

        item.setCategory(Category.values()[idCat]);
        item.setMaterial(Material.values()[idMat]);
        material_temp++;

        if (!materialpresents.contains(item.getMaterial())) {
            materialpresents.add(item.getMaterial());
        }
        /*Set quantity of materials */
        item.setQuantity(quantityGenerator.nextInt(MIN_REQUIRED,MAX_REQUIRED));
        /*Set quantity of points */
        item.setPoints(quantityGenerator.nextInt(6,11));

        return item;

    }
    
    public int getMaxPoints(){
        return this.MAX_REQUIRED;
    }

    public List<Material> materialPresents() {
        return materialpresents;
    }
}
