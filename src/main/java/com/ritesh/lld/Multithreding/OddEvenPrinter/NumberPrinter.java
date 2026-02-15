package com.ritesh.lld.Multithreding.OddEvenPrinter;

import java.util.concurrent.locks.Lock;

public class NumberPrinter {
    public static int counter = 1;
    public static int max = 100;
    private static Object lock = new Object();

    static class OddEvenPrinter{
        public void evenPrinter() throws InterruptedException {
            while (counter < max){
                if(counter%2==1){
                    notifyAll();
                }
                System.out.println(counter+" "+Thread.currentThread().getName());
                counter++;
                wait();
            }

        }

        public  void oddPrinter() throws InterruptedException {
            while (counter < max){
                if(counter%2==0){
                    notifyAll();
                }

                System.out.println(counter+" "+Thread.currentThread().getName());
                counter++;
               wait();
            }


        }
    }
}
