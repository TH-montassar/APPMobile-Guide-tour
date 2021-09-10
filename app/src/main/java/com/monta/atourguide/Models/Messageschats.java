package com.monta.atourguide.Models;

public class Messageschats {
    private String sender,receiver,messge;

    public Messageschats() {
    }

    public Messageschats(String sender, String receiver, String messge) {
        this.sender = sender;
        this.receiver = receiver;
        this.messge = messge;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }
}
