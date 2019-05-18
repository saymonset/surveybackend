package com.dto;

import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */
public class TreeModelDTO {
    private String value;
    private long id;
    private List<TreeModelDTO> children;
    private long parent;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public List<TreeModelDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModelDTO> children) {
        this.children = children;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }
}
