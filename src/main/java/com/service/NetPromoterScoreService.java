package com.service;

import com.dto.ChartCHARTDTO;
import com.dto.FilterCHARTDTO;
import com.dto.NpsChartDTO;
import com.dto.Serie0ChartDTO;
import com.model.mongo.NetPromoterScore;
import com.model.mongo.TreeModelServicio;
import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.NetPromoterScoreRepository;
import com.tools.Calc;
import com.tools.Constant;
import com.tools.DateUtils;
import com.tools.typeNPS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by simon on 6/3/2019.
 */
@Service
public class NetPromoterScoreService {
    @Inject
    private NetPromoterScoreRepository netPromoterScoreRepository;
    @Inject
    private TreeTerritorialService treeTerritorialService;
    @Inject
    private TreeServicioService treeServicioService;

    private int acumdetractor = 0;
    private int acumpromotores = 0;
    private int acumpasivos = 0;
    private float detractorValue = 0f;
    private float promotoresValue = 0f;
    private float pasivosValue = 0f;
    public NpsChartDTO searchNpsSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {
        NpsChartDTO npsChartDTO = new NpsChartDTO();
        List<TreeModelServicio> marcaServicios = null;
        List<TreeModelTerritorial> marcaTerritorios = null;
        Date start = null;
        Date end = null;
        ArrayList<String> territorials = new ArrayList<>();
        ArrayList<String> marcas = new ArrayList<>();
        boolean isProcesar = false;
        if (null != filterCHARTDTO && filterCHARTDTO.getTerritorialNode() != null){
            marcaTerritorios = treeTerritorialService.getMarcaChilds(filterCHARTDTO.getTerritorialNode());
            if (null != marcaTerritorios){
                marcaTerritorios.stream().forEach(e->{
                    territorials.add(e.getDivisionTerritorial());
                });
                isProcesar = true;
            }
        }

        if (null != filterCHARTDTO && filterCHARTDTO.getServicioNode() != null){
            marcaServicios = treeServicioService.getMarcaChilds(filterCHARTDTO.getServicioNode());
            if (null != marcaServicios ){
                marcaServicios.stream().forEach((e->{
                    marcas.add(e.getDivisionServicios());
                }));
                isProcesar = true;
            }
        }

        if (filterCHARTDTO.getDateBegin() != null && filterCHARTDTO.getDateBegin().length() > 0){

            /**
             * String s = "2013-07-17T03:58:00.000Z";
             DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
             Date d = formatter.parse(s);
             * */
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            try {
                Date date = sdf.parse(filterCHARTDTO.getDateBegin());
                start = DateUtils.getStartOfDay(date);
                isProcesar = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        if (filterCHARTDTO.getDateEnd() != null && filterCHARTDTO.getDateEnd().length() > 0){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            try {

                Date date = sdf.parse(filterCHARTDTO.getDateEnd());
                end = DateUtils.getStartOfDay(date);
                isProcesar = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            isProcesar = true;
        }

        Map<String,Object> npsMap = nps(territorials,  marcas, start,  end);

        if (isProcesar ){
            npsChartDTO =  npsValuesChart((Float)npsMap.get(typeNPS.detractores),
                    (Float)npsMap.get(typeNPS.promotores), (Float)npsMap.get(typeNPS.pasivos)) ;
        }

        return npsChartDTO;
    }



    public NpsChartDTO npsValuesChart(float detractores, float promotores, float pasivos) {
        NpsChartDTO npsChartDTO = new NpsChartDTO();
        List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();
        Serie0ChartDTO nerie0ChartDTO = new Serie0ChartDTO();
        List<Object>operationsummaryRescue = new ArrayList<>();
        List<Object> operationsummaryRescue2 = new ArrayList<>();
       // operationsummaryRescue2.add("\'Detractores\',13.29f");
        operationsummaryRescue2.add(typeNPS.detractores + ", " + detractores);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
        //operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add(typeNPS.promotores + ", " + promotores);
       // operationsummaryRescue2.add("\'Promotores\',13f");
        nerie0ChartDTO.getData().add(operationsummaryRescue2);
        // operationsummaryRescue.add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        //operationsummaryRescue2.add("\'pasivos\',3.29f");
        operationsummaryRescue2.add(typeNPS.pasivos + ", " + pasivos);
        // operationsummaryRescue.add(operationsummaryRescue2);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);

        series.add(nerie0ChartDTO);
        npsChartDTO.setSeries(series);
        ChartCHARTDTO chartCHARTDTO = new ChartCHARTDTO();
        chartCHARTDTO.setPlotBackgroundColor(18l);
        return npsChartDTO;
    }

   /* public NpsChartDTO searchNpsSurveyXXX(@RequestBody FilterCHARTDTO filterCHARTDTO) {
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
*/

    public Map<String,Object> nps(List<String> territorials, List<String> marcas, Date start, Date end) {
        Map<String,Object> result = new HashMap<>();
        List<NetPromoterScore> detractores = null;
        List<NetPromoterScore> pasivos = null;
        List<NetPromoterScore> promotores = null;
        int contPromotor = 0;

        if (start != null && end != null) {
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetweenAndType(territorials, marcas,  start,  end, typeNPS.detractores);


            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetweenAndType(territorials, marcas,  start,  end, typeNPS.pasivos);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetweenAndType(territorials, marcas,  start,  end, typeNPS.promotores);

        }
        if (start != null) {
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfterAndType(territorials, marcas,  start,   typeNPS.detractores);
            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfterAndType(territorials, marcas,  start,   typeNPS.pasivos);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfterAndType(territorials, marcas,  start,   typeNPS.promotores);
        }
        if (end != null) {
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBeforeAndType(territorials, marcas,  end,   typeNPS.detractores);
            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBeforeAndType(territorials, marcas,  end,   typeNPS.pasivos);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBeforeAndType(territorials, marcas,  end,   typeNPS.promotores);
        }
        if (start == null && end == null){
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndType(territorials, marcas,     typeNPS.detractores);
            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndType(territorials, marcas,     typeNPS.pasivos);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndType(territorials, marcas,     typeNPS.promotores);
        }


        acumdetractor = 0;
        acumpromotores = 0;
        acumpasivos = 0;
        detractorValue = 0f;
        promotoresValue = 0f;
        pasivosValue = 0f;

        if (detractores != null && detractores.size() > 0 ){
            detractores.forEach(e->{
                acumdetractor += e.getPoint();
            });
            detractorValue = Calc.porcentaje(acumdetractor/detractores.size(), Constant.NUM_BARRA_NPS);
        }

        if (promotores != null && promotores.size() > 0 ){
            promotores.forEach(e->{
                acumpromotores += e.getPoint();
            });
            promotoresValue = Calc.porcentaje(acumpromotores/promotores.size(), Constant.NUM_BARRA_NPS);
        }

        if (pasivos != null && pasivos.size() > 0 ){
            pasivos.forEach(e->{
                acumpasivos += e.getPoint();
            });
            pasivosValue = Calc.porcentaje(acumpasivos/pasivos.size(), Constant.NUM_BARRA_NPS);
        }



        result.put( typeNPS.detractores,detractorValue);
        result.put( typeNPS.pasivos,pasivosValue);
        result.put( typeNPS.promotores,promotoresValue);
        return result;

    }
    /***
     *
     *
     * */
}
