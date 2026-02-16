package com.ritesh.lld.ConcurrentLRU;

interface PersistenceStore<K, V> {
    void save(K key, V value);
    void delete(K key);
}
