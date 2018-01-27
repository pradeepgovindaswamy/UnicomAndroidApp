package com.makeathon.enable.simplechatapp;

/**
 * Created by lakapoor on 12/12/17.
 */

public class Message {
    String phone;
    String msg;
    String date;
    public Message() {

    }


    public Message(String phone, String msg, String date) {
        this.phone = phone;
        this.msg = msg;
        this.date = date;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
