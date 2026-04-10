package com.ritesh.lld.Elevetor_System.Entity.Entity;

import java.util.LinkedList;
import java.util.Queue;

import lombok.Data;

@Data
public class Floor {
    private int floorNumber;
    private Queue<Request> upCall = new LinkedList<>();
    private Queue<Request> downCall = new LinkedList<>();
}
