package com.angelobarbu.warehouseapp.domain;

import java.util.Set;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Comparator;

public class WarehouseReport {
    public WarehouseReport() {}

    public static int getNumberOfSuppliers(Warehouse warehouse) {
        return warehouse.getSuppliers().size();
    }

    public static int getNumberOfProducts(Warehouse warehouse) {
        int noProducts = 0;

        for (Supplier supplier : warehouse.getSuppliers()) {
            noProducts += supplier.getProducts().size();
        }

        return noProducts;
    }

    public static SortedSet<Supplier> getSuppliersSorted(Warehouse warehouse) {
        SortedSet<Supplier> sortedSuppliers = new TreeSet<>(Comparator.comparing(Supplier::getName).thenComparing(Supplier::getLocation));
        sortedSuppliers.addAll(warehouse.getSuppliers());
        return sortedSuppliers;
    }

    public static SortedSet<Product> getProductsSortedByQuantity(Warehouse warehouse) {
        SortedSet<Product> allProducts = new TreeSet<>(Comparator.comparingDouble(Product::getQuantity).thenComparing(Product::getId));
        for (Supplier supplier : warehouse.getSuppliers()) {
            allProducts.addAll(supplier.getProducts());
        }
        return allProducts;
    }
}
