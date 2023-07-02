package com.example.doan;

public class Users {
    private String uid;
    private String email;
    private String name;
    private String profileImage;
    private String userType;
    private long timestamp;

    public Users() {
        // Empty constructor needed for Firebase
    }

    public Users(String uid, String email, String name, String profileImage, String userType, long timestamp) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.userType = userType;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
