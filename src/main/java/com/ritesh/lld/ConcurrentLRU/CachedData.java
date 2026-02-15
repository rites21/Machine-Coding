package com.ritesh.lld.ConcurrentLRU;

import java.sql.Time;

public class CachedData {
    Object key;
    Object value;
    Time time;

    public CachedData(Object key, Object value, Time time) {
        this.key = key;
        this.value = value;
        this.time = time;
    }
}
