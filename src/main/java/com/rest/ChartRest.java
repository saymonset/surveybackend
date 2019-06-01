package com.rest;

import com.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by simon on 5/31/2019.
 */
@RestController
@RequestMapping("/chart")
public class ChartRest {

   /* @GetMapping(value = "/nps")
    public  NpsChartDTO  nps()  {
        NpsChartDTO npsChartDTO = new NpsChartDTO();
        List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();
        Serie0ChartDTO nerie0ChartDTO = new Serie0ChartDTO();
        List<Object>operationsummaryRescue = new ArrayList<>();
        List<Object> operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'Detractores\',13.29f");
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
        //operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'Promotores\',13f");
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
       // operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'pasivos\',3.29f");
       // operationsummaryRescue.add(operationsummaryRescue2);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);

        series.add(nerie0ChartDTO);
        npsChartDTO.setSeries(series);
        return npsChartDTO;
    }*/


    //@RequestMapping(value = "/nps", method = RequestMethod.POST)
    @PostMapping("/nps")
   // public NpsChartDTO searchSurvey(@RequestParam String territorialNode, @RequestParam String servicioNode, @RequestParam Date dateBegin, @RequestParam Date dateEnd) {
    public NpsChartDTO searchSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {
        NpsChartDTO npsChartDTO = new NpsChartDTO();
        List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();
        Serie0ChartDTO nerie0ChartDTO = new Serie0ChartDTO();
        List<Object>operationsummaryRescue = new ArrayList<>();
        List<Object> operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'Detractores\',13.29f");
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
        //operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'Promotores\',13f");
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
        // operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'pasivos\',3.29f");
        // operationsummaryRescue.add(operationsummaryRescue2);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);

        series.add(nerie0ChartDTO);
        npsChartDTO.setSeries(series);
        ChartCHARTDTO chartCHARTDTO = new ChartCHARTDTO();
        chartCHARTDTO.setPlotBackgroundColor(18l);
        return npsChartDTO;
    }

    @GetMapping("/nps")
    // public NpsChartDTO searchSurvey(@RequestParam String territorialNode, @RequestParam String servicioNode, @RequestParam Date dateBegin, @RequestParam Date dateEnd) {
    public ChartCHARTDTO searchSurveyGet(@RequestParam  String territorialNode) {
        NpsChartDTO npsChartDTO = new NpsChartDTO();
        List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();
        Serie0ChartDTO nerie0ChartDTO = new Serie0ChartDTO();
        List<Object>operationsummaryRescue = new ArrayList<>();
        List<Object> operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'Detractores\',13.29f");
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
        //operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'Promotores\',13f");
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
        // operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add("\'pasivos\',3.29f");
        // operationsummaryRescue.add(operationsummaryRescue2);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);

        series.add(nerie0ChartDTO);
        npsChartDTO.setSeries(series);
        ChartCHARTDTO chartCHARTDTO = new ChartCHARTDTO();
        chartCHARTDTO.setPlotBackgroundColor(18l);
        return chartCHARTDTO;
    }


}
/*
 ['Detractores', 13.29],
         ['Promotores', 13],
         ['pasivos', 3.78]*/
