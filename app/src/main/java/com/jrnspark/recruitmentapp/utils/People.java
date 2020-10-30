package com.jrnspark.recruitmentapp.utils;

import android.graphics.drawable.Drawable;

public class People {


    public String name;
    public String email,id;
    public String phoneNumber, resume;


    public People() {
    }

    public People(String name, String email, String phoneNumber, String resume,String id) {
        this.name = name;
        this.email = email;
        this.id= id;
        this.phoneNumber = phoneNumber;
        this.resume = resume;
    }
}
