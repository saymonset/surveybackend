package com.dto;

/**
 * Created by simon on 5/31/2019.
 */
public class TooltipCHARTDTO {
  private String  pointFormat = "{series.name}: <b>{point.percentage:.1f}%</b>";

    public String getPointFormat() {
        return pointFormat;
    }

    public void setPointFormat(String pointFormat) {
        this.pointFormat = pointFormat;
    }
}
