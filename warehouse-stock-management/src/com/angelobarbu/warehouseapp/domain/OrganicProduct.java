package com.angelobarbu.warehouseapp.domain;

public class OrganicProduct extends AbstractProduct {
    private int daysOfValability;

    public OrganicProduct(int id, String name, double quantity, int daysOfValability) {
        super(id, name, quantity);
        if (daysOfValability < 0) {
            throw new IllegalArgumentException("Cannot create an organic product with a negative valability duration");
        } else {
            this.daysOfValability = daysOfValability;
        }
    }

    public int getDaysOfValability() {
        return daysOfValability;
    }
}
