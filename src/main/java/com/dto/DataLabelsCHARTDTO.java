package com.dto;

/**
 * Created by simon on 5/31/2019.
 */
public class DataLabelsCHARTDTO {
    private boolean enabled = true;
    private long distance = -50;
    private StyleCHARTDTO style = new StyleCHARTDTO();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public StyleCHARTDTO getStyle() {
        return style;
    }

    public void setStyle(StyleCHARTDTO style) {
        this.style = style;
    }
}
