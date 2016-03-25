package com.grability.myappstore.model;

/**
 * Created by Luis Vespa on 23/03/2016.
 */
public class image {

    private int id;
    private String entryid;
    private String label;
    private int height;

    public image(String entryid, String label, int height) {
        this.id = id;
        this.entryid = entryid;
        this.label = label;
        this.height = height;
    }

    public image(int id, String entryid, String label, int height) {
        this.id = id;
        this.entryid = entryid;
        this.label = label;
        this.height = height;
    }

    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
