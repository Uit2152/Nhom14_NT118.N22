package com.example.doan;

public class Chapter {

    private int MaC;
    private int MaT;
    private String ND;
    private String tenC;
    private String timestamp;

    public Chapter(int maC, String ND, String tenC) {
        MaC = maC;
        this.ND = ND;
        this.tenC = tenC;
    }

    public void setMaC(int maC) {
        MaC = maC;
    }

    public void setMaT(int maT) {
        MaT = maT;
    }

    public void setND(String ND) {
        this.ND = ND;
    }

    public void setTenC(String tenC) {
        this.tenC = tenC;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getMaC() {
        return MaC;
    }

    public int getMaT() {
        return MaT;
    }

    public String getND() {
        return ND;
    }

    public String getTenC() {
        return tenC;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Chapter(int maC, int maT, String ND, String tenC, String timestamp) {
        MaC = maC;
        MaT = maT;
        this.ND = ND;
        this.tenC = tenC;
        this.timestamp = timestamp;
    }
}
