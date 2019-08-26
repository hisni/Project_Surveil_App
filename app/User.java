package com.aslam.co321_project;

class User{
    private String name;
    private String phone;
    private String type;

    public User(){

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(String name, String phone, String type){
        this.name = name;
        this.phone = phone;
        this.type = type;
    }
}