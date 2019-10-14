package com.aslam.co321_project.Common;

public class User {

    private String name;
    private String phone;
    private String type;
    private String address;

    public User(){
    }

    public User(String name, String phone, String type, String address) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.address = address;
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

    public String getAddress() { return address; }
}
