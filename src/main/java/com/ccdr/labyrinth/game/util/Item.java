package com.ccdr.labyrinth.game.util;

/**
 * Class Item that rappresent one mission item to do.
 */
public class Item {
    private Category category;
    private Material material;
    private int quantity;
    private int points;

    /**
     *
     * @param category
     */
    public final void setCategory(final Category category) {
        this.category = category;
    }
    /**
     *
     * @param material
     */
    public final void setMaterial(final Material material) {
        this.material = material;
    }
    /**
     *
     * @param quantity
     */
    public final void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
    /**
     *
     * @param points
     */
    public final void setPoints(final int points) {
        this.points = points;
    }
    /**
     *
     * @return item category
     */
    public final Category getCategory() {
        return this.category;
    }
    /**
     *
     * @return item material
     */
    public final Material getMaterial() {
        return this.material;
    }
    /**
     *
     * @return item material quantity
     */
    public final int getQuantity() {
        return this.quantity;
    }
    /**
     *
     * @return item value points
     */
    public final int getPoints() {
        return this.points;
    }
}
