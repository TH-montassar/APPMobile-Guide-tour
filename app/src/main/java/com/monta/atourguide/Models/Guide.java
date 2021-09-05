package com.monta.atourguide.Models;

public class Guide {
    private String name, fullname, email, city, description,descriptionDetails, date, sex;

    private Integer age,number;
    private String imgGd;
    private Float price;

    public Guide() {
    }

    public Guide(String name, String fullname, String email, String city, Integer number) {
        this.name = name;
        this.fullname = fullname;
        this.email = email;
        this.city = city;
        this.number = number;
    }

    public Guide(String name, String fullname, String email, String city, String description, String descriptionDetails, String date, String sex, Integer age, Integer number, Float price) {
        this.name = name;
        this.fullname = fullname;
        this.email = email;

        this.city = city;
        this.description = description;
        this.descriptionDetails = descriptionDetails;
        this.date = date;
        this.sex = sex;
        this.age = age;
        this.number = number;

        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionDetails() {
        return descriptionDetails;
    }

    public void setDescriptionDetails(String descriptionDetails) {
        this.descriptionDetails = descriptionDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getImgGd() {
        return imgGd;
    }

    public void setImgGd(String imgGd) {
        this.imgGd = imgGd;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
