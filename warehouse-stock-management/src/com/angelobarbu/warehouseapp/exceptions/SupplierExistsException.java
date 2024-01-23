package com.angelobarbu.warehouseapp.exceptions;

public class SupplierExistsException extends WarehouseException {
    private static final long serialVersionUID = 5721920572112398L;
    public SupplierExistsException(String message) {
        super(message);
    }
}
