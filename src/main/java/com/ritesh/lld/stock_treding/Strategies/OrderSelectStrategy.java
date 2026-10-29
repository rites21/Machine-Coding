package com.ritesh.lld.stock_treding.Strategies;

import com.ritesh.lld.stock_treding.Entity.Order;
import com.ritesh.lld.stock_treding.Entity.Stock;
import com.ritesh.lld.stock_treding.Entity.User;

/**
 * Strategy for processing a BUY order of a specific OrderType.
 *
 * @return true if the order was executed; false if left PENDING
 */
public interface OrderSelectStrategy {
    boolean buyStock(User user, Stock stock, Order order);
}
