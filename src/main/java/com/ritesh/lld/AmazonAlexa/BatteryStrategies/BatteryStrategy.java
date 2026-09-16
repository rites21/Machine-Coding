package com.ritesh.lld.AmazonAlexa.BatteryStrategies;

public interface BatteryStrategy {
    public boolean hasBattery();
    int getPercentage();
}
