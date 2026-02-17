package com.ritesh.lld.Revision.MultithreadedLLD.TrafficLightSimulator;

public class TrafficLightController {
    private final Object lock = new Object();
    private Direction current = Direction.NORTH;

    public void runLight(Direction myDirection) throws InterruptedException {
        while (true){
            synchronized (lock){
                while (current != myDirection){
                    lock.wait();
                }
                System.out.println(myDirection+" "  + "Green");
                current = nextDirection(current);
                lock.notifyAll();
            }
            sleep(2000);
        }
    }

    private Direction nextDirection(Direction direction) {
        switch (direction) {
            case NORTH: return Direction.EAST;
            case EAST: return Direction.SOUTH;
            case SOUTH: return Direction.WEST;
            default: return Direction.NORTH;
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
