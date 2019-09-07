package com.aslam.co321_project.Authentication;

public class Distributor {
    private String shopName;
    private String shopPhone;
    private String shopAddress;

    public Distributor(){
    }

    public Distributor(String name, String phone, String address){
        this.shopName = name;
        this.shopPhone = phone;
        this.shopAddress = address;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public String getShopAddress() {
        return shopAddress;
    }
}
