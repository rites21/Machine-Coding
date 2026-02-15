package com.ritesh.lld.Multithreding.FizzBuzz;

public class Main {
    public static void main(String[] args) {
        FIZZBUZZ fizzbuzz = new FIZZBUZZ(50);

        Thread fizzThread = new Thread(()->{
            try {
                fizzbuzz.fizz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread buzzThread = new Thread(()->{
            try {
                fizzbuzz.buzz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread fizzBuzzThread = new Thread(()->{
            try {
                fizzbuzz.fizzBuzz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread numberThread = new Thread(()->{
            try {
                fizzbuzz.number();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        fizzThread.start();
        buzzThread.start();
        fizzBuzzThread.start();
        numberThread.start();
    }
}
