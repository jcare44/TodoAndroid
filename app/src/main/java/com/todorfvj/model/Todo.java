package com.todorfvj.model;

import android.util.Log;

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
    private Date creation ;
    private String labels ;
    private boolean deleted;

    public Todo(String _label,String _content,boolean _checked, Date _creation, String _tags){
        this.id = UUID.randomUUID().toString();
        this.label = _label;
        this.content = _content;
        this.checked = _checked;
        this.creation = _creation ;
        this.labels = _tags ;
        this.deleted = false ;
    }

   public Todo(String _id,String _label,String _content, boolean _checked, Date _creation, String _tags){
       this(_label,_content,_checked,_creation,_tags);
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

    public Date getCreation() { return creation; }
    public void setCreation(Date creation) { this.creation = creation; }

    public String getTags() { return labels; }
    public void setTags(String labels) { this.labels = labels; }

    public boolean isDeteled() { return this.deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }


}
