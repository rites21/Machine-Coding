package com.ritesh.lld.Multithreding.NumberPrinter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumberPrinter extends Thread{
    int start;
    int end;

    public NumberPrinter (int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        while(start<=end){
            System.out.println(start+" "+ Thread.currentThread().getName());
            start++;
        }

    }
}

