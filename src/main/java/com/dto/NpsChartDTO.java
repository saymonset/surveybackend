package com.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simon on 5/31/2019.
 */
public class NpsChartDTO {
    private  ChartCHARTDTO chart = new ChartCHARTDTO();
    private TitleCHARTDTO title = new TitleCHARTDTO();
    private TooltipCHARTDTO tooltip = new TooltipCHARTDTO();
    private PlotOptionsCHARTDTO plotOptions = new PlotOptionsCHARTDTO();
    private List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();


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
