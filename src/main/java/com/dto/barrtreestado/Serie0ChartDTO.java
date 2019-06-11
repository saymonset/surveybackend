package com.dto.barrtreestado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 6/10/2019.
 */
public class Serie0ChartDTO {
    private String name = "";
    private  String color;
    private List<Object> data = new ArrayList();

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
