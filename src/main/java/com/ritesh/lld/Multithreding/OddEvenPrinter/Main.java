package com.ritesh.lld.Multithreding.OddEvenPrinter;
import com.ritesh.lld.Multithreding.OddEvenPrinter.NumberPrinter.OddEvenPrinter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        OddEvenPrinter oddEvenPrinter = new OddEvenPrinter();
        Thread evenTread = new Thread(() -> {
            try {
                oddEvenPrinter.evenPrinter();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread oddThread = new Thread(() -> {
            try {
                oddEvenPrinter.oddPrinter();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        evenTread.start();
        oddThread.start();
        evenTread.join();
        oddThread.join();
    }
}
