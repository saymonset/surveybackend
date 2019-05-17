package com.dto;

import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */
public class TreeModelDTO {
    private String value;
    private long id;
    private List<TreeModelDTO> children;

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

    public List<TreeModelDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModelDTO> children) {
        this.children = children;
    }
}
