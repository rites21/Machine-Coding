package com.ritesh.lld.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRL implements RateLimiter {
    long refillRatePerSec; //itna refill per sec
    long bucketSize; //maxCap

    public TokenBucketRL(long refillRate, long bucketSize) {
        this.refillRatePerSec = refillRate;
        this.bucketSize = bucketSize;
    }

    static class Bucket {
        long lastRefillTime;
        double token;


        public Bucket(long lastRefillTime, double token) {
            this.lastRefillTime = lastRefillTime;
            this.token = token;
        }
    }

    ConcurrentHashMap<Integer, Bucket> map = new ConcurrentHashMap<>();


    @Override
    public boolean allowRequest(Integer userId) {
        long now = System.currentTimeMillis();
        map.putIfAbsent(userId, new Bucket(now, 0L));
        Bucket bucket = map.get(userId);
        synchronized (bucket) {
            long elapsedMs = now - bucket.lastRefillTime;

            double tokensToAdd = (elapsedMs / 1000.0) * refillRatePerSec;
            bucket.token = Math.min(bucketSize, bucket.token + tokensToAdd);


            bucket.lastRefillTime = now;
            if (bucket.token >= 1) {
                bucket.token -= 1;
                return true;
            }
        }
        return false;
    }
}
