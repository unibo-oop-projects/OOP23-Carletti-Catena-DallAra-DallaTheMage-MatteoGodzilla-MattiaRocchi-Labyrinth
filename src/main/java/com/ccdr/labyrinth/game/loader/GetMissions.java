package com.ccdr.labyrinth.game.loader;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

public class GetMissions {
    private final int MIN_REQUIRED = 3;
    private final int MAX_REQUIRED = 9;
    private static int material_temp = 0;
    private static int id_cat = 0;
    private static int id_mat = 0;
    private static Item item;
    private static Category[] category = Category.values();
    private static Material[] material = Material.values();
    private static Random quantityGenerator = new Random();
    private static int[] mat_select;

    public static void setTypeMatItem(){
        id_cat = quantityGenerator.nextInt(0,4);
        item.setCategory(category[id_cat]);
        if(material_temp == 2){
            id_mat = quantityGenerator.nextInt(0,6);
            for(int i = 0; i<mat_select.length; i++){
                if(id_mat == mat_select[i]){
                    setTypeMatItem();
                    break;
                }
            }
            material_temp = 0;
        }
        item.setMaterial(material[id_mat]);
        mat_select[id_mat] = id_mat;
        material_temp++;
    }

    public static boolean control(List<Item>missions){
        for(Item mission : missions){
            if(mission.equals(item) ){
                return false;
            }
        }
        return true;
    }


    public void setRandomRequiredQuantities() {
        switch (item.getMaterial()) {
            case WOOD:
                item.getRequiredMaterials().setRequiredWood(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                break;
            case COAL:
                item.getRequiredMaterials().setRequiredCoal(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                break;
            case IRON:
                item.getRequiredMaterials().setRequiredIron(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                break;
            case COPPER:
                item.getRequiredMaterials().setRequiredCopper(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                break;
            case DIAMOND:
                item.getRequiredMaterials().setRequiredDiamond(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                break;
            case SILK:
                item.getRequiredMaterials().setRequiredSilk(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                break;
        }
    }

    public static Item Get_mis(){
        return item;
    }
}
