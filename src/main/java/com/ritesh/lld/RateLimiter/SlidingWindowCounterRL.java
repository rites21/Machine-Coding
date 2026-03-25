package com.ritesh.lld.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounterRL implements RateLimiter {
    private final int limit;
    private final long windowSize;

    public SlidingWindowCounterRL(int limit, long windowSize) {
        this.limit = limit;
        this.windowSize = windowSize;
    }


    static class Window {
        int currentCounter;
        int prevCounter;
        long startTime;


        private Window(int currentCounter, int prevCounter, long startTime) {
            this.currentCounter = currentCounter;
            this.prevCounter = prevCounter;
            this.startTime = startTime;
        }
    }

    ConcurrentHashMap<Integer, Window> map = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(Integer userId) {
        long now = System.currentTimeMillis();
        map.putIfAbsent(userId, new Window(0, 0, now));
        Window window = map.get(userId);
        synchronized (window) {
            long elapsed = now - window.startTime;
            if (elapsed >= windowSize) {
                window.prevCounter = window.currentCounter;
                window.currentCounter = 0;
                window.startTime = now;
            }

            double prevWindowWeight = 1.0 - (now - window.startTime) / (double) windowSize;
            double total = window.prevCounter * prevWindowWeight + window.currentCounter;
            if(total < limit){
                window.currentCounter--;
                return true;
            }

        }

        return false;
    }
}
