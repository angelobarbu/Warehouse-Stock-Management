package com.angelobarbu.warehouseapp.domain;

import com.angelobarbu.warehouseapp.exceptions.NotEnoughQuantityException;

import java.util.Objects;

public abstract class AbstractProduct implements Product {
    private int id;
    private String name;
    private double quantity;

    public AbstractProduct(int id, String name, double quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AbstractProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractProduct that = (AbstractProduct) o;
        return getId() == that.getId() && Double.compare(getQuantity(), that.getQuantity()) == 0 && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getQuantity());
    }

    @Override
    public void addQuantity(double amount) {
        if (amount < 0.0) {
            throw new IllegalArgumentException("Cannot add a negative amount");
        } else {
            this.quantity += amount;
        }
    }

    @Override
    public void removeQuantity(double amount) throws NotEnoughQuantityException {
        if (amount < 0.0) {
            throw new IllegalArgumentException("Cannot remove a negative amount");
        } else if (amount > this.quantity) {
            throw new NotEnoughQuantityException(this.id, this.quantity, amount, "Requested amount to remove exceeds the quantity");
        } else {
            this.quantity -= amount;
        }
    }
}
