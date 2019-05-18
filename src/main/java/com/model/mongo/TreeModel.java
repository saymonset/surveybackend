package com.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * Created by simon on 5/17/2019.
 */
@Document
public class TreeModel {
    private String value;
    private long node;
    private long parentNode;
    private List<TreeModel> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public List<TreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }


    public long getNode() {
        return node;
    }

    public void setNode(long node) {
        this.node = node;
    }

    public long getParentNode() {
        return parentNode;
    }

    public void setParentNode(long parentNode) {
        this.parentNode = parentNode;
    }
}
