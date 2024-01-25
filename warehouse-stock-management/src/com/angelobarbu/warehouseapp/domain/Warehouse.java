package com.angelobarbu.warehouseapp.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Collections;
import java.util.List;
import com.angelobarbu.warehouseapp.utils.SupplierRegistrationListener;
import com.angelobarbu.warehouseapp.exceptions.SupplierExistsException;

public class Warehouse {
    private final Set<Supplier> suppliers = new LinkedHashSet<>();
    private final List<SupplierRegistrationListener> listeners = new ArrayList<>();
    private int emailedSuppliers = 0;

    public Warehouse() {
        this.listeners.add(supplier -> {
            System.out.println("Notification email for supplier " + supplier.getName() + " to be sent");
            ++Warehouse.this.emailedSuppliers;
        });
    }

    public int getEmailedSuppliers() {
        return this.emailedSuppliers;
    }

    public void addSupplier(Supplier supplier) throws SupplierExistsException {
        if (this.suppliers.contains(supplier)) {
            throw new SupplierExistsException("Supplier already exists in the warehouse's suppliers' list.");
        } else {
            this.suppliers.add(supplier);
            this.notify(supplier);
        }
    }

    private void notify(Supplier supplier) {

        for (SupplierRegistrationListener listener : this.listeners) {
            listener.onSupplierAdded(supplier);
        }
    }

    public Set<Supplier> getSuppliers() {
        return Collections.unmodifiableSet(this.suppliers);
    }
}
