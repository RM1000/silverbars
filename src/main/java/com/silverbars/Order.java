package com.silverbars;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {

    private final String user;
    private final BigDecimal price; // sterling
    private final BigDecimal quantity; // kg
    private final Type type;

    public Order(String user,BigDecimal price, BigDecimal quantity, Type type) {
        this.user = user;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public String getUser() {
        return user;
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
        Order order = (Order) o;
        return Objects.equals(user, order.user) &&
                Objects.equals(price, order.price) &&
                Objects.equals(quantity, order.quantity) &&
                type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, price, quantity, type);
    }
}
