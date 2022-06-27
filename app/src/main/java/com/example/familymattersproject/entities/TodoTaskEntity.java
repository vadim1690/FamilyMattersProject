package com.example.familymattersproject.entities;

import java.util.UUID;

public class TodoTaskEntity {
    private String UID = "";
    private Boolean isDone = false;
    private String description = "";

    private String createdByIconPath = "";

    public TodoTaskEntity() {

    }

    public TodoTaskEntity( String description) {
        this.UID = UUID.randomUUID().toString();
        this.description = description;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getCreatedByIconPath() {
        return createdByIconPath;
    }

    public void setCreatedByIconPath(String createdByIconPath) {
        this.createdByIconPath = createdByIconPath;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }


    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
