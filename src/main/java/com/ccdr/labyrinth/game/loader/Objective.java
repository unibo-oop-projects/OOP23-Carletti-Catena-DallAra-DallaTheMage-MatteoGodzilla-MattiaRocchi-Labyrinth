package com.ccdr.labyrinth.game.loader;

import java.util.Random;

public class Objective  {

    private final int MIN_REQUIRED = 1;
    private final int MAX_REQUIRED = 9;
    private Item item;
    private Random quantityGenerator = new Random();

    /*    public void setRandomRequiredQuantities(Set<Material> materialsToSet) {
        for(Material material : materialsToSet) {
            switch (material) {
                case WOOD:
                    item.getRequiredMaterials().setRequiredWood(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case COAL:
                    item.getRequiredMaterials().setRequiredCoal(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case IRON:
                    item.getRequiredMaterials().setRequiredIron(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case COPPER:
                    item.getRequiredMaterials().setRequiredCopper(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case DIAMOND:
                    item.getRequiredMaterials().setRequiredDiamond(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case SILK:
                    item.getRequiredMaterials().setRequiredSilk(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
            }
        }
    }*/
}
