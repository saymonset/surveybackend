package com.dto;

import java.util.*;

/**
 * Created by simon on 5/31/2019.
 */
public class Serie0ChartDTO {
    private String  type = "pie";
    private String name = "";
    private String innerSize = "50%";
    private List<Object> data = new ArrayList();


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInnerSize() {
        return innerSize;
    }

    public void setInnerSize(String innerSize) {
        this.innerSize = innerSize;
    }


    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
