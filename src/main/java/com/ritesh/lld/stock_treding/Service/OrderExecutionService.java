package com.ritesh.lld.stock_treding.Service;

import com.ritesh.lld.stock_treding.Entity.Order;
import com.ritesh.lld.stock_treding.Entity.OrderSide;
import com.ritesh.lld.stock_treding.Entity.OrderStatus;
import com.ritesh.lld.stock_treding.Entity.Stock;
import com.ritesh.lld.stock_treding.Entity.Trade;
import com.ritesh.lld.stock_treding.Entity.User;
import com.ritesh.lld.stock_treding.Repository.TradeHistoryRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Shared buy-execution logic used by all order strategies.
 * Strategies must not call back into OrderService.
 */
public class OrderExecutionService {

    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final TradeHistoryRepository tradeHistoryRepository;

    public OrderExecutionService(TradeHistoryRepository tradeHistoryRepository) {
        this.tradeHistoryRepository = tradeHistoryRepository;
    }

    /**
     * Debits available balance and finalizes a BUY execution.
     * Idempotent: already-EXECUTED orders are ignored.
     */
    public void executeBuyFromAvailable(User user, Stock stock, Order order, BigDecimal executionPrice) {
        if (order.getStatus() == OrderStatus.EXECUTED) {
            return;
        }
        BigDecimal cost = costOf(executionPrice, order.getQuantity());
        user.getAccount().debitAvailable(cost);
        finalizeBuy(user, stock, order, executionPrice);
    }

    /**
     * Consumes previously reserved funds (refunding unused reservation) and finalizes a BUY.
     * Idempotent: already-EXECUTED orders are ignored.
     */
    public void executeBuyFromReserved(User user, Stock stock, Order order, BigDecimal executionPrice) {
        if (order.getStatus() == OrderStatus.EXECUTED) {
            return;
        }
        BigDecimal reserved = order.getReservedAmount();
        if (reserved.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Order has no reserved funds to consume: " + order.getOrderId());
        }
        BigDecimal cost = costOf(executionPrice, order.getQuantity());
        user.getAccount().consumeReserved(reserved, cost);
        order.clearReservedAmount();
        finalizeBuy(user, stock, order, executionPrice);
    }

    private void finalizeBuy(User user, Stock stock, Order order, BigDecimal executionPrice) {
        if (order.getStatus() == OrderStatus.EXECUTED) {
            return;
        }

        BigDecimal price = executionPrice.setScale(MONEY_SCALE, MONEY_ROUNDING);
        order.markExecuted(price);
        user.getPortfolio().addBuy(stock.getStockId(), order.getQuantity(), price);

        Trade trade = new Trade(
                UUID.randomUUID().toString(),
                order.getOrderId(),
                user.getUserId(),
                stock.getStockId(),
                order.getQuantity(),
                price,
                OrderSide.BUY
        );
        tradeHistoryRepository.addTrade(user.getUserId(), trade);
    }

    private static BigDecimal costOf(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity)).setScale(MONEY_SCALE, MONEY_ROUNDING);
    }
}
