package com.angelobarbu.warehouseapp.exceptions;

public class NotEnoughQuantityException extends WarehouseException {
    private static final long serialVersionUID = 92381239571239123L;
    private int id;
    private double quantity;
    private double amount;

    public NotEnoughQuantityException(int id, double quantity, double amount, String message) {
        super(message);
        this.id = id;
        this.quantity = quantity;
        this.amount = (double) Math.round(amount * 100.0) / 100.0;
    }

    public int getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }
}
