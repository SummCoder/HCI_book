package com.example.hci_book.entity;

/**
 * @author SummCoder
 * @date 2023/12/21 14:30
 */
public class User {
    public String username;
    public String password;
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public User(){};
}
