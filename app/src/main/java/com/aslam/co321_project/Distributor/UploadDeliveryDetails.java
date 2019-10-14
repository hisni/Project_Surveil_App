package com.aslam.co321_project.Distributor;

import java.util.List;

class UploadDeliveryDetails {
    String driverName;
    String pharmacyName;
    String pharmacyId;
    String driverId;
    List boxList;

    public UploadDeliveryDetails(String driverName, String pharmacyName, String pharmacyId, String driverId, List boxList) {
        this.driverName = driverName;
        this.pharmacyName = pharmacyName;
        this.pharmacyId = pharmacyId;
        this.driverId = driverId;
        this.boxList = boxList;
    }
}
