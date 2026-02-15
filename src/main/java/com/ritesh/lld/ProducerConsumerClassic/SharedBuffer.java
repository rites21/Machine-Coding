package com.ritesh.lld.ProducerConsumerClassic;

import java.util.LinkedList;
import java.util.Queue;

public class SharedBuffer {
    private Queue<Integer> queue = new LinkedList<>();
    private int capacity = 3;

    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == capacity){
            wait();
        }
        queue.add(item);
        System.out.println("produced"+  item);
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()){
            wait();
        }
        int item = queue.poll();
        System.out.println("consumed"+ item);
        notifyAll();
        return item;
    }
}
