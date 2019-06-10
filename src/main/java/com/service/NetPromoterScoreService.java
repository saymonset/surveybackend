package com.service;

import com.dto.*;
import com.dto.Serie0ChartDTO;
import com.dto.piecircle.*;
import com.dto.piecircle.TitleCHARTDTO;
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
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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

        NpsChartDTO npsChartDTO = new NpsChartDTO();


        Map<String,Object> npsMap = nps(filterCHARTDTO.getTerritorios(),  filterCHARTDTO.getServicios(), filterCHARTDTO.getStart(),  filterCHARTDTO.getEnd(), filterCHARTDTO.getCompany());

        if (filterCHARTDTO.isProcesar() ){
            npsChartDTO =  npsValuesChart((Float)npsMap.get(typeNPS.detractores),
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
 /*       ChartCHARTDTO chartCHARTDTO = new ChartCHARTDTO();
        chartCHARTDTO.setPlotBackgroundColor(18l);*/
        return npsChartDTO;
    }





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
