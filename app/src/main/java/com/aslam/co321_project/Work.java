package com.aslam.co321_project;

import java.util.List;

public class Work {
    private String driverId;
    private String pharmacyId;
    private String driverName;
    private String pharmacyName;
    private String title;
    private String subTitle;
    public List boxList;

    public Work(String title, String subtitle, List boxList) {
        this.title = title;
        this.subTitle = subtitle;
        this.boxList = boxList;
    }



    public Work(String driverId, String pharmacyId, String driverName, String pharmacyName, List boxList) {
        this.driverId = driverId;
        this.pharmacyId = pharmacyId;
        this.driverName = driverName;
        this.pharmacyName = pharmacyName;
        this.boxList = boxList;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public String getDriverName() {
        return driverName;
    }

    public List getBoxList() {
        return boxList;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getTitle() {
        return title;
    }
}
