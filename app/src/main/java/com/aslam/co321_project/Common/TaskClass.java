package com.aslam.co321_project.Common;

public class TaskClass {
    private String distributorId;
    private String randomId;

    public TaskClass(String distributorId, String randomId) {
        this.distributorId = distributorId;
        this.randomId = randomId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public String getRandomId() {
        return randomId;
    }
}
