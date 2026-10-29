package com.ritesh.lld.stock_treding;

import com.ritesh.lld.stock_treding.Entity.OrderType;
import com.ritesh.lld.stock_treding.Service.OrderExecutionService;
import com.ritesh.lld.stock_treding.Strategies.LimitOrderBuyStrategy;
import com.ritesh.lld.stock_treding.Strategies.MarketOrderBuyStrategy;
import com.ritesh.lld.stock_treding.Strategies.OrderSelectStrategy;

import java.util.EnumMap;
import java.util.Map;

/**
 * Factory / registry that returns the BUY strategy for a given OrderType.
 * Strategies are created once and reused.
 */
public class OrderStrategyFactory {

    private final Map<OrderType, OrderSelectStrategy> strategies;

    public OrderStrategyFactory(OrderExecutionService orderExecutionService) {
        strategies = new EnumMap<>(OrderType.class);
        strategies.put(OrderType.MARKET, new MarketOrderBuyStrategy(orderExecutionService));
        strategies.put(OrderType.LIMIT, new LimitOrderBuyStrategy(orderExecutionService));
    }

    public OrderSelectStrategy getOrderSelectStrategy(OrderType orderType) {
        OrderSelectStrategy strategy = strategies.get(orderType);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported order type: " + orderType);
        }
        return strategy;
    }
}
