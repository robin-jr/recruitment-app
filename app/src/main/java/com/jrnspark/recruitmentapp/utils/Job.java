package com.jrnspark.recruitmentapp.utils;

public class Job {
    public String name, description, id,contactEmail;

    public Job(String name, String description, String id,String contactEmail) {
        this.name = name;
        this.id = id;
        this.contactEmail = contactEmail;
        this.description = description;
    }
}
