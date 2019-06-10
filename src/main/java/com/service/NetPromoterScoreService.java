package com.service;

import com.dto.*;
import com.model.mongo.Company;
import com.model.mongo.NetPromoterScore;
import com.model.mongo.TreeModelServicio;
import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.CompanyRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
    @Inject
    private CompanyRepository companyRepository;

    private int acumdetractor = 0;
    private int acumpromotores = 0;
    private int acumpasivos = 0;
    private float detractorValue = 0f;
    private float promotoresValue = 0f;
    private float pasivosValue = 0f;

    public NpsChartDTO searchNpsSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {

        Company company =   companyRepository.findByCode(filterCHARTDTO.getCodeCompany());
        NpsChartDTO npsChartDTO = new NpsChartDTO();
        List<TreeModelServicio> marcaServicios = null;
        List<TreeModelTerritorial> marcaTerritorios = null;
        Date start = null;
        Date end = null;
        ArrayList<String> territorials = new ArrayList<>();
        ArrayList<String> marcas = new ArrayList<>();
        boolean isProcesar = false;
        if (null != filterCHARTDTO && filterCHARTDTO.getTerritorialNode() == null){
            filterCHARTDTO.setTerritorialNode(treeTerritorialService.getTree(company).getNode());
        }
        if (null != filterCHARTDTO && filterCHARTDTO.getTerritorialNode() != null){
            marcaTerritorios = treeTerritorialService.getMarcaChilds(filterCHARTDTO.getTerritorialNode(), company);
            if (null != marcaTerritorios){
                marcaTerritorios.stream().forEach(e->{
                    territorials.add(e.getDivisionTerritorial());
                });
                isProcesar = true;
            }
        }

        if (null != filterCHARTDTO && filterCHARTDTO.getServicioNode() == null){
            filterCHARTDTO.setServicioNode(treeServicioService.getTree(company).getNode());
        }

        if (null != filterCHARTDTO && filterCHARTDTO.getServicioNode() != null){
            marcaServicios = treeServicioService.getMarcaChilds(filterCHARTDTO.getServicioNode(), company);
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
           end = DateUtils.getEndOfDay( date);
                isProcesar = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            isProcesar = true;
        }

        Map<String,Object> npsMap = nps(territorials,  marcas, start,  end, company);

        if (isProcesar ){
            npsChartDTO =  npsValuesChart((Float)npsMap.get(typeNPS.detractores),
                    (Float)npsMap.get(typeNPS.promotores), (Float)npsMap.get(typeNPS.pasivos)) ;
        }

        return npsChartDTO;
    }



    public com.dto.piecircle.SatisfactionGeneralCHARTDTO searchSatisfactionGeneralSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {

        Company company =   companyRepository.findByCode(filterCHARTDTO.getCodeCompany());
       // SatisfactionGeneralCHARTDTO npsChartDTO = new SatisfactionGeneralCHARTDTO();
        List<TreeModelServicio> marcaServicios = null;
        List<TreeModelTerritorial> marcaTerritorios = null;
        Date start = null;
        Date end = null;
        ArrayList<String> territorials = new ArrayList<>();
        ArrayList<String> marcas = new ArrayList<>();
        boolean isProcesar = false;
        if (null != filterCHARTDTO && filterCHARTDTO.getTerritorialNode() == null){
            filterCHARTDTO.setTerritorialNode(treeTerritorialService.getTree(company).getNode());
        }
        if (null != filterCHARTDTO && filterCHARTDTO.getTerritorialNode() != null){
            marcaTerritorios = treeTerritorialService.getMarcaChilds(filterCHARTDTO.getTerritorialNode(), company);
            if (null != marcaTerritorios){
                marcaTerritorios.stream().forEach(e->{
                    territorials.add(e.getDivisionTerritorial());
                });
                isProcesar = true;
            }
        }

        if (null != filterCHARTDTO && filterCHARTDTO.getServicioNode() == null){
            filterCHARTDTO.setServicioNode(treeServicioService.getTree(company).getNode());
        }

        if (null != filterCHARTDTO && filterCHARTDTO.getServicioNode() != null){
            marcaServicios = treeServicioService.getMarcaChilds(filterCHARTDTO.getServicioNode(), company);
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
                end = DateUtils.getEndOfDay( date);
                isProcesar = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            isProcesar = true;
        }

        Map<String,Object> npsMap = nps(territorials,  marcas, start,  end, company);
        com.dto.piecircle.SatisfactionGeneralCHARTDTO npsChartDTO = null;
       if (isProcesar ){
            npsChartDTO =  searchSatisfactionGeneralSurvey((Float)npsMap.get(typeNPS.detractores),
                    (Float)npsMap.get(typeNPS.promotores), (Float)npsMap.get(typeNPS.pasivos)) ;
        }

        return npsChartDTO;
    }

    public NpsChartDTO npsValuesChart(float detractores, float promotores, float pasivos) {
        NpsChartDTO npsChartDTO = new NpsChartDTO();
        List<Serie0ChartDTO> series = new ArrayList<Serie0ChartDTO>();

        Serie0ChartDTO nerie0ChartDTO = new Serie0ChartDTO();

        List<Object> operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add(typeNPS.promotores + ", " + promotores);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add(typeNPS.pasivos + ", " + pasivos);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);

        operationsummaryRescue2 = new ArrayList<>();
        operationsummaryRescue2.add(typeNPS.detractores + ", " + detractores);
        nerie0ChartDTO.getData().add(operationsummaryRescue2);


        series.add(nerie0ChartDTO);
        npsChartDTO.setSeries(series);
        ChartCHARTDTO chartCHARTDTO = new ChartCHARTDTO();
        chartCHARTDTO.setPlotBackgroundColor(18l);
        return npsChartDTO;
    }


    public com.dto.piecircle.SatisfactionGeneralCHARTDTO searchSatisfactionGeneralSurvey(float detractores, float promotores, float pasivos) {
        com.dto.piecircle.SatisfactionGeneralCHARTDTO npsChartDTO = new com.dto.piecircle.SatisfactionGeneralCHARTDTO();
        List<com.dto.piecircle.Serie0ChartDTO> series = new ArrayList<com.dto.piecircle.Serie0ChartDTO>();

        com.dto.piecircle.Serie0ChartDTO nerie0ChartDTO = new com.dto.piecircle.Serie0ChartDTO();
        nerie0ChartDTO.setName("Share");



        List<Object> operationsummaryRescue2 = new ArrayList<>();

        operationsummaryRescue2.add(new KeyValueCHARTDTO(typeNPS.pasivos, pasivos, "#f6ed16"));
        operationsummaryRescue2.add( new KeyValueCHARTDTO(typeNPS.promotores, promotores, "#32f253"));
        operationsummaryRescue2.add(new KeyValueCHARTDTO(typeNPS.detractores, detractores, "#0f0101"));

        nerie0ChartDTO.setData(operationsummaryRescue2);

        series.add(nerie0ChartDTO);
        npsChartDTO.setSeries(series);
      /*  ChartCHARTDTO chartCHARTDTO = new ChartCHARTDTO();
        chartCHARTDTO.setPlotBackgroundColor(18l);*/
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

    public Map<String,Object> nps(List<String> territorials, List<String> marcas, Date start, Date end, Company company) {
        Map<String,Object> result = new HashMap<>();
        List<NetPromoterScore> detractores = null;
        List<NetPromoterScore> pasivos = null;
        List<NetPromoterScore> promotores = null;
        int contPromotor = 0;

        if (start != null && end != null) {
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, typeNPS.detractores,  start,  end, company);
            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, typeNPS.pasivos,  start,  end, company);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, typeNPS.promotores,  start,  end, company);

        }else if  (start != null) {
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   typeNPS.detractores,  start, company);
            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   typeNPS.pasivos,  start, company);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   typeNPS.promotores,  start, company);
        }else if  (end != null) {
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   typeNPS.detractores,  end, company);
            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   typeNPS.pasivos,  end, company);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   typeNPS.promotores,  end, company);
        }else  if (start == null && end == null){
            detractores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     typeNPS.detractores, company);
            pasivos = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     typeNPS.pasivos, company);
            promotores = netPromoterScoreRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     typeNPS.promotores, company);
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
