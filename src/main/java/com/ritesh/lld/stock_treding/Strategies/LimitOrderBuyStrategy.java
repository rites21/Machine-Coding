package com.ritesh.lld.stock_treding.Strategies;

import com.ritesh.lld.stock_treding.Entity.Account;
import com.ritesh.lld.stock_treding.Entity.Order;
import com.ritesh.lld.stock_treding.Entity.OrderStatus;
import com.ritesh.lld.stock_treding.Entity.Stock;
import com.ritesh.lld.stock_treding.Entity.User;
import com.ritesh.lld.stock_treding.Service.OrderExecutionService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LimitOrderBuyStrategy implements OrderSelectStrategy {

    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final OrderExecutionService orderExecutionService;

    public LimitOrderBuyStrategy(OrderExecutionService orderExecutionService) {
        this.orderExecutionService = orderExecutionService;
    }

    @Override
    public boolean buyStock(User user, Stock stock, Order order) {
        if (order.getStatus() == OrderStatus.EXECUTED) {
            return true;
        }

        BigDecimal limitPrice = order.getLimitPrice();
        if (limitPrice == null || limitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Limit price required for LIMIT order");
        }

        BigDecimal marketPrice = stock.getCurrentPrice();
        boolean alreadyReserved = order.getReservedAmount().compareTo(BigDecimal.ZERO) > 0;

        if (marketPrice.compareTo(limitPrice) <= 0) {
            if (alreadyReserved) {
                orderExecutionService.executeBuyFromReserved(user, stock, order, marketPrice);
            } else {
                BigDecimal cost = marketPrice.multiply(BigDecimal.valueOf(order.getQuantity()))
                        .setScale(MONEY_SCALE, MONEY_ROUNDING);
                Account account = user.getAccount();
                if (!account.hasSufficientBalance(cost)) {
                    throw new IllegalStateException(
                            "Insufficient balance. Required: " + cost
                                    + ", Available: " + account.getAvailableBalance());
                }
                orderExecutionService.executeBuyFromAvailable(user, stock, order, marketPrice);
            }
            return true;
        }

        if (!alreadyReserved) {
            BigDecimal reservedCost = limitPrice.multiply(BigDecimal.valueOf(order.getQuantity()))
                    .setScale(MONEY_SCALE, MONEY_ROUNDING);
            Account account = user.getAccount();
            if (!account.hasSufficientBalance(reservedCost)) {
                throw new IllegalStateException(
                        "Insufficient balance to place limit order. Required: " + reservedCost
                                + ", Available: " + account.getAvailableBalance());
            }
            account.reserve(reservedCost);
            order.markReserved(reservedCost);
        }

        return false;
    }
}
