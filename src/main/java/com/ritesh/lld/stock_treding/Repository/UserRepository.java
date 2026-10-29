package com.ritesh.lld.stock_treding.Repository;

import com.ritesh.lld.stock_treding.Entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getUserId(), user);
    }

    public Optional<User> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }
}
