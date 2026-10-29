package com.ritesh.lld.stock_treding.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Holding {
    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final String stockId;
    private int quantity;
    private BigDecimal averagePurchasePrice;

    public Holding(String stockId, int quantity, BigDecimal averagePurchasePrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Holding quantity must be positive");
        }
        this.stockId = stockId;
        this.quantity = quantity;
        this.averagePurchasePrice = normalize(averagePurchasePrice);
    }

    public Holding(String stockId, int quantity, double averagePurchasePrice) {
        this(stockId, quantity, BigDecimal.valueOf(averagePurchasePrice));
    }

    public String getStockId() {
        return stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getAveragePurchasePrice() {
        return averagePurchasePrice;
    }

    public void addQuantity(int qty, BigDecimal purchasePrice) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive");
        }
        BigDecimal price = normalize(purchasePrice);
        BigDecimal existingCost = averagePurchasePrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal newCost = price.multiply(BigDecimal.valueOf(qty));
        quantity += qty;
        averagePurchasePrice = existingCost.add(newCost)
                .divide(BigDecimal.valueOf(quantity), MONEY_SCALE, MONEY_ROUNDING);
    }

    public void reduceQuantity(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity to reduce must be positive");
        }
        if (qty > quantity) {
            throw new IllegalStateException("Not enough holding quantity");
        }
        quantity -= qty;
    }

    private static BigDecimal normalize(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Purchase price must be positive");
        }
        return value.setScale(MONEY_SCALE, MONEY_ROUNDING);
    }
}
