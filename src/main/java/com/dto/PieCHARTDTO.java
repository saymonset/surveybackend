package com.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simon on 5/31/2019.
 */
public class PieCHARTDTO {
    private DataLabelsCHARTDTO dataLabels = new DataLabelsCHARTDTO();
    private int startAngle = -90;
    private int  endAngle = 90;
    private List<String> center = new ArrayList<String>(Arrays.asList("50%", "75%"));
   private String  size= "110%";



    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

    public List<String> getCenter() {
        return center;
    }

    public void setCenter(List<String> center) {
        this.center = center;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public DataLabelsCHARTDTO getDataLabels() {
        return dataLabels;
    }

    public void setDataLabels(DataLabelsCHARTDTO dataLabels) {
        this.dataLabels = dataLabels;
    }
}
