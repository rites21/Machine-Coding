package com.ritesh.lld.AmazonAlexa.BatteryStrategies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class withBattery implements BatteryStrategy{
    private int percentage;
    @Override
    public boolean hasBattery() {
        return true;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }
}
