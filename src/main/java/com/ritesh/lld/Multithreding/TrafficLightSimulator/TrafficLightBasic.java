package com.ritesh.lld.Multithreding.TrafficLightSimulator;

import java.util.concurrent.Semaphore;

public class TrafficLightBasic {
     static final Semaphore intersection = new Semaphore(1,true);
     protected static final Semaphore northGreen = new Semaphore(0,true);
     static final Semaphore southGreen = new Semaphore(0,true);
     static final Semaphore eastGreen = new Semaphore(0,true);
     static final Semaphore westGreen = new Semaphore(0,true);

    static void car(String name, Semaphore greenLight) {
        try {
            while (true) {

                greenLight.acquire();        // wait for green
                intersection.acquire();     // enter intersection

                System.out.println(name + " is crossing 🚗");
                Thread.sleep(1000);          // crossing time

                intersection.release();     // exit intersection
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
