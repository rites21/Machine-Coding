package com.ritesh.lld.stock_treding.Repository;

import com.ritesh.lld.stock_treding.Entity.Trade;
import com.ritesh.lld.stock_treding.Entity.TradeHistory;

import java.util.HashMap;
import java.util.Map;

public class TradeHistoryRepository {
    private final Map<String, TradeHistory> historyByUser = new HashMap<>();

    public TradeHistory getOrCreate(String userId) {
        return historyByUser.computeIfAbsent(userId, TradeHistory::new);
    }

    public void addTrade(String userId, Trade trade) {
        getOrCreate(userId).addTrade(trade);
    }

    public TradeHistory findByUserId(String userId) {
        return historyByUser.get(userId);
    }
}
