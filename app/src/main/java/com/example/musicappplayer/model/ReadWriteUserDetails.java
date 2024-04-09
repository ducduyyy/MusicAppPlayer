package com.example.musicappplayer.model;

public class ReadWriteUserDetails {
    public String fullname, doB, gender, PhoneNumber;

    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String textDoB, String textGender, String textMobile) {
        this.doB = textDoB;
        this.gender = textGender;
        this.PhoneNumber = textMobile;
    }

    public ReadWriteUserDetails(String fullname, String doB, String gender, String PhoneNumber) {
        this.fullname = fullname;
        this.doB = doB;
        this.gender = gender;
        this.PhoneNumber = PhoneNumber;
    }
}
