package com.dto.piecircle;

/**
 * Created by simon on 6/9/2019.
 */
public class DataLabelsCHARTDTO {
    private String  connectorColor = "silver";
    private String format = "<b>{point.name}</b>: {point.percentage:.1f} %";
    private StyleCHARTDTO style = new StyleCHARTDTO();
    public String getConnectorColor() {
        return connectorColor;
    }

    public void setConnectorColor(String connectorColor) {
        this.connectorColor = connectorColor;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public StyleCHARTDTO getStyle() {
        return style;
    }

    public void setStyle(StyleCHARTDTO style) {
        this.style = style;
    }
}
