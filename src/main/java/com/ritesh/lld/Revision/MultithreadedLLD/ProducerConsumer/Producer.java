package com.ritesh.lld.Revision.MultithreadedLLD.ProducerConsumer;

import java.util.Queue;

public class Producer implements Runnable {
    private final BlockingQueue queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        produce();
    }

    private void produce() {
        Queue q = queue.getQueue();
        int cap = queue.getCap();
        while (true) {
            synchronized (queue) {
                while (q.size() == cap) {
                    try {
                        q.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                q.add(BlockingQueue.counter++);
                System.out.println("Produced " + BlockingQueue.counter);

                q.notifyAll();
            }
        }
    }
}
