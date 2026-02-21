package com.ritesh.lld.BookMyShow.Entity;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String userName;

    public User(int userId, String name) {
    }
}
