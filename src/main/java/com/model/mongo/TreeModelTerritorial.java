package com.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * Created by simon on 5/17/2019.
 */
@Document
public class TreeModelTerritorial {
    private String value;
    private String node;
    private String parentNode;
    private String divisionTerritorial;
    private List<TreeModelTerritorial> children;


    public TreeModelTerritorial() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public List<TreeModelTerritorial> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModelTerritorial> children) {
        this.children = children;
    }



    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public String getDivisionTerritorial() {
        return divisionTerritorial;
    }

    public void setDivisionTerritorial(String divisionTerritorial) {
        this.divisionTerritorial = divisionTerritorial;
    }
}
