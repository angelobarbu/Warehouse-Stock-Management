package com.angelobarbu.warehouseapp.domain;

import com.angelobarbu.warehouseapp.exceptions.NotEnoughQuantityException;

public interface Product {
    int getId();
    String getName();
    double getQuantity();
    void addQuantity(double amount);
    void removeQuantity(double amount) throws NotEnoughQuantityException;
}
