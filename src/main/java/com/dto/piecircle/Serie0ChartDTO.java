package com.dto.piecircle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 6/9/2019.
 */
public class Serie0ChartDTO {
    private String  type = "pie";
    private String name = "";
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

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
