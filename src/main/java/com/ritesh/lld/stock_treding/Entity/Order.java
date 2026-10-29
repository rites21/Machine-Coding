package com.ritesh.lld.stock_treding.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Order {
    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final String orderId;
    private final String userId;
    private final String stockId;
    private final int quantity;
    private final OrderSide side;
    private final OrderType type;
    private OrderStatus status;
    private final BigDecimal limitPrice;
    private BigDecimal executedPrice;
    private BigDecimal reservedAmount;
    private final LocalDateTime createdAt;

    public Order(String orderId, String userId, String stockId, int quantity,
                 OrderSide side, OrderType type, BigDecimal limitPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.side = side;
        this.type = type;
        this.limitPrice = limitPrice == null ? null : normalize(limitPrice);
        this.status = OrderStatus.PENDING;
        this.reservedAmount = BigDecimal.ZERO.setScale(MONEY_SCALE, MONEY_ROUNDING);
        this.createdAt = LocalDateTime.now();
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

    public OrderSide getSide() {
        return side;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getLimitPrice() {
        return limitPrice;
    }

    public BigDecimal getExecutedPrice() {
        return executedPrice;
    }

    public BigDecimal getReservedAmount() {
        return reservedAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void markReserved(BigDecimal amount) {
        this.reservedAmount = normalize(amount);
    }

    public void clearReservedAmount() {
        this.reservedAmount = BigDecimal.ZERO.setScale(MONEY_SCALE, MONEY_ROUNDING);
    }

    public void markExecuted(BigDecimal price) {
        if (this.status == OrderStatus.EXECUTED) {
            return;
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot execute a cancelled order");
        }
        this.status = OrderStatus.EXECUTED;
        this.executedPrice = normalize(price);
    }

    public void markCancelled() {
        this.status = OrderStatus.CANCELLED;
    }

    private static BigDecimal normalize(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Money value cannot be null");
        }
        return value.setScale(MONEY_SCALE, MONEY_ROUNDING);
    }
}
