package com.ritesh.lld.stock_treding.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Trade {
    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final String tradeId;
    private final String orderId;
    private final String userId;
    private final String stockId;
    private final int quantity;
    private final BigDecimal price;
    private final OrderSide side;
    private final LocalDateTime executedAt;

    public Trade(String tradeId, String orderId, String userId, String stockId,
                 int quantity, BigDecimal price, OrderSide side) {
        this.tradeId = tradeId;
        this.orderId = orderId;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.price = price.setScale(MONEY_SCALE, MONEY_ROUNDING);
        this.side = side;
        this.executedAt = LocalDateTime.now();
    }

    public String getTradeId() {
        return tradeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getStockId() {
        return stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderSide getSide() {
        return side;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }
}
