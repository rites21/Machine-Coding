package com.ritesh.lld.Elevetor_System.Entity.Entity;

import java.util.PriorityQueue;

import lombok.Data;

@Data
public class Elevator {
    private int id;
    private int currentFloor;
    private Direction direction;
    private final PriorityQueue<Request> upStops = new PriorityQueue<>((r1, r2) -> r1.getFloorNUmber() - r2.getFloorNUmber());
    //min heap go up by min to max
    private final PriorityQueue<Request> downStops = new PriorityQueue<>(((r1, r2) -> r2.getFloorNUmber() - r1.getFloorNUmber()));
    //maxheap go down by max to min

    public void addStop(Request request) {
        if (request.getDirection().equals(Direction.UP)) {
            upStops.add(request);
        } else if (request.getDirection().equals(Direction.DOWN)) {
            downStops.add(request);
        }
    }
}
