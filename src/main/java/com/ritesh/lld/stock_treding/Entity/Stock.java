package com.ritesh.lld.stock_treding.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stock {
    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final String stockId;
    private final String symbol;
    private final String name;
    private BigDecimal currentPrice;

    public Stock(String stockId, String symbol, String name, BigDecimal currentPrice) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = normalize(currentPrice);
    }

    public Stock(String stockId, String symbol, String name, double currentPrice) {
        this(stockId, symbol, name, BigDecimal.valueOf(currentPrice));
    }

    public String getStockId() {
        return stockId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = normalize(currentPrice);
    }

    public void setCurrentPrice(double currentPrice) {
        setCurrentPrice(BigDecimal.valueOf(currentPrice));
    }

    private static BigDecimal normalize(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Stock price must be positive");
        }
        return value.setScale(MONEY_SCALE, MONEY_ROUNDING);
    }
}
