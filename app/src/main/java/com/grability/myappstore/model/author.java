package com.grability.myappstore.model;

/**
 * Created by Luis Vespa on 23/03/2016.
 */
public class author {

    private int code;
    private String id;
    private String name;
    private String uri;


    public author() {
    }

    public author(String id, String name, String uri) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public author(int code, String id, String name, String uri) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
