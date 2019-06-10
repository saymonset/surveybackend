package com.dto.piecircle;

/**
 * Created by simon on 6/9/2019.
 */
public class TooltipCHARTDTO {
    private String  pointFormat = "{series.name}: <b>{point.percentage:.1f}%</b>";

    public TooltipCHARTDTO(String pointFormat) {
        this.pointFormat = pointFormat;
    }

    public String getPointFormat() {
        return pointFormat;
    }

    public void setPointFormat(String pointFormat) {
        this.pointFormat = pointFormat;
    }
}
