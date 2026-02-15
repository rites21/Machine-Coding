package com.ritesh.lld.Multithreding.FizzBuzz;

//package com.ritesh.lld.Multithreding.FizzBuzz;
    
public class FIZZBUZZ {
    private int n;
    private int current = 1;

    public FIZZBUZZ(int n) {
        this.n = n;
    }

    public synchronized void fizz() throws InterruptedException {
        while (current <= n) {
            while (current <= n && !(current % 3 == 0 && current % 5 != 0)) {
                wait();
            }
            if (current > n) return;

            System.out.println("Fizz " + Thread.currentThread().getName());
            current++;
            notifyAll();
        }
    }

    public synchronized void buzz() throws InterruptedException {
        while (current <= n) {
            while (current <= n && !(current % 5 == 0 && current % 3 != 0)) {
                wait();
            }
            if (current > n) return;

            System.out.println("Buzz " + Thread.currentThread().getName());
            current++;
            notifyAll();
        }
    }

    public synchronized void fizzBuzz() throws InterruptedException {
        while (current <= n) {
            while (current <= n && !(current % 15 == 0)) {
                wait();
            }
            if (current > n) return;

            System.out.println("FizzBuzz " + Thread.currentThread().getName());
            current++;
            notifyAll();
        }
    }

    public synchronized void number() throws InterruptedException {
        while (current <= n) {
            while (current <= n && (current % 3 == 0 || current % 5 == 0)) {
                wait();
            }
            if (current > n) return;

            System.out.println(current + " " + Thread.currentThread().getName());
            current++;
            notifyAll();
        }
    }
}