package com.example.doan;

public class DocTruyen {
    private String uid;
    private int MaT;
    private int DC;
    private int maDD;
    private int YT;
    private int DG;
    private int chuongDD;
    private String datetime;

    public DocTruyen() {}

    public DocTruyen(String uid, int maT, int DC, int DD, int YT, int DG, int chuongDD, String datetime) {
        this.uid = uid;
        this.MaT = maT;
        this.DC = DC;
        this.maDD = DD;
        this.YT = YT;
        this.DG = DG;
        this.chuongDD = chuongDD;
        this.datetime = datetime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getMaT() {
        return MaT;
    }

    public void setMaT(int maT) {
        MaT = maT;
    }

    public int getDC() {
        return DC;
    }

    public void setDC(int DC) {
        this.DC = DC;
    }

    public int getmaDD() {
        return maDD;
    }

    public void setmaDD(int DD) {
        this.maDD = DD;
    }

    public int getYT() {
        return YT;
    }

    public void setYT(int YT) {
        this.YT = YT;
    }

    public int getDG() {
        return DG;
    }

    public void setDG(int DG) {
        this.DG = DG;
    }

    public int getChuongDD() {
        return chuongDD;
    }

    public void setChuongDD(int chuongDD) {
        this.chuongDD = chuongDD;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetimep(String datetime) {
        this.datetime = datetime;
    }
}
