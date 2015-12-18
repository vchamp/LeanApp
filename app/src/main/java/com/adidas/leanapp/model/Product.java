package com.adidas.leanapp.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Product {

    String kind;
    String name;
    BigDecimal cost;

    BigDecimal discount = new BigDecimal(0);

    public Product(String name, BigDecimal cost) {
        this(name, name, cost);
    }

    public Product(String name, String kind, BigDecimal cost) {
        super();
        this.name = name;
        this.kind = kind;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void addDiscount(BigDecimal discount) {
        this.discount = this.discount.add(discount);
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public static String getDisplayCost(BigDecimal cost) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(cost);
    }
}
