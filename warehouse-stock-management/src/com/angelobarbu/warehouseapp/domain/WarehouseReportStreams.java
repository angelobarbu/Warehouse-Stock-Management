package com.angelobarbu.warehouseapp.domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.SortedSet;
import java.util.TreeSet;

public class WarehouseReportStreams {

    public WarehouseReportStreams() {}
    public static int getNumberOfSuppliers(Warehouse warehouse) {
        // return warehouse.getSuppliers().size();
        return (int) warehouse.getSuppliers().stream().count();
    }

    public static int getNumberOfProducts(Warehouse warehouse) {
        /* int noProducts = 0;

        for (Supplier supplier : warehouse.getSuppliers()) {
            noProducts += supplier.getProducts().size();
        }

        return noProducts; */

        return (warehouse.getSuppliers().stream().flatMap((supplier) -> {
            return supplier.getProducts().stream();
        }).toList()).size();
    }

    public static SortedSet<Supplier> getSuppliersSorted(Warehouse warehouse) {
        /* SortedSet<Supplier> sortedSuppliers = new TreeSet<>(Comparator.comparing(Supplier::getName).thenComparing(Supplier::getLocation));
        sortedSuppliers.addAll(warehouse.getSuppliers());
        return sortedSuppliers; */
        return ((SortedSet<Supplier>) warehouse.getSuppliers().stream().collect(Collectors.toCollection(() -> {
            return new TreeSet(Comparator.comparing(Supplier::getName).thenComparing(Supplier::getLocation));
        })));
    }

    public static SortedSet<Product> getProductsSortedByQuantity(Warehouse warehouse) {
        /* SortedSet<Product> allProducts = new TreeSet<>(Comparator.comparingDouble(Product::getQuantity).thenComparing(Product::getId));
        for (Supplier supplier : warehouse.getSuppliers()) {
            allProducts.addAll(supplier.getProducts());
        }
        return allProducts; */
        return (SortedSet<Product>) warehouse.getSuppliers().stream().flatMap((supplier) -> {
            return supplier.getProducts().stream();
        }).collect(Collectors.toCollection(() -> {
            return new TreeSet(Comparator.comparingDouble(Product::getQuantity).thenComparing(Product::getId));
        }));
    }
}
