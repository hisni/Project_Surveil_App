package com.aslam.co321_project.Authentication;

public class Pharmacy {
    String pharmacyName;
    String pharmacyPhone;
    String pharmacyAddress;
    String pharmacyId;
    String longitude;
    String latitude;

    public Pharmacy() {
    }

    public Pharmacy(String pharmacyId, String pharmacyName, String pharmacyPhone, String pharmacyAddress) {
        this.pharmacyName = pharmacyName;
        this.pharmacyPhone = pharmacyPhone;
        this.pharmacyAddress = pharmacyAddress;
        this.pharmacyId = pharmacyId;
    }

    public Pharmacy(String pharmacyId, String pharmacyName, String pharmacyPhone, String pharmacyAddress, String longatude, String latitude) {
        this.pharmacyName = pharmacyName;
        this.pharmacyPhone = pharmacyPhone;
        this.pharmacyAddress = pharmacyAddress;
        this.pharmacyId = pharmacyId;
        this.longitude = longatude;
        this.latitude = latitude;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public String getPharmacyPhone() {
        return pharmacyPhone;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
