package com.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;


/**
 * Created by simon on 5/17/2019.
 */
@Document
public class TreeModelTerritorial {
    @Id
    private String id;
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

    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TreeModelTerritorial other = (TreeModelTerritorial) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
