package com.ritesh.lld.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class LeakyBucketRL implements RateLimiter {

    long leakPerSec;
    long capacity;

    public LeakyBucketRL(long leakPerSec, long capacity) {
        this.leakPerSec = leakPerSec;
        this.capacity = capacity;
    }

    static class LeakyBucket {
        int queueSize;
        long lastLeakTime;


        public LeakyBucket(int queueSize, long lastLeakTime) {
            this.queueSize = queueSize;
            this.lastLeakTime = lastLeakTime;
        }
    }

    ConcurrentHashMap<Integer, LeakyBucket> map = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(Integer userId) {
        long now = System.currentTimeMillis();
        map.putIfAbsent(userId, new LeakyBucket(0, now));
        LeakyBucket bucket = map.get(userId);

        synchronized (bucket) {

            long elapsed = now - bucket.lastLeakTime;

            // correct leak calculation
            int leaked = (int) (elapsed * leakPerSec / 1000);

            if (leaked > 0) {
                bucket.queueSize = Math.max(0, bucket.queueSize - leaked);

                long timeConsumed = (leaked * 1000L) / leakPerSec;
                bucket.lastLeakTime += timeConsumed;
            }

            // add request if capacity available
            if (bucket.queueSize < capacity) {
                bucket.queueSize++;
                return true;
            }

            return false;
        }
    }
}
