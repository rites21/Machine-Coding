package com.ritesh.lld.AmazonAlexa.Entity;

import com.ritesh.lld.AmazonAlexa.ChargingStrategies.ChargeStrategy;

public class Alexa {
    private final Battery battery;
    private final ChargeStrategy charging;

    public Alexa(Battery battery, ChargeStrategy chargeStrategy) {
        this.battery = battery;
        this.charging = chargeStrategy;
    }

    public void show(){
        if (charging.charge()){
            System.out.printf("charging");
        }

        if(battery.hasBattery()){
            System.out.println("Battery: " + battery.getPercentage() + "%");
        }else {
            System.out.println("Battery not available");
        }
    }
}
