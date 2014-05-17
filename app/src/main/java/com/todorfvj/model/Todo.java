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
    private boolean checked;
    private String label;
    private String content;
    private DateTime creation ;
    private String labels ;
    private DateTime reminder ;
    private boolean deleted;

    public Todo(String _label,String _content,boolean _checked, DateTime _creation, String _tags){
        this.id = UUID.randomUUID().toString();
        this.label = _label;
        this.content = _content;
        this.checked = _checked;
        this.creation = _creation ;
        this.labels = _tags ;
        this.deleted = false ;
    }

   public Todo(String _id,String _label,String _content, boolean _checked, String _tags){
       this(_label,_content,_checked, new DateTime(),_tags);
       this.id = _id;
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

    public DateTime getCreation() { return creation; }
    public void setCreation(DateTime creation) { this.creation = creation; }

    public DateTime getReminder() { return reminder; }
    public void setReminder(DateTime reminder) { this.reminder = reminder; }

    public String getTags() { return labels; }
    public void setTags(String labels) { this.labels = labels; }

    public boolean isDeteled() { return this.deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }


}
