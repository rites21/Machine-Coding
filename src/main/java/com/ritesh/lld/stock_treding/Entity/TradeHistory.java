package com.ritesh.lld.stock_treding.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TradeHistory {
    private String userId;
    private List<Trade> trades;

    public TradeHistory(String userId) {
        this.userId = userId;
        this.trades = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public List<Trade> getTrades() {
        return Collections.unmodifiableList(trades);
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
    }
}
