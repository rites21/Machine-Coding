package com.ritesh.lld.stock_treding.Repository;

import com.ritesh.lld.stock_treding.Entity.Stock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StockRepository {
    private final Map<String, Stock> stocks = new HashMap<>();

    public void save(Stock stock) {
        stocks.put(stock.getStockId(), stock);
    }

    public Optional<Stock> findById(String stockId) {
        return Optional.ofNullable(stocks.get(stockId));
    }
}
