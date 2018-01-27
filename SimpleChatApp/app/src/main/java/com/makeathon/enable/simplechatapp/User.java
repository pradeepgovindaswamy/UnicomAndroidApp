package com.makeathon.enable.simplechatapp;

/**
 * Created by lakapoor on 11/12/17.
 */

public class User {

    public String name;
    public String phone;
    public String password;
    public String blind;
    public String deaf;
    public String dumb;

    public User(String name, String phone, String password, String blind, String deaf, String dumb) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.blind = blind;
        this.deaf = deaf;
        this.dumb = dumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBlind() {
        return blind;
    }

    public void setBlind(String blind) {
        this.blind = blind;
    }

    public String getDeaf() {
        return deaf;
    }

    public void setDeaf(String deaf) {
        this.deaf = deaf;
    }

    public String getDumb() {
        return dumb;
    }

    public void setDumb(String dumb) {
        this.dumb = dumb;
    }

    public User() {
    }
}
