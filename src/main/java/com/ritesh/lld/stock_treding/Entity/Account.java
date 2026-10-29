package com.ritesh.lld.stock_treding.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private final String accountId;
    private BigDecimal availableBalance;
    private BigDecimal reservedBalance;

    public Account(String accountId, BigDecimal availableBalance) {
        this.accountId = accountId;
        this.availableBalance = normalize(availableBalance);
        this.reservedBalance = BigDecimal.ZERO.setScale(MONEY_SCALE, MONEY_ROUNDING);
    }

    public Account(String accountId, double availableBalance) {
        this(accountId, BigDecimal.valueOf(availableBalance));
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public BigDecimal getReservedBalance() {
        return reservedBalance;
    }

    public void addFunds(BigDecimal amount) {
        requirePositive(amount, "amount");
        availableBalance = availableBalance.add(normalize(amount));
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return availableBalance.compareTo(normalize(amount)) >= 0;
    }

    public void debitAvailable(BigDecimal amount) {
        BigDecimal normalized = normalize(amount);
        requireNonNegative(normalized, "amount");
        if (!hasSufficientBalance(normalized)) {
            throw new IllegalStateException(
                    "Insufficient available balance. Required: " + normalized
                            + ", Available: " + availableBalance);
        }
        availableBalance = availableBalance.subtract(normalized);
    }

    public void reserve(BigDecimal amount) {
        BigDecimal normalized = normalize(amount);
        requirePositive(normalized, "reserve amount");
        if (!hasSufficientBalance(normalized)) {
            throw new IllegalStateException(
                    "Insufficient balance to reserve: " + normalized
                            + ", Available: " + availableBalance);
        }
        availableBalance = availableBalance.subtract(normalized);
        reservedBalance = reservedBalance.add(normalized);
    }

    public void releaseReserved(BigDecimal amount) {
        BigDecimal normalized = normalize(amount);
        requirePositive(normalized, "release amount");
        if (reservedBalance.compareTo(normalized) < 0) {
            throw new IllegalStateException("Cannot release more than reserved");
        }
        reservedBalance = reservedBalance.subtract(normalized);
        availableBalance = availableBalance.add(normalized);
    }

    /**
     * Consumes reserved funds for an actual execution cost and refunds any unused reserved amount.
     */
    public void consumeReserved(BigDecimal reservedAmount, BigDecimal actualCost) {
        BigDecimal reserved = normalize(reservedAmount);
        BigDecimal cost = normalize(actualCost);
        requirePositive(reserved, "reservedAmount");
        requireNonNegative(cost, "actualCost");

        if (reservedBalance.compareTo(reserved) < 0) {
            throw new IllegalStateException(
                    "Insufficient reserved balance. Required: " + reserved
                            + ", Reserved: " + reservedBalance);
        }
        if (cost.compareTo(reserved) > 0) {
            throw new IllegalStateException(
                    "Actual cost " + cost + " exceeds reserved amount " + reserved);
        }

        reservedBalance = reservedBalance.subtract(reserved);
        BigDecimal refund = reserved.subtract(cost);
        if (refund.compareTo(BigDecimal.ZERO) > 0) {
            availableBalance = availableBalance.add(refund);
        }
    }

    private static BigDecimal normalize(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Money value cannot be null");
        }
        return value.setScale(MONEY_SCALE, MONEY_ROUNDING);
    }

    private static void requirePositive(BigDecimal amount, String label) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(label + " must be positive");
        }
    }

    private static void requireNonNegative(BigDecimal amount, String label) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(label + " cannot be negative");
        }
    }
}
