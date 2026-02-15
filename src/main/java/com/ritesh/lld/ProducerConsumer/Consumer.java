package com.ritesh.lld.ProducerConsumer;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer item = null;

                item = (Integer) queue.take();
                System.out.println("consumed item " + item);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
