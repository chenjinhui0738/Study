package com.cjh.spring.bean;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;

    public User() {
    }

    public User(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }

}
