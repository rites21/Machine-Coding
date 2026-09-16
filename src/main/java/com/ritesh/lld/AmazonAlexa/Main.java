package com.ritesh.lld.AmazonAlexa;

import com.ritesh.lld.AmazonAlexa.BatteryStrategies.WithoutBattery;
import com.ritesh.lld.AmazonAlexa.BatteryStrategies.withBattery;
import com.ritesh.lld.AmazonAlexa.ChargingStrategies.ChargeDisable;
import com.ritesh.lld.AmazonAlexa.ChargingStrategies.ChargingEnable;
import com.ritesh.lld.AmazonAlexa.Entity.Alexa;
import com.ritesh.lld.AmazonAlexa.Entity.Battery;

public class Main {
    public static void main(String[] args) {
        Alexa alexa1 = new Alexa(
                new Battery(new WithoutBattery()),
                new ChargingEnable()
        );
        alexa1.show();

        Alexa alexa2 = new Alexa(
                new Battery(new withBattery(80)),
                new ChargingEnable()
        );
        alexa2.show();


        Alexa alexa3 = new Alexa(
                new Battery(new withBattery(80)),
                new ChargeDisable()
        );
        alexa3.show();


        Alexa alexa4 = new Alexa(
                new Battery(new WithoutBattery()),
                new ChargeDisable()
        );
        alexa4.show();

//        System.out.println(alexa1);
//        System.out.println(alexa2);
//        System.out.println(alexa3);
//        System.out.println(alexa4);

    }
}
