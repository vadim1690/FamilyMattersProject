package com.example.familymattersproject.entities;

import java.util.Date;

public class UserEntity {
    private String UID = "";
    private String name = "";
    private String avatar = "";
    private String information = "";
    private Date joinDate = null;
    private String familyUID = "";

    public UserEntity() {
    }

    public UserEntity(String UID,String name, String information) {
        this.UID = UID;
        this.name = name;
        this.information = information;
        this.joinDate = new Date();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyUID() {
        return familyUID;
    }

    public void setFamilyUID(String familyUID) {
        this.familyUID = familyUID;
    }
}
