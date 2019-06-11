package com.dto.barrtreestado;

import java.util.ArrayList;
import java.util.List;

public class AlertCHARTDTO {
    private ChartCHARTDTO chart = new ChartCHARTDTO();
    private TitleCHARTDTO title = new TitleCHARTDTO();
    private XaxisCHARTDTO xAxis = new XaxisCHARTDTO();
    private YaxisCHARTDTO yAxis = new YaxisCHARTDTO();
    private TooltipCHARTDTO tooltip = new TooltipCHARTDTO();
    private PlotOptionsCHARTDTO plotOptions = new PlotOptionsCHARTDTO();
    private List<Serie0ChartDTO> series = new ArrayList<>();

    public ChartCHARTDTO getChart() {
        return chart;
    }

    public void setChart(ChartCHARTDTO chart) {
        this.chart = chart;
    }

    public TitleCHARTDTO getTitle() {
        return title;
    }

    public void setTitle(TitleCHARTDTO title) {
        this.title = title;
    }

    public XaxisCHARTDTO getxAxis() {
        return xAxis;
    }

    public void setxAxis(XaxisCHARTDTO xAxis) {
        this.xAxis = xAxis;
    }

    public YaxisCHARTDTO getyAxis() {
        return yAxis;
    }

    public void setyAxis(YaxisCHARTDTO yAxis) {
        this.yAxis = yAxis;
    }

    public TooltipCHARTDTO getTooltip() {
        return tooltip;
    }

    public void setTooltip(TooltipCHARTDTO tooltip) {
        this.tooltip = tooltip;
    }

    public PlotOptionsCHARTDTO getPlotOptions() {
        return plotOptions;
    }

    public void setPlotOptions(PlotOptionsCHARTDTO plotOptions) {
        this.plotOptions = plotOptions;
    }


    public List<Serie0ChartDTO> getSeries() {
        return series;
    }

    public void setSeries(List<Serie0ChartDTO> series) {
        this.series = series;
    }
}
