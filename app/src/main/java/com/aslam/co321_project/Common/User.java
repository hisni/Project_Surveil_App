package com.aslam.co321_project.Common;

public class User {

    private String name;
    private String phone;
    private String type;

    public User(){
    }

    public User(String name, String phone, String type){
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }
}
