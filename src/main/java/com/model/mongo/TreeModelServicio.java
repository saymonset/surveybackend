package com.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by simon on 5/22/2019.
 */
@Document
public class TreeModelServicio {
    private String value;
    private String node;
    private String parentNode;
    private String divisionServicios;
    private List<TreeModelServicio> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public List<TreeModelServicio> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModelServicio> children) {
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

    public String getDivisionServicios() {
        return divisionServicios;
    }

    public void setDivisionServicios(String divisionServicios) {
        this.divisionServicios = divisionServicios;
    }
}
