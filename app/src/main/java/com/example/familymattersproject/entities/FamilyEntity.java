package com.example.familymattersproject.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FamilyEntity {
    private String UID = "";
    private String name = "";
    private String creator = "";
    Map<String, Boolean> members = new HashMap<>();
    Map<String, FamilyEventEntity> events = new HashMap<>();
    Map<String, UpdateEntity> updates = new HashMap<>();
    Map<String, TodoTaskEntity> todoList = new HashMap<>();
    Map<String, TodoTaskEntity> todoListArchived = new HashMap<>();

    public FamilyEntity() {
        UID = UUID.randomUUID().toString();
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

    public Map<String, UpdateEntity> getUpdates() {
        return updates;
    }


    public void setUpdates(Map<String, UpdateEntity> updates) {
        this.updates = updates;
    }

    public Map<String, TodoTaskEntity> getTodoListArchived() {
        return todoListArchived;
    }

    public void setTodoListArchived(Map<String, TodoTaskEntity> todoListArchived) {
        this.todoListArchived = todoListArchived;
    }

    public Map<String, TodoTaskEntity> getTodoList() {
        return todoList;
    }


    public void setTodoList(Map<String, TodoTaskEntity> todoList) {
        this.todoList = todoList;
    }

    public Map<String, Boolean> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Boolean> members) {
        this.members = members;
    }

    public Map<String, FamilyEventEntity> getEvents() {
        return events;
    }

    public void setEvents(Map<String, FamilyEventEntity> events) {
        this.events = events;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
