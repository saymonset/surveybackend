package com.dto;

/**
 * Created by simon on 5/25/2019.
 */
public class TemplatesDTO {
    private String node = "<i class='icon-folder-close icon-folder-close icon-folder-close'></i>";
    private String leaf = "<i class='icon-file-text icon-file-text icon-file-text'></i>";
    private String leftMenu = "<i class='icon-folder-close icon-home icon-home'></i>";

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getLeftMenu() {
        return leftMenu;
    }

    public void setLeftMenu(String leftMenu) {
        this.leftMenu = leftMenu;
    }
}


