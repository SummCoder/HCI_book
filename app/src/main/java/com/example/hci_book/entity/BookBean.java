package com.example.hci_book.entity;

/**
 * @author SummCoder
 * @date 2023/12/19 23:28
 */
public class BookBean {

    public int id;
    public String cover;
    public String bookName;
    public String author;
    public String bookContent;
    public BookBean(int id, String cover, String bookName, String author, String bookContent){
        this.id = id;
        this.cover = cover;
        this.bookName = bookName;
        this.author = author;
        this.bookContent = bookContent;
    }
    public BookBean(){};
}
