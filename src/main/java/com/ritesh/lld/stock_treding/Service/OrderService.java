package com.ritesh.lld.stock_treding.Service;

import com.ritesh.lld.stock_treding.Entity.Order;
import com.ritesh.lld.stock_treding.Entity.OrderSide;
import com.ritesh.lld.stock_treding.Entity.OrderType;
import com.ritesh.lld.stock_treding.Entity.Stock;
import com.ritesh.lld.stock_treding.Entity.User;
import com.ritesh.lld.stock_treding.OrderStrategyFactory;
import com.ritesh.lld.stock_treding.Repository.OrderRepository;
import com.ritesh.lld.stock_treding.Repository.StockRepository;
import com.ritesh.lld.stock_treding.Repository.UserRepository;
import com.ritesh.lld.stock_treding.Strategies.OrderSelectStrategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderService {

    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final OrderStrategyFactory orderStrategyFactory;

    public OrderService(UserRepository userRepository,
                        StockRepository stockRepository,
                        OrderRepository orderRepository,
                        OrderStrategyFactory orderStrategyFactory) {
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.orderStrategyFactory = orderStrategyFactory;
    }

    /**
     * Place a BUY order (MARKET or LIMIT).
     * limitPrice is required for LIMIT; ignored for MARKET.
     */
    public Order placeBuyOrder(String userId, String stockId, int quantity,
                               OrderType orderType, BigDecimal limitPrice) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + stockId));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (orderType == null) {
            throw new IllegalArgumentException("Order type is required");
        }

        Order order = new Order(
                UUID.randomUUID().toString(),
                userId,
                stockId,
                quantity,
                OrderSide.BUY,
                orderType,
                orderType == OrderType.LIMIT ? limitPrice : null
        );

        OrderSelectStrategy strategy = orderStrategyFactory.getOrderSelectStrategy(orderType);
        strategy.buyStock(user, stock, order);

        orderRepository.save(order);
        return order;
    }

    public Order placeBuyOrder(String userId, String stockId, int quantity,
                               OrderType orderType, Double limitPrice) {
        BigDecimal price = limitPrice == null ? null : BigDecimal.valueOf(limitPrice);
        return placeBuyOrder(userId, stockId, quantity, orderType, price);
    }

    /**
     * Re-evaluates PENDING BUY orders for a stock after its price changes.
     * Eligible LIMIT buys execute once; others remain PENDING.
     */
    public void processPendingOrders(String stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + stockId));

        List<Order> pendingOrders = orderRepository.findPendingByStockId(stockId);
        for (Order order : pendingOrders) {
            if (order.getSide() != OrderSide.BUY) {
                continue;
            }

            User user = userRepository.findById(order.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "User not found for order: " + order.getOrderId()));

            OrderSelectStrategy strategy = orderStrategyFactory.getOrderSelectStrategy(order.getType());
            strategy.buyStock(user, stock, order);
            orderRepository.save(order);
        }
    }
}
