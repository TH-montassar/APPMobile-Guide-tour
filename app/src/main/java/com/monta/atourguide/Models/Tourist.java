package com.monta.atourguide.Models;

public class Tourist {
    private String name, email,id,image;
    private int number;


    public Tourist() {
    }

    public Tourist(String name, String email, int number) {
        this.name = name;
        this.email = email;
        this.number = number;
    }

    public Tourist(String name, String email, int number, String id) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.id = id;

    }

    public Tourist(String name, String email, int number, String id, String image) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.id = id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
