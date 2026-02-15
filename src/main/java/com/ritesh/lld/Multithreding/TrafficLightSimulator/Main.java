package com.ritesh.lld.Multithreding.TrafficLightSimulator;

import static com.ritesh.lld.Multithreding.TrafficLightSimulator.TrafficLightBasic.car;
import static com.ritesh.lld.Multithreding.TrafficLightSimulator.TrafficLightBasic.eastGreen;
import static com.ritesh.lld.Multithreding.TrafficLightSimulator.TrafficLightBasic.intersection;
import static com.ritesh.lld.Multithreding.TrafficLightSimulator.TrafficLightBasic.northGreen;
import static com.ritesh.lld.Multithreding.TrafficLightSimulator.TrafficLightBasic.southGreen;
import static com.ritesh.lld.Multithreding.TrafficLightSimulator.TrafficLightBasic.westGreen;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Thread controller = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("\n🟢 NORTH / SOUTH GREEN");
                    northGreen.release();
                    southGreen.release();

                    Thread.sleep(4000);
                    northGreen.drainPermits();
                    southGreen.drainPermits();

                    System.out.println("\n🟢 EAST / WEST GREEN");
                    eastGreen.release();
                    westGreen.release();
                    Thread.sleep(4000);
                    eastGreen.drainPermits();
                    westGreen.drainPermits();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        controller.setDaemon(true);
        controller.start();

        new Thread(() -> car("North Car", northGreen)).start();
        new Thread(() -> car("South Car", southGreen)).start();
        new Thread(() -> car("East Car",  eastGreen)).start();
        new Thread(() -> car("West Car",  westGreen)).start();
    }

}
