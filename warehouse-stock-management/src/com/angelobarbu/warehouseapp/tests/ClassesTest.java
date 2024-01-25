package com.angelobarbu.warehouseapp.tests;

import com.angelobarbu.warehouseapp.exceptions.NotEnoughQuantityException;
import org.junit.Test;
import com.angelobarbu.warehouseapp.domain.*;
import com.angelobarbu.warehouseapp.exceptions.*;
import static org.junit.Assert.*;

public class ClassesTest {
    public ClassesTest() {}

    @Test
    public void testNonOrganicProduct() {
        NonOrganicProduct nonOrganicProduct = new NonOrganicProduct(1, "TestProduct", 10.0);
        nonOrganicProduct.addQuantity(5.0);
        assertEquals(15.0, nonOrganicProduct.getQuantity(), 0.01);
        try {
            nonOrganicProduct.removeQuantity(5.0);
        } catch (NotEnoughQuantityException exc) {
            System.out.println("Testing remove quantity failed");
        }
        assertEquals(10.0, nonOrganicProduct.getQuantity(), 0.01);
        assertEquals(1, nonOrganicProduct.getId());
        assertEquals("TestProduct", nonOrganicProduct.getName());
    }

    @Test
    public void testOrganicProduct() {
        OrganicProduct organicProduct = new OrganicProduct(1, "TestOrganicProduct", 10.0, 15);
        organicProduct.addQuantity(5.0);
        assertEquals(15.0, organicProduct.getQuantity(), 0.01);
        try {
            organicProduct.removeQuantity(5.0);
        } catch (NotEnoughQuantityException exc) {
            System.out.println("Testing remove quantity failed");
        }
        assertEquals(10.0, organicProduct.getQuantity(), 0.01);
        assertEquals(1, organicProduct.getId());
        assertEquals("TestOrganicProduct", organicProduct.getName());
        assertEquals(15, organicProduct.getDaysOfValability());
    }

    @Test
    public void testSupplier() {
        Supplier supplier = new Supplier(1, "TestSupplier", "TestLocation");

        // Mock products for testing
        Product product1 = new Product() {
            @Override public int getId() { return 1; }
            @Override public String getName() { return "Product1"; }
            @Override public double getQuantity() { return 10; }
            @Override public void addQuantity(double amount) {}
            @Override public void removeQuantity(double amount) {}
        };

        Product product2 = new Product() {
            @Override public int getId() { return 2; }
            @Override public String getName() { return "Product2"; }
            @Override public double getQuantity() { return 20; }
            @Override public void addQuantity(double amount) {}
            @Override public void removeQuantity(double amount) {}
        };

        assertEquals("Initial ID should match", 1, supplier.getId());
        assertEquals("Initial name should match", "TestSupplier", supplier.getName());
        assertEquals("Initial location should match", "TestLocation", supplier.getLocation());
        assertTrue("Initial products set should be empty", supplier.getProducts().isEmpty());

        // Test setters
        supplier.setId(2);
        assertEquals("Updated ID should match", 2, supplier.getId());

        supplier.setName("NewName");
        assertEquals("Updated name should match", "NewName", supplier.getName());

        supplier.setLocation("NewLocation");
        assertEquals("Updated location should match", "NewLocation", supplier.getLocation());

        // Test adding products
        supplier.addProduct(product1);
        supplier.addProduct(product2);
        assertEquals("Products set should contain 2 products after adding", 2, supplier.getProducts().size());
        assertTrue("Products set should contain product1", supplier.getProducts().contains(product1));
        assertTrue("Products set should contain product2", supplier.getProducts().contains(product2));

        // Test immutability of products set
        try {
            supplier.getProducts().add(product1);
            fail("getProducts() should return an unmodifiable set");
        } catch (UnsupportedOperationException expected) {
            // Expected behavior, test should pass
        }
    }

    @Test
    public void testWarehouse() {
        Warehouse warehouse = new Warehouse();
        Supplier supplier1 = new Supplier(1, "Supplier1", "Location1");
        Supplier supplier2 = new Supplier(2, "Supplier2", "Location2");

        // Initial state
        assertEquals("Initial emailed suppliers count should be 0", 0, warehouse.getEmailedSuppliers());
        assertTrue("Initial supplier set should be empty", warehouse.getSuppliers().isEmpty());

        // Add a supplier and verify
        try {
            warehouse.addSupplier(supplier1);
            assertEquals("Emailed suppliers should be incremented", 1, warehouse.getEmailedSuppliers());
            assertTrue("Supplier set should include supplier1", warehouse.getSuppliers().contains(supplier1));
        } catch (SupplierExistsException e) {
            fail("SupplierExistsException should not be thrown here");
        }
        // Attempt to add the same supplier again and expect an exception
        try {
            warehouse.addSupplier(supplier1);
            fail("SupplierExistsException should be thrown when adding a duplicate supplier");
        } catch (SupplierExistsException e) {
            assertEquals("Emailed suppliers should not be incremented after exception", 1, warehouse.getEmailedSuppliers());
        }

        // Add another supplier and verify
        try {
            warehouse.addSupplier(supplier2);
            assertEquals("Emailed suppliers should be incremented to 2", 2, warehouse.getEmailedSuppliers());
            assertTrue("Supplier set should include supplier2", warehouse.getSuppliers().contains(supplier2));
        } catch (SupplierExistsException e) {
            fail("SupplierExistsException should not be thrown here");
        }
    }

}
