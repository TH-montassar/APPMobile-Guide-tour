package com.monta.atourguide.Models;

public class Post {

    private String title, desc;
    private String imagePub;

    //id,auhore


    public Post() {
    }

    public Post(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public Post(String title, String desc, String imagePub) {
        this.title = title;
        this.desc = desc;
        this.imagePub = imagePub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePub() {
        return imagePub;
    }

    public void setImagePub(String imagePub) {
        this.imagePub = imagePub;
    }
}
