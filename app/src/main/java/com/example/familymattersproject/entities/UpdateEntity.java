package com.example.familymattersproject.entities;

import java.util.Date;
import java.util.UUID;

public class UpdateEntity {
    private String UID = "";
    private String text = "";
    private String relatedToName = "";
    private String relatedToIconPath = "";
    private Date date = null;

    public UpdateEntity(){

    }

    public UpdateEntity(String text, String relatedToName, Date date) {
        UID = UUID.randomUUID().toString();
        this.text = text;
        this.relatedToName = relatedToName;
        this.date = date;
    }

    public String getRelatedToIconPath() {
        return relatedToIconPath;
    }

    public void setRelatedToIconPath(String relatedToIconPath) {
        this.relatedToIconPath = relatedToIconPath;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRelatedToName() {
        return relatedToName;
    }

    public void setRelatedToName(String relatedToName) {
        this.relatedToName = relatedToName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
