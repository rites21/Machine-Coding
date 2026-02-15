package com.ritesh.lld.Multithreding.NumberPrinter;
import com.ritesh.lld.Multithreding.NumberPrinter.NumberPrinterAlt.NumberSeqPrinter;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//       NumberPrinter numberPrinter = new NumberPrinter(1,30);
//        NumberPrinter numberPrinter2 = new NumberPrinter(31,60);
//        NumberPrinter numberPrinter3 = new NumberPrinter(61,100);
//
//        numberPrinter.start();
//        numberPrinter2.start();
//        numberPrinter3.start();
//
//        numberPrinter.join();
//        numberPrinter2.join();
//        numberPrinter3.join();

        Thread t1 = new NumberSeqPrinter(0,3);
        Thread t2 = new NumberSeqPrinter(1,3);
        Thread t3 = new NumberSeqPrinter(2,3);

        t1.start();
        t2.start();
        t3.start();
    }
}
