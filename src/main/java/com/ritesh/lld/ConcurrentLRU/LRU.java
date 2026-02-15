package com.ritesh.lld.ConcurrentLRU;

import java.util.HashMap;
import java.util.Map;

public class LRU<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    public LRU(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        if (!map.containsKey(key)) return null;

        Node<K, V> node = map.get(key);
        remove(node);
        insert(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }

        if (map.size() == capacity) {
            Node<K, V> lru = head.next;
            remove(lru);
        }

        insert(new Node<>(key, value));
    }

    private void insert(Node<K, V> node) {
        map.put(node.key, node);

        Node<K, V> prevLast = tail.prev;

        prevLast.next = node;
        node.prev = prevLast;

        node.next = tail;
        tail.prev = node;
    }

    private void remove(Node<K, V> node) {
        map.remove(node.key);

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
