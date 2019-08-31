package com.aslam.co321_project;

public class Delivery {

    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String id;

    public Delivery(){
    }


    public Delivery(String name, String address, String phone, String id) {
        this.shopName = name;
        this.shopAddress= address;
        this.shopPhone= phone;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }
}
