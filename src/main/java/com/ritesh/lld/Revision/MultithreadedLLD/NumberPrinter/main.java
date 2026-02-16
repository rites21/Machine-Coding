package com.ritesh.lld.Revision.MultithreadedLLD.NumberPrinter;

public class main {
    public static void main(String[] args) {
        NumberPrinter numberPrinter = new NumberPrinter(20);
        Thread t1 = new Thread(() -> {
            try {
                numberPrinter.numberPrinter(0,3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                numberPrinter.numberPrinter(1,3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                numberPrinter.numberPrinter(2,3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
