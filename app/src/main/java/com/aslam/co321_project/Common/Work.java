package com.aslam.co321_project.Common;

import java.util.List;

public class Work {
    private String driverId;
    private String distributorId;
    private String rightId;

    public Work(String distributorId, String rightId, String title, String subTitle, String leftId, String randomId) {
        this.distributorId = distributorId;
        this.rightId = rightId;
        this.title = title;
        this.subTitle = subTitle;
        this.leftId = leftId;
        this.randomId = randomId;
    }

    private String driverName;
    private String pharmacyName;
    private String title;
    private String subTitle;
    private String leftId;
    private String randomId;

    public Work(String title, String subTitle, String leftId, String rightId, String randomId) {
        this.title = title;
        this.subTitle = subTitle;
        this.leftId = leftId;
        this.randomId = randomId;
        this.rightId = rightId;
    }

    public String getLeftId() {
        return leftId;
    }

    public Work(String driverId, String rightId, String leftId, List boxList) {
        this.driverId = driverId;
        this.rightId = rightId;
        this.leftId = leftId;
        this.boxList = boxList;
    }


    public String getRandomId() {
        return randomId;
    }

    public List boxList;

    public Work(String title, String subtitle, List boxList) {
        this.title = title;
        this.subTitle = subtitle;
        this.boxList = boxList;
    }

    public Work(String driverId, String rightId, String driverName, String pharmacyName, List boxList) {
        this.driverId = driverId;
        this.rightId = rightId;
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

    public String getRightId() {
        return rightId;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getTitle() {
        return title;
    }
}
