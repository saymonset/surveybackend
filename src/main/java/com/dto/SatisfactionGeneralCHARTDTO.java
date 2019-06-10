package com.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 6/9/2019.
 */
public class SatisfactionGeneralCHARTDTO {
    private TitleCHARTDTO title =  new TitleCHARTDTO("Satisfaccion General");
    private TooltipCHARTDTO tooltip = new TooltipCHARTDTO();
    private PlotOptionsCHARTDTO plotOptions = new PlotOptionsCHARTDTO();;
    private List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();

    /**Estas la sobreescribimos enn las clases internas con nuevos valores*/
    private DataLabelsCHARTDTO dataLabels = new DataLabelsCHARTDTO();
    private StyleCHARTDTO style = new StyleCHARTDTO();
    private PieCHARTDTO pie = new PieCHARTDTO();
    /** Fin Estas la sobreescribimos enn las clases internas con nuevos valores*/
    public void SatisfactionGeneralCHARTDTO(){
        /**Sobreescribimos variables  por defecto*/

        getStyle().setColor("black");
        getDataLabels().setStyle(getStyle());
        getPie().setDataLabels(getDataLabels());
        getPlotOptions().setPie(getPie());



    }

    public List<Serie0ChartDTO> getSeries() {
        return series;
    }

    public void setSeries(List<Serie0ChartDTO> series) {
        this.series = series;
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

    public DataLabelsCHARTDTO getDataLabels() {
        return dataLabels;
    }

    public void setDataLabels(DataLabelsCHARTDTO dataLabels) {
        this.dataLabels = dataLabels;
    }

    public StyleCHARTDTO getStyle() {
        return style;
    }

    public void setStyle(StyleCHARTDTO style) {
        this.style = style;
    }

    public PieCHARTDTO getPie() {
        return pie;
    }

    public void setPie(PieCHARTDTO pie) {
        this.pie = pie;
    }
}
