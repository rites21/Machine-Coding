package com.ritesh.lld.stock_treding.Repository;

import com.ritesh.lld.stock_treding.Entity.Order;
import com.ritesh.lld.stock_treding.Entity.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public void save(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public List<Order> findByUserId(String userId) {
        return orders.values().stream()
                .filter(o -> o.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Order> findPendingByStockId(String stockId) {
        return orders.values().stream()
                .filter(o -> o.getStockId().equals(stockId))
                .filter(o -> o.getStatus() == OrderStatus.PENDING)
                .collect(Collectors.toList());
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}
