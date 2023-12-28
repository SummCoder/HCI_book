package com.example.hci_book.entity;

/**
 * @author SummCoder
 * @date 2023/12/21 21:37
 */
public class Comment {

    public int id;
    public int bookId;
    public String username;
    public String commentContent;

    public Comment(int bookId, String username, String commentContent){
        this.bookId = bookId;
        this.username = username;
        this.commentContent = commentContent;
    }
    public Comment(){};

}
