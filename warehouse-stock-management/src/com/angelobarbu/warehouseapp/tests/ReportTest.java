package com.angelobarbu.warehouseapp.tests;

import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import java.util.SortedSet;
import static org.junit.Assert.*;
import com.angelobarbu.warehouseapp.domain.*;
import com.angelobarbu.warehouseapp.exceptions.*;

public class ReportTest {

    private Warehouse warehouse;
    private Supplier supplier1;
    private Supplier supplier2;
    private Product product1;
    private Product product2;

    public ReportTest() {}

    @Before
    public void setUp() {
        // Setup warehouse and suppliers
        warehouse = new Warehouse();
        supplier1 = new Supplier(1, "SupplierA", "Location1");
        supplier2 = new Supplier(2, "SupplierB", "Location2");

        // Mock products
        product1 = new Product() {
            @Override public int getId() { return 1; }
            @Override public String getName() { return "Product1"; }
            @Override public double getQuantity() { return 100; }
            @Override public void addQuantity(double amount) {}
            @Override public void removeQuantity(double amount) {}
        };

        product2 = new Product() {
            @Override public int getId() { return 2; }
            @Override public String getName() { return "Product2"; }
            @Override public double getQuantity() { return 200; }
            @Override public void addQuantity(double amount) {}
            @Override public void removeQuantity(double amount) {}
        };

        // Adding products to suppliers
        supplier1.addProduct(product1);
        supplier2.addProduct(product2);

        // Adding suppliers to warehouse
        try {
            warehouse.addSupplier(supplier1);
        } catch (SupplierExistsException e) {
            System.out.format("Testing: Supplier %s already exists", supplier1.getName());
        }

        try {
            warehouse.addSupplier(supplier2);
        } catch (SupplierExistsException e) {
            System.out.format("Testing: Supplier %s already exists", supplier2.getName());
        }
    }

    @Test
    public void testGetNumberOfSuppliers() {
        int numberOfSuppliers = WarehouseReport.getNumberOfSuppliers(warehouse);
        assertEquals("Number of suppliers should be 2", 2, numberOfSuppliers);
    }

    @Test
    public void testGetNumberOfProducts() {
        int numberOfProducts = WarehouseReport.getNumberOfProducts(warehouse);
        assertEquals("Total number of products should be 2", 2, numberOfProducts);
    }

    @Test
    public void testGetSuppliersSorted() {
        SortedSet<Supplier> sortedSuppliers = WarehouseReport.getSuppliersSorted(warehouse);
        assertNotNull("Sorted suppliers set should not be null", sortedSuppliers);
        assertEquals("Sorted suppliers set should have 2 suppliers", 2, sortedSuppliers.size());
        assertEquals("First supplier in sorted set should be SupplierA", "SupplierA", sortedSuppliers.first().getName());
    }

    @Test
    public void testGetProductsSortedByQuantity() {
        SortedSet<Product> sortedProducts = WarehouseReport.getProductsSortedByQuantity(warehouse);
        assertNotNull("Sorted products set should not be null", sortedProducts);
        assertEquals("Sorted products set should have 2 products", 2, sortedProducts.size());
        assertEquals("Product with the lowest quantity should be first", product1.getName(), sortedProducts.first().getName());
    }
}
