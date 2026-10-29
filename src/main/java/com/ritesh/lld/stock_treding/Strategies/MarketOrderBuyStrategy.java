package com.ritesh.lld.stock_treding.Strategies;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ritesh.lld.stock_treding.Entity.Account;
import com.ritesh.lld.stock_treding.Entity.Order;
import com.ritesh.lld.stock_treding.Entity.OrderStatus;
import com.ritesh.lld.stock_treding.Entity.Stock;
import com.ritesh.lld.stock_treding.Entity.User;
import com.ritesh.lld.stock_treding.Service.OrderExecutionService;

public class MarketOrderBuyStrategy implements OrderSelectStrategy {

    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final OrderExecutionService orderExecutionService;

    public MarketOrderBuyStrategy(OrderExecutionService orderExecutionService) {
        this.orderExecutionService = orderExecutionService;
    }

    @Override
    public boolean buyStock(User user, Stock stock, Order order) {
        if (order.getStatus() == OrderStatus.EXECUTED) {
            return true;
        }

        BigDecimal marketPrice = stock.getCurrentPrice();
        BigDecimal cost = marketPrice.multiply(BigDecimal.valueOf(order.getQuantity()))
                .setScale(MONEY_SCALE, MONEY_ROUNDING);

        Account account = user.getAccount();
        if (!account.hasSufficientBalance(cost)) {
            throw new IllegalStateException(
                    "Insufficient balance. Required: " + cost
                            + ", Available: " + account.getAvailableBalance());
        }

        orderExecutionService.executeBuyFromAvailable(user, stock, order, marketPrice);
        return true;
    }
}
