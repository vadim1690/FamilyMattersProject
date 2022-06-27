package com.example.familymattersproject.entities;

import java.util.Date;
import java.util.UUID;

public class FamilyEventEntity {
    private String UID = "";
    private Date date = null;
    private String description = "";


    public FamilyEventEntity() {
    }

    public FamilyEventEntity(Date date, String description) {
        this.UID = UUID.randomUUID().toString();
        this.date = date;
        this.description = description;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
