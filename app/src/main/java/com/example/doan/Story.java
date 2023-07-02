package com.example.doan;

public class Story {
    private int MaT;
    private String tenTV;
    private String tacgiaTV;
    private String tinhtrangTV;
    private int sochuongTV;
    private String Mota;
    private String timestamp;
    private int maTL;
    private String image;
    private String uid;


    public Story() {
    }

    public Story(int id, String name, String author, String status, int views, String mota, String time, int matl, String image, String uid) {
        this.MaT = id;
        this.tenTV = name;
        this.tacgiaTV = author;
        this.tinhtrangTV = status;
        this.sochuongTV = views;
        this.Mota= mota;
        this.timestamp=time;
        this.maTL=matl;
        this.image=image;
        this.uid=uid;
    }

    public Story(int maT,String tenTV, String tacgiaTV,int maTL, int sochuongTV, String mota) {
        this.MaT=maT;
        this.tenTV=tenTV;
        this.tacgiaTV=tacgiaTV;
        this.maTL=maTL;
        this.sochuongTV=sochuongTV;
        this.Mota=mota;
    }

    public int getMaT() {
        return MaT;
    }

    public void setMaT(int id) {
        this.MaT = id;
    }

    public String gettenTV() {
        return tenTV;
    }

    public void settenTV(String name) {
        this.tenTV = name;
    }

    public String gettacgiaTV() {
        return tacgiaTV;
    }

    public void settacgiaTV(String author) {
        this.tacgiaTV = author;
    }

    public String gettinhtrangTV() {
        return tinhtrangTV;
    }

    public void settinhtrangTV(String status) {
        this.tinhtrangTV = status;
    }

    public int getsochuongTV() {
        return sochuongTV;
    }

    public void setsochuongTV(int views) {
        this.sochuongTV = views;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    public int getMaTL() {
        return maTL;
    }
    public void setMaTL(int maTL) {
        this.maTL = maTL;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public void setMota(String mota) {
        Mota = mota;
    }

    public String getMota() {
        return Mota;
    }

}