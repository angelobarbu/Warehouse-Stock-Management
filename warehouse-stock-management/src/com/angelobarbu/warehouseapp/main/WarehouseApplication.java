package com.angelobarbu.warehouseapp.main;

import com.angelobarbu.warehouseapp.domain.*;
import com.angelobarbu.warehouseapp.exceptions.NotEnoughQuantityException;
import com.angelobarbu.warehouseapp.exceptions.SupplierExistsException;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

public class WarehouseApplication {

    private static Warehouse warehouse;

    public static void main(String[] args) {
        warehouse = new Warehouse();
        modifyWarehouse();
        warehouseReport();
    }

    public WarehouseApplication() {}

    private static void modifyWarehouse() {
        Supplier supplier1 = new Supplier(1, "Fresh Fruit Inc.", "New Jersey");
        Product product1 = new NonOrganicProduct(1, "Boxes", 25.0);
        Product product2 = new OrganicProduct(2, "bananas", 2.5, 15);
        supplier1.addProduct(product1);
        supplier1.addProduct(product2);

        Supplier supplier2 = new Supplier(2, "Electronics Store", "New York");
        Product product3 = new NonOrganicProduct(3, "HDMI Cables", 50.0);
        Product product4 = new OrganicProduct(4, "Ham Sandwiches", 15.0, 30);
        supplier2.addProduct(product3);
        supplier2.addProduct(product4);

        try {
            warehouse.addSupplier(supplier1);
        } catch (SupplierExistsException exc) {
            System.out.format("Cannot add an already existing supplier: %s%n", supplier1.getName());
        }

        product1.addQuantity(50.0);

        try {
            product1.removeQuantity(60.0);
        } catch (NotEnoughQuantityException exc) {
            System.out.format("Not enough quantity of product id %d. Quantity left: %.2f, tried removing: %.2f %n", exc.getId(), exc.getQuantity(), exc.getAmount());
        }

        try {
            product1.removeQuantity(30.0);
        } catch (NotEnoughQuantityException exc) {
            System.out.format("Not enough quantity of product id %d. Quantity left: %.2f, tried removing: %.2f %n", exc.getId(), exc.getQuantity(), exc.getAmount());
        }

        try {
            product2.removeQuantity(5);
        } catch (NotEnoughQuantityException exc) {
            System.out.format("Not enough quantity of product id %d. Quantity left: %.2f, tried removing: %.2f %n", exc.getId(), exc.getQuantity(), exc.getAmount());
        }


        try {
            warehouse.addSupplier(supplier2);
        } catch (SupplierExistsException exc) {
            System.out.format("Cannot add an already existing supplier: %s%n", supplier2);
        }

        try {
            warehouse.addSupplier(supplier1);
        } catch (SupplierExistsException exc) {
            System.out.format("Cannot add an already existing supplier: %s%n", supplier1);
        }

    }

    private static void warehouseReport() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter a command (type 'exit' to exit):");
            switch (scanner.nextLine().trim()) {
                case "numberOfSuppliers":
                    System.out.println("Number of suppliers: " + WarehouseReport.getNumberOfSuppliers(warehouse));
                    break;
                case "numberOfProducts":
                    System.out.println("Number of products: " + WarehouseReport.getNumberOfProducts(warehouse));
                    break;
                case "suppliersSorted":
                    System.out.println("Suppliers sorted alphabetically: " + WarehouseReport.getSuppliersSorted(warehouse));
                    break;
                case "productsSortedByQuantity":
                    System.out.println("Products sorted by quantity: " + WarehouseReport.getProductsSortedByQuantity(warehouse));
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }

}