package com.example.doan;

public class DocTruyen {
    private String uid;
    private int maT;
    private int dc;
    private int dd;
    private int yt;
    private int dg;
    private int chuongDD;
    private String timestamp;

    public DocTruyen() {}

    public DocTruyen(String uid, int maT, int DC, int DD, int YT, int DG, int chuongDD, String timestamp) {
        this.uid = uid;
        this.maT = maT;
        this.dc = DC;
        this.dd = DD;
        this.yt = YT;
        this.dg = DG;
        this.chuongDD = chuongDD;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getmaT() {
        return maT;
    }

    public void setmaT(int maT) {
        maT = maT;
    }

    public int getdc() {
        return dc;
    }

    public void setdc(int DC) {
        this.dc = DC;
    }

    public int getdd() {
        return dd;
    }

    public void setdd(int DD) {
        this.dd = DD;
    }

    public int getyt() {
        return yt;
    }

    public void setyt(int YT) {
        this.yt = YT;
    }

    public int getdg() {
        return dg;
    }

    public void setdg(int DG) {
        this.dg = DG;
    }

    public int getChuongDD() {
        return chuongDD;
    }

    public void setChuongDD(int chuongDD) {
        this.chuongDD = chuongDD;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
