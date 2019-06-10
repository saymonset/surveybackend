package com.dto;

/**
 * Created by simon on 5/31/2019.
 */
public class DataLabelsCHARTDTO {
    private boolean enabled = true;
    private long distance = -50;
    private String format = "<b>{point.name}</b>: {point.percentage:.1f} %";
    private StyleCHARTDTO style = new StyleCHARTDTO();
    private String  connectorColor = "silver";

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getConnectorColor() {
        return connectorColor;
    }

    public void setConnectorColor(String connectorColor) {
        this.connectorColor = connectorColor;
    }
}
