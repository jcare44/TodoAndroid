package com.todorfvj.model;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.UUID;

/**
 * Created by julien on 30/04/14.
 */
public class Todo {
    private String id;
    private String label;
    private String content;
    private boolean checked;
    private String tags ;
    private DateTime reminder ;
    private boolean deleted;
    private DateTime creation ;


    public Todo(String id, String label, String content, boolean checked, String tags, DateTime reminder, boolean deleted, DateTime creation) {
        this.id = id;
        this.label = label;
        this.content = content;
        this.checked = checked;
        this.tags = tags;
        this.reminder = reminder;
        this.deleted = deleted;
        this.creation = creation;
    }

    public Todo(String label) {
        this(UUID.randomUUID().toString(), label,"", false, "", null, false, new DateTime()) ;
    }

    public Todo(String label, String content, String tags, DateTime reminder) {
        this(UUID.randomUUID().toString(), label, content, false, tags, reminder, false, new DateTime()) ;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) { this.id = id; }

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getCreation() {
        return creation;
    }
    public void setCreation(DateTime creation) {
        this.creation = creation;
    }

    public DateTime getReminder() {
        return reminder;
    }
    public void setReminder(DateTime reminder) {
        this.reminder = reminder;
    }

    public String getTags() {
        return tags;
    }
    public void setTags(String labels) {
        this.tags = labels;
    }

    public boolean isDeteled() {
        return this.deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
