package com.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * Created by simon on 5/17/2019.
 */
@Document
public class TreeModelMongo {
    private String value;
    private long id;
    private long parent;
    private List<TreeModelMongo> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TreeModelMongo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModelMongo> children) {
        this.children = children;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }


}
