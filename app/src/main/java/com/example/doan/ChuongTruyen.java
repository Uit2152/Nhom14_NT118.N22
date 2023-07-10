package com.example.doan;

public class ChuongTruyen {
    private int maT;
    private int maC;
    private String nd;
    private String tenC;
    private String timestamp;

    public ChuongTruyen() {
        // Constructor mặc định không tham số
    }
    public ChuongTruyen(int maC, String ND, String tenC) {
        this.maC = maC;
        this.nd = ND;
        this.tenC = tenC;
    }

    public ChuongTruyen(int maT, int maC, String ND, String tenC) {
        this.maT=maT;
        this.maC = maC;
        this.nd = ND;
        this.tenC = tenC;
    }
    public int getmaT() {
        return this.maT;
    }

    public void setmaT(int maT) {
        this.maT = maT;
    }

    public int getMaC() {
        return this.maC;
    }

    public void setMaC(int maC) {
        this.maC = maC;
    }

    public String getnd() {
        return this.nd;
    }

    public void setnd(String ND) {
        this.nd = ND;
    }

    public String getTenC() {
        return this.tenC;
    }

    public void setTenC(String tenC) {
        this.tenC = tenC;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
