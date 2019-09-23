package com.aslam.co321_project.Distributor;

import java.util.List;

class Work {
    private String driver;
    private String pharmacy;
    List boxList;

    public Work(String driver, String pharmacy, List boxList) {
        this.driver = driver;
        this.pharmacy = pharmacy;
        this.boxList = boxList;
    }

    public String getDriver() {
        return driver;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public List getBoxList() {
        return boxList;
    }
}
