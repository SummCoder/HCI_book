package com.example.hci_book.entity;

/**
 * @author SummCoder
 * @date 2023/12/22 15:59
 * 共读交流活动实体类
 */
public class Communication {

    public int id;
    public String title;
    public String communicationDate;
    public String cover;
    public String content;
    public Communication(int id, String title, String date, String cover, String content){
        this.id = id;
        this.title = title;
        this.communicationDate = date;
        this.cover = cover;
        this.content = content;
    }

    public Communication() {

    }
}
