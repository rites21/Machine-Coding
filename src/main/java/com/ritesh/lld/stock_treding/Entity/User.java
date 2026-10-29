package com.ritesh.lld.stock_treding.Entity;

public class User {
    private String userId;
    private String name;
    private Account account;
    private Portfolio portfolio;

    public User(String userId, String name, Account account, Portfolio portfolio) {
        this.userId = userId;
        this.name = name;
        this.account = account;
        this.portfolio = portfolio;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}
