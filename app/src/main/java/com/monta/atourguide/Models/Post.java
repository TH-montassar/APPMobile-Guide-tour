package com.monta.atourguide.Models;

public class Post {

    private String title, desc;
    private String imagePub;
    private String iduser;
    private String idpost;





    public Post() {
    }

    public Post(String title, String desc, String imagePub, String iduser,String idpost) {
        this.title = title;
        this.desc = desc;
        this.imagePub = imagePub;
        this.iduser = iduser;
        this.idpost = idpost;
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

    public String getIdpost() {
        return idpost;
    }

    public void setIdpost(String idpost) {
        this.idpost = idpost;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
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
