package com.dto;

import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */
public class TreeModelDTO {
    private String id;
    private List<TreeModelDTO> children;
    private String parent;
    private String codigo;
    private SettingsDTO settings = new SettingsDTO();


    private String value;




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




    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public SettingsDTO getSettings() {
        return settings;
    }

    public void setSettings(SettingsDTO settings) {
        this.settings = settings;
    }
}
