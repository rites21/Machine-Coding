package com.ritesh.lld.RateLimiter;

public interface RateLimiter {

    public boolean allowRequest(Integer userId);
}
