package com.makeathon.enable.simplechatapp;

/**
 * Created by lakapoor on 11/12/17.
 */

public class GetContactsFirebase {
    String phone;

    public GetContactsFirebase(String phone) {
        this.phone = phone;
    }

    public GetContactsFirebase(){}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
