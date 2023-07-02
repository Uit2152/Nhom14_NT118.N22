package com.example.doan;

import java.security.Timestamp;

public class ChuongTruyen {
    private int MaT;
    private int MaC;
    private String ND;
    private String tenC;
    private Timestamp timestamp;

    public ChuongTruyen() {
        // Constructor mặc định không tham số
    }
    public ChuongTruyen(int maC, String ND, String tenC) {
        MaC = maC;
        this.ND = ND;
        this.tenC = tenC;
    }

    public int getMaT() {
        return MaT;
    }

    public void setMaT(int maT) {
        MaT = maT;
    }

    public int getMaC() {
        return MaC;
    }

    public void setMaC(int maC) {
        MaC = maC;
    }

    public String getND() {
        return ND;
    }

    public void setND(String ND) {
        this.ND = ND;
    }

    public String getTenC() {
        return tenC;
    }

    public void setTenC(String tenC) {
        this.tenC = tenC;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
