package com.ritesh.lld.Revision.MultithreadedLLD.ProducerConsumer;

public class Main {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue(4);
        Producer producer =  new Producer(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        t1.start();
        t2.start();
    }
}
