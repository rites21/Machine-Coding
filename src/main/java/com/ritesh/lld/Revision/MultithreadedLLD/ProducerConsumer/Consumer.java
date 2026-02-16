package com.ritesh.lld.Revision.MultithreadedLLD.ProducerConsumer;

import java.util.Queue;

public class Consumer implements Runnable {
    private final BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        consume();
    }

    void consume(){
        Queue q = queue.getQueue();
        while (true) {
            synchronized (queue) {
                while (q.isEmpty()) {
                    try {
                        q.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(q.poll() + " Consumed");
                q.notifyAll();
            }
        }
    }
}
