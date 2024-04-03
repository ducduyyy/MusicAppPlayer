package com.example.musicappplayer.model;

public class ReadWriteUserDetails {
    public String fullname, doB, gender, PhoneNumber;

    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String fullname, String doB, String gender, String PhoneNumber) {
        this.fullname = fullname;
        this.doB = doB;
        this.gender = gender;
        this.PhoneNumber = PhoneNumber;
    }
}
