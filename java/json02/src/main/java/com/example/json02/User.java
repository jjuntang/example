package com.example.json02;

import lombok.Data;

//@Data
public class User {
    public int id;
    public String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}