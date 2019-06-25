package com.silverbars;

import java.math.BigDecimal;
import java.util.Objects;

public class Summary {

    private final BigDecimal price;
    private final BigDecimal quantity;
    private final Type type;

    public Summary(BigDecimal price, BigDecimal quantity, Type type) {
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary summary = (Summary) o;
        return Objects.equals(price, summary.price) &&
                Objects.equals(quantity, summary.quantity) &&
                type == summary.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity, type);
    }

    @Override
    public String toString() {
        return "Summary{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", type=" + type +
                '}';
    }
}
