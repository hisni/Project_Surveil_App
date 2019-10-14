package com.aslam.co321_project.Common;

public class DeliverDetails {
    private String rightId;
    private String title;
    private String subTitle;
    private String leftId;
    private String randomId;

    public DeliverDetails(String title, String subTitle, String leftId, String rightId, String randomId) {
        this.title = title;
        this.subTitle = subTitle;
        this.leftId = leftId;
        this.randomId = randomId;
        this.rightId = rightId;
    }

    public String getLeftId() {
        return leftId;
    }

    public String getRandomId() {
        return randomId;
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
