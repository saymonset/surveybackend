package com.service;

import com.dto.FilterCHARTDTO;
import com.dto.barrtreestado.*;
import com.model.mongo.AlertResponse;
import com.model.mongo.Company;
import com.model.mongo.TreeModelServicio;
import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.AlertRepository;
import com.tools.Calc;
import com.tools.Constant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by simon on 6/10/2019.
 */
@Service
@Transactional
public class AlertService {
    @Inject
    private TreeTerritorialService treeTerritorialService;
    @Inject
    private TreeServicioService treeServicioService;
    @Inject
    private AlertRepository alertRepository;
    private int acumdetractor = 0;
    private int acumpromotores = 0;
    private int acumpasivos = 0;
    private float detractorValue = 0f;
    private float promotoresValue = 0f;
    private float pasivosValue = 0f;
    private List<String> filterTerritoryChild = null;
    private Serie0ChartDTO serie0ChartDTO = null;
    private  Map<String,List> resultSerie = new HashMap<>();
    private List<String>chilldsServiciosStr;


    public void save(AlertResponse alertResponse){
        alertRepository.save(alertResponse);
    }


    public AlertCHARTDTO alerts(@RequestBody FilterCHARTDTO filterCHARTDTO) {

        AlertCHARTDTO alerts = new AlertCHARTDTO();
        TitleCHARTDTO title = new TitleCHARTDTO("Alertas");
        alerts.setTitle(title);

        YaxisCHARTDTO yaxisCHARTDTO = new YaxisCHARTDTO();
        TitleCHARTDTO tiposAlerts = new TitleCHARTDTO("Tipos de Alertas");
        yaxisCHARTDTO.setTitle(tiposAlerts);
        alerts.setyAxis(yaxisCHARTDTO);

        XaxisCHARTDTO xaxis = new XaxisCHARTDTO();
        List<String> categories = new ArrayList<>();
        /**Solo los hijo para dbujar categorias en la grafica*/
        List<TreeModelTerritorial>onlyChilds = treeTerritorialService.onlyChilds(filterCHARTDTO.getTerritorialNode(), filterCHARTDTO.getCompany());

        /**Dame los ultimos niveles qie son las marcas*/
        List<TreeModelServicio>chilldsServicios = treeServicioService.getMarcaChildsNietos(filterCHARTDTO.getServicioNode(), filterCHARTDTO.getCompany());
        chilldsServiciosStr = new ArrayList<>();
        if (null!= chilldsServicios && chilldsServicios.size() > 0){
            chilldsServicios.forEach((e)-> {
                chilldsServiciosStr.add(e.getNode());
            });
        }

/*
        List<String> goods = toolsSurvey.getAlerta(simplifySurvey,  Constant.ALARMA_GOOD);
        List<String> pasivos = toolsSurvey.getAlerta(simplifySurvey,  Constant.ALARMA_PASIVO);
        List<String> detractors = toolsSurvey.getAlerta(simplifySurvey,  Constant.ALARMA_DETRACTOR);*/



        resultSerie = new HashMap<>();
        onlyChilds.forEach(e->{
            categories.add(e.getValue());
            /**Solo los hijos del nodo*/
            filterTerritoryChild = treeTerritorialService.onlyChildNietosNodeStrind(e.getNode(), filterCHARTDTO.getCompany());
            Map<String,Object> serieInfo = sgCalc(filterTerritoryChild,chilldsServiciosStr, filterCHARTDTO.getStart(),  filterCHARTDTO.getEnd(), filterCHARTDTO.getCompany());
           if (!resultSerie.containsKey(Constant.ALARMA_PASIVO)){
               resultSerie.put(Constant.ALARMA_PASIVO,new ArrayList());
           }
            if (!resultSerie.containsKey(Constant.ALARMA_DETRACTOR)){
                resultSerie.put(Constant.ALARMA_DETRACTOR,new ArrayList());
            }
            if (!resultSerie.containsKey(Constant.ALARMA_GOOD)){
                resultSerie.put(Constant.ALARMA_GOOD,new ArrayList());
            }
            resultSerie.get(Constant.ALARMA_PASIVO).add( serieInfo.get( Constant.ALARMA_PASIVO));
            resultSerie.get(Constant.ALARMA_DETRACTOR).add( serieInfo.get( Constant.ALARMA_DETRACTOR));
            resultSerie.get(Constant.ALARMA_GOOD).add( serieInfo.get( Constant.ALARMA_GOOD));



        });
        xaxis.setCategories(categories);
        alerts.setxAxis(xaxis);


        List<Serie0ChartDTO> series = new ArrayList<>();
        serie0ChartDTO = new Serie0ChartDTO();
        serie0ChartDTO.setName( Constant.ALARMA_PASIVO);
        serie0ChartDTO.setColor(Constant.PASIVO_COLOR);
        serie0ChartDTO.setData(resultSerie.get(Constant.ALARMA_PASIVO));
        series.add(serie0ChartDTO);

        serie0ChartDTO = new Serie0ChartDTO();
        serie0ChartDTO.setName( Constant.ALARMA_DETRACTOR);
        serie0ChartDTO.setColor(Constant.DETRACTOR_COLOR);
        serie0ChartDTO.setData(resultSerie.get(Constant.ALARMA_DETRACTOR));
        series.add(serie0ChartDTO);

        serie0ChartDTO = new Serie0ChartDTO();
        serie0ChartDTO.setName( Constant.ALARMA_GOOD);
        serie0ChartDTO.setColor(Constant.PROMOTOR_COLOR);
        serie0ChartDTO.setData(resultSerie.get(Constant.ALARMA_GOOD));
        series.add(serie0ChartDTO);

        alerts.setSeries(series);


        return alerts;
    }




    public Map<String,Object> sgCalc(List<String> territorials, List<String> marcas, Date start, Date end, Company company) {
        Map<String,Object> result = new HashMap<>();
        List<AlertResponse> detractores = null;
        List<AlertResponse> pasivos = null;
        List<AlertResponse> promotores = null;
        int contPromotor = 0;

        if (start != null && end != null) {
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, Constant.ALARMA_DETRACTOR,  start,  end, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, Constant.ALARMA_PASIVO,  start,  end, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, Constant.ALARMA_GOOD,  start,  end, company);

        }else if  (start != null) {
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   Constant.ALARMA_DETRACTOR,  start, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   Constant.ALARMA_PASIVO,  start, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   Constant.ALARMA_GOOD,  start, company);
        }else if  (end != null) {
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   Constant.ALARMA_DETRACTOR,  end, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   Constant.ALARMA_PASIVO,  end, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   Constant.ALARMA_GOOD,  end, company);
        }else  if (start == null && end == null){
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     Constant.ALARMA_DETRACTOR, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     Constant.ALARMA_PASIVO, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     Constant.ALARMA_GOOD, company);
        }


        acumdetractor = 0;
        acumpromotores = 0;
        acumpasivos = 0;
        detractorValue = 0f;
        promotoresValue = 0f;
        pasivosValue = 0f;

        if (detractores != null && detractores.size() > 0 ){

            detractorValue = detractores.size();
        }

        if (promotores != null && promotores.size() > 0 ){

            promotoresValue =promotores.size();
        }

        if (pasivos != null && pasivos.size() > 0 ){

            pasivosValue = pasivos.size();
        }



        result.put( Constant.ALARMA_DETRACTOR,detractorValue);
        result.put( Constant.ALARMA_PASIVO,pasivosValue);
        result.put( Constant.ALARMA_GOOD,promotoresValue);
        return result;

    }



}
