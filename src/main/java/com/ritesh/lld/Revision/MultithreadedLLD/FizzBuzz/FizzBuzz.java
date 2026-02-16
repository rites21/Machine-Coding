package com.ritesh.lld.Revision.MultithreadedLLD.FizzBuzz;

import java.util.concurrent.locks.ReentrantLock;

public class FizzBuzz {
    int n;
    public static int counter = 1;
    //    ReentrantLock lock = new ReentrantLock();
    private Object lock = new Object();

    public FizzBuzz(int n) {
        this.n = n;
    }

    synchronized void fizzPrinter() {
        //multiplie by 3
        while (counter < n && !(counter % 3 == 0 && counter % 5 != 0)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        System.out.println(counter + Thread.currentThread().getName());
        counter++;
        this.notifyAll();
    }

    synchronized void buzzzzPrinter() {  //multiplie by 5
        while (counter < n && !(counter % 5 == 0 && counter % 3 != 0)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        System.out.println(counter + Thread.currentThread().getName());
        counter++;
        this.notifyAll();
    }

    synchronized void fizzBuzzPrinter() {  //multiplie by 3
        while (counter < n && !(counter % 15 == 0)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        System.out.println(counter + Thread.currentThread().getName());
        counter++;
        this.notifyAll();
    }

    synchronized void numberPrinter() {   //multiplie by 3
        while (counter < n && (counter % 3 != 0 || counter % 5 != 0)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        System.out.println(counter + Thread.currentThread().getName());
        counter++;
        this.notifyAll();
    }
}
