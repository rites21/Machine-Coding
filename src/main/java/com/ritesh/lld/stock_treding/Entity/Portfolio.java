package com.ritesh.lld.stock_treding.Entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private final String portfolioId;
    private final Map<String, Holding> holdings;

    public Portfolio(String portfolioId) {
        this.portfolioId = portfolioId;
        this.holdings = new HashMap<>();
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public Map<String, Holding> getHoldings() {
        return Collections.unmodifiableMap(holdings);
    }

    public Holding getHolding(String stockId) {
        return holdings.get(stockId);
    }

    public void addBuy(String stockId, int quantity, BigDecimal price) {
        Holding holding = holdings.get(stockId);
        if (holding == null) {
            holdings.put(stockId, new Holding(stockId, quantity, price));
        } else {
            holding.addQuantity(quantity, price);
        }
    }

    public void removeSell(String stockId, int quantity) {
        Holding holding = holdings.get(stockId);
        if (holding == null || holding.getQuantity() < quantity) {
            throw new IllegalStateException("Insufficient holdings for stock: " + stockId);
        }
        holding.reduceQuantity(quantity);
        if (holding.getQuantity() == 0) {
            holdings.remove(stockId);
        }
    }
}
