package com.example.doan;

import java.security.Timestamp;

public class ChuongTruyen {
    private int maT;
    private int maC;
    private String ND;
    private String tenC;
    private String timestamp;

    public ChuongTruyen() {
        // Constructor mặc định không tham số
    }
    public ChuongTruyen(int maC, String ND, String tenC) {
        this.maC = maC;
        this.ND = ND;
        this.tenC = tenC;
    }

    public int getMaT() {
        return this.maT;
    }

    public void setMaT(int maT) {
        this.maT = maT;
    }

    public int getMaC() {
        return this.maC;
    }

    public void setMaC(int maC) {
        this.maC = maC;
    }

    public String getND() {
        return this.ND;
    }

    public void setND(String ND) {
        this.ND = ND;
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
