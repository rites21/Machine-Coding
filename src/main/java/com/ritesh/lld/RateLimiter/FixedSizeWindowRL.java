package com.ritesh.lld.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class FixedSizeWindowRL implements RateLimiter {
    ConcurrentHashMap<Integer, Window> map = new ConcurrentHashMap<>();
    public final int limit = 10;
    private final long windowSizeMs = 60 * 1000;

    static class Window {
        long startTime;
        int token;

        private Window(int token, long startTime) {
            this.token = token;
            this.startTime = startTime;
        }
    }

    @Override
    public boolean allowRequest(Integer userId) {
        long now = System.currentTimeMillis();
        map.putIfAbsent(userId, new Window(0, now));
        Window window = map.get(userId);

        synchronized (window) {
            if (now - window.startTime >= windowSizeMs) {
                window.startTime = now;
                window.token = 1;
                return true;
            }

            if (window.token < limit) {
                window.token++;
                return true;
            }
        }
        return false;
    }
}
