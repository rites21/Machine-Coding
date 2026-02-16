package com.ritesh.lld.Revision.MultithreadedLLD.ProducerConsumer;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.Data;

@Data
public class BlockingQueue {
    private  Queue<Integer> queue;
    private  int cap;
    static int counter;

    public BlockingQueue(int cap) {
        this.cap = cap;
        queue = new ArrayDeque<>();
    }
}
