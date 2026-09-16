package com.ritesh.lld.AmazonAlexa.ChargingStrategies;

public class ChargeDisable implements ChargeStrategy{
    @Override
    public boolean charge() {
        return false;
    }
}
