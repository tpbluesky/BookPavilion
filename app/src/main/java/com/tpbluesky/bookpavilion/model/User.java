package com.tpbluesky.bookpavilion.model;

/**
 * Created by tpbluesky on 2017/2/13.
 */

public class User {
    private String name;
    private int age;

    public User() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public int getAge() {
        return age;
    }
}
