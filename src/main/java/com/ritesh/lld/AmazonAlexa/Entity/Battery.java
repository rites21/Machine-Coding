package com.ritesh.lld.AmazonAlexa.Entity;

import com.ritesh.lld.AmazonAlexa.BatteryStrategies.BatteryStrategy;

public class Battery {
    private final BatteryStrategy strategy;

    public Battery(BatteryStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean hasBattery() {
        return strategy.hasBattery();
    }

    public int getPercentage() {
        return strategy.getPercentage();
    }
}
