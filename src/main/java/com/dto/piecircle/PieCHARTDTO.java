package com.dto.piecircle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simon on 6/9/2019.
 */
public class PieCHARTDTO {
    private  boolean allowPointSelect = true;
    private  String  cursor = "pointer";
    private DataLabelsCHARTDTO dataLabels = new DataLabelsCHARTDTO();


    public boolean isAllowPointSelect() {
        return allowPointSelect;
    }

    public void setAllowPointSelect(boolean allowPointSelect) {
        this.allowPointSelect = allowPointSelect;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public DataLabelsCHARTDTO getDataLabels() {
        return dataLabels;
    }

    public void setDataLabels(DataLabelsCHARTDTO dataLabels) {
        this.dataLabels = dataLabels;
    }
}
