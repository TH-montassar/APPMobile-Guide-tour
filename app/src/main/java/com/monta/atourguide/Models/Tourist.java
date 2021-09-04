package com.monta.atourguide.Models;

public class Tourist {
    private String name, email, number;
    private int image;

    public Tourist() {
    }

    public Tourist(String name, String email, String number) {
        this.name = name;
        this.email = email;
        this.number = number;
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


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
