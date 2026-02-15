package com.ritesh.lld.Multithreding.NumberPrinter;

public class NumberPrinterAlt {

    private static final int max = 100;
    private static int counter = 1;
    private static final Object lock = new Object();

    static class NumberSeqPrinter extends Thread{
        final int tid;
        final int totalthread;

        public NumberSeqPrinter(int tid, int totalthread) {
            this.tid = tid;
            this.totalthread = totalthread;
        }

        @Override
        public void run() {
            super.run();
            while (true){
                synchronized (lock){
                    if(counter>max){
                        return;
                    }
                    if ((counter-1) % totalthread == tid){
                        System.out.println(counter+" " + Thread.currentThread().getName());
                        counter++;
                    }
                }
            }
        }
    }
}



