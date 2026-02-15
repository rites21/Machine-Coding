package com.ritesh.lld.ProducerConsumerClassic;

public class Main {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();
//        new Thread(new Producer(buffer)).start();
//        new Thread(new Consumer(buffer)).start();

        Thread t = new Thread(new Producer(buffer));
        Thread t2 = new Thread(new Consumer(buffer));
        t.start();
        t2.start();

    }
}
