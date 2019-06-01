package com.dto;

/**
 * Created by simon on 5/31/2019.
 */
public class ChartCHARTDTO {
   private Long plotBackgroundColor = null;
    private Long  plotBorderWidth = 0l;
    private boolean  plotShadow = false;

    public Long getPlotBackgroundColor() {
        return plotBackgroundColor;
    }

    public void setPlotBackgroundColor(Long plotBackgroundColor) {
        this.plotBackgroundColor = plotBackgroundColor;
    }

    public Long getPlotBorderWidth() {
        return plotBorderWidth;
    }

    public void setPlotBorderWidth(Long plotBorderWidth) {
        this.plotBorderWidth = plotBorderWidth;
    }

    public boolean isPlotShadow() {
        return plotShadow;
    }

    public void setPlotShadow(boolean plotShadow) {
        this.plotShadow = plotShadow;
    }
}
