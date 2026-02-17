package com.ritesh.lld.Revision.MultithreadedLLD.TrafficLightSimulator;

public class Main {
    public static void main(String[] args) {
        TrafficLightController controller = new TrafficLightController();

        for (Direction dir : Direction.values()) {
            new Thread(() -> {
                try {
                    controller.runLight(dir);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
