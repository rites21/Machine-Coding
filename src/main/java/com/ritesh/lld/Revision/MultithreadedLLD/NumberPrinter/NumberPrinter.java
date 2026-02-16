package com.ritesh.lld.Revision.MultithreadedLLD.NumberPrinter;

public class NumberPrinter {
    public int num;
    private int counter = 0;
    public Object lock = new Object();


    public NumberPrinter(int num) {
        this.num = num;
    }

    public void numberPrinter(int id, int totalThread) throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (counter <= num && counter % totalThread != id) {
                    lock.wait();
                }

                if (counter > num) {
                    lock.notifyAll();
                    return;
                }
                System.out.println(counter + Thread.currentThread().getName());
                counter++;
                lock.notifyAll();
            }
        }

    }
}
