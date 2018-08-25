package com.example.ideathon;

public class Ads {
    private String company;
    private String detail;
    private String photoUrl;
    private String offerUrl;

    public Ads() {

    }

    public Ads(String company, String detail, String photoUrl, String offerUrl) {
        this.company = company;
        this.detail = detail;
        this.photoUrl = photoUrl;
        this.offerUrl = offerUrl;
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

    public String getOfferUrl() {
        return offerUrl;
    }

    public void setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
    }
}
