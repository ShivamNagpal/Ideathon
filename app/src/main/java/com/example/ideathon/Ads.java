package com.example.ideathon;

public class Ads {
    private String company;
    private String detail;
    private String photoUrl;

    public Ads() {

    }

    public Ads(String company, String detail, String photoUrl) {
        this.company = company;
        this.detail = detail;
        this.photoUrl = photoUrl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
