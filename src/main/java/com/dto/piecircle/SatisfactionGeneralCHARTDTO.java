package com.dto.piecircle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 6/9/2019.
 */
public class SatisfactionGeneralCHARTDTO {
    private TitleCHARTDTO title;
    private TooltipCHARTDTO tooltip = new TooltipCHARTDTO("{series.name}: <b>{point.percentage:.1f}%</b>");
    private PlotOptionsCHARTDTO plotOptions = new PlotOptionsCHARTDTO();;
    private List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();

    /**Estas la sobreescribimos enn las clases internas con nuevos valores*//*
    private DataLabelsCHARTDTO dataLabels = new DataLabelsCHARTDTO();
    private StyleCHARTDTO style = new StyleCHARTDTO();
    private PieCHARTDTO pie = new PieCHARTDTO();*/
    /** Fin Estas la sobreescribimos enn las clases internas con nuevos valores*/
    public void SatisfactionGeneralCHARTDTO(){



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


    public void setTitle(TitleCHARTDTO title) {
        this.title = title;
    }

    public TitleCHARTDTO getTitle() {
        return title;
    }
}
