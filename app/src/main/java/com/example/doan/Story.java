package com.example.doan;

public class Story {
    private int maT;
    private String tenTV;
    private String tacgiaTV;
    private String tinhtrangTV;
    private int sochuongTV;
    private String mota;
    private String timestamp;
    private int maTL;
    private String image;
    private String uid;
    private int Views;
    private int DeCu;
    private int Likes;


    public Story() {
    }

    public Story(int id, String name, String author, String status, int views, String mota, String time, int matl, String image, String uid) {
        this.maT = id;
        this.tenTV = name;
        this.tacgiaTV = author;
        this.tinhtrangTV = status;
        this.sochuongTV = views;
        this.mota= mota;
        this.timestamp=time;
        this.maTL=matl;
        this.image=image;
        this.uid=uid;
    }
    public Story(int maT,String tenTV, String tacgiaTV,int maTL, int sochuongTV, String mota) {
        this.maT=maT;
        this.tenTV=tenTV;
        this.tacgiaTV=tacgiaTV;
        this.maTL=maTL;
        this.sochuongTV=sochuongTV;
        this.mota=mota;
    }

    public int getmaT() {
        return maT;
    }

    public void setmaT(int id) {
        this.maT = id;
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


    public void setmota(String mota) {
        mota = mota;
    }

    public String getmota() {
        return mota;
    }


    public void setViews(int views) { Views=views;
    }

    public int getViews() { return Views;
    }



    public void setDeCu(int deCu) {
        DeCu = deCu;
    }

    public int getDeCu() {
        return DeCu;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }
}
