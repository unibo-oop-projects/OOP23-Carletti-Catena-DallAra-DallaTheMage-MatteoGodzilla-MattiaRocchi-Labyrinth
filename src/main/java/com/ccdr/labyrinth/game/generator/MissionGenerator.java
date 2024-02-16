package com.ccdr.labyrinth.game.generator;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.ccdr.labyrinth.game.util.Category;
import com.ccdr.labyrinth.game.util.Item;
import com.ccdr.labyrinth.game.util.Material;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class for creating missions belonging to the guild.
 */
public class MissionGenerator {
    private static final int MIN_REQUIRED = 5;
    private static final int MAX_REQUIRED = 10;
    private int materialtemp;
    private int idCat;
    private int idMat;
    private final Set<Category> category = new HashSet<>(Set.of(Category.values()));
    private final List<Material> materialpresents = new ArrayList<>();
    private final Set<Material> material = new HashSet<>(Set.of(Material.values()));
    private final Random quantityGenerator = new Random();

    /**
     * method for generating a mission considering that there must not be
     * identical missions and that there must be two missions per material.
     * @return one item tha is a mission
     */
    public final Item generateMission() {
        final Item item = new Item(); /**creation of the new Item to be able to generate a list. */

        /**Condition used to not repeat the same category with the same material. */
        if (materialtemp == 1 || materialtemp == 0) {
            if (materialtemp == 1) {
                category.remove(Category.values()[idCat]);
            }

            do {
                idCat = quantityGenerator.nextInt(0, 2 * 2 + 1);
            } while (!category.contains(Category.values()[idCat]));
        }

        /**Condition used to not repeat the same material. */
        if (materialtemp == 2 || materialtemp == 0) {
            if (materialtemp == 2) {
                material.remove(Material.values()[idMat]);
                materialtemp = 0;
            }

            do {
                idMat = quantityGenerator.nextInt(0, 2 * 3);
            } while (!material.contains(Material.values()[idMat]));
        }

        item.setCategory(Category.values()[idCat]);
        item.setMaterial(Material.values()[idMat]);
        materialtemp++;

        if (!materialpresents.contains(item.getMaterial())) {
            materialpresents.add(item.getMaterial());
        }
        /**Set quantity of materials. */
        item.setQuantity(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
        /**Set quantity of points. */
        item.setPoints(quantityGenerator.nextInt(2 * 3, 10 + 1));

        return item;

    }
    /**
     *
     * @return max point
     */
    public final int getMaxPoints() {
        return MAX_REQUIRED;
    }
    /**
     *
     * @return materials presents in game
     */
    public final List<Material> materialPresents() {
        return materialpresents;
    }
}
