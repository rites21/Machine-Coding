package com.ritesh.lld.AmazonAlexa.BatteryStrategies;

public class WithoutBattery implements BatteryStrategy{
    @Override
    public boolean hasBattery() {
        return false;
    }
    @Override
    public int getPercentage() {
        throw new UnsupportedOperationException("No battery");
    }
}
