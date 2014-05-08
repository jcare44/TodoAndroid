package com.example.model;

/**
 * Created by julien on 30/04/14.
 */
public class Todo {
    private int id;
    private boolean checked;
    private String label;
    private String content;

   public Todo(int _id,String _label,String _content,boolean _checked){
        this.id = _id;
        this.label = _label;
        this.content = _content;
       this.checked = _checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
