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
import com.tools.TypeNPS;
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
        List<String> goods = toolsSurvey.getAlerta(simplifySurvey,  TypeNPS.PROMOTER);
        List<String> PASSIVE = toolsSurvey.getAlerta(simplifySurvey,  TypeNPS.PASSIVE);
        List<String> detractors = toolsSurvey.getAlerta(simplifySurvey,  TypeNPS.DETRACTOR);*/



        resultSerie = new HashMap<>();
        onlyChilds.forEach(e->{
            categories.add(e.getValue());
            /**Solo los hijos del nodo*/
            filterTerritoryChild = treeTerritorialService.onlyChildNietosNodeStrind(e.getNode(), filterCHARTDTO.getCompany());
            Map<String,Object> serieInfo = sgCalc(filterTerritoryChild,chilldsServiciosStr, filterCHARTDTO.getStart(),  filterCHARTDTO.getEnd(), filterCHARTDTO.getCompany());
           if (!resultSerie.containsKey(TypeNPS.PASSIVE)){
               resultSerie.put(TypeNPS.PASSIVE,new ArrayList());
           }
            if (!resultSerie.containsKey(TypeNPS.DETRACTOR)){
                resultSerie.put(TypeNPS.DETRACTOR,new ArrayList());
            }
            if (!resultSerie.containsKey(TypeNPS.PROMOTER)){
                resultSerie.put(TypeNPS.PROMOTER,new ArrayList());
            }
            resultSerie.get(TypeNPS.PASSIVE).add( serieInfo.get( TypeNPS.PASSIVE));
            resultSerie.get(TypeNPS.DETRACTOR).add( serieInfo.get( TypeNPS.DETRACTOR));
            resultSerie.get(TypeNPS.PROMOTER).add( serieInfo.get(TypeNPS.PROMOTER));



        });
        xaxis.setCategories(categories);
        alerts.setxAxis(xaxis);


        List<Serie0ChartDTO> series = new ArrayList<>();
        serie0ChartDTO = new Serie0ChartDTO();
        serie0ChartDTO.setName( TypeNPS.PASSIVE);
        serie0ChartDTO.setColor(Constant.PASIVO_COLOR);
        serie0ChartDTO.setData(resultSerie.get(TypeNPS.PASSIVE));
        series.add(serie0ChartDTO);

        serie0ChartDTO = new Serie0ChartDTO();
        serie0ChartDTO.setName( TypeNPS.DETRACTOR);
        serie0ChartDTO.setColor(Constant.DETRACTOR_COLOR);
        serie0ChartDTO.setData(resultSerie.get(TypeNPS.DETRACTOR));
        series.add(serie0ChartDTO);

        serie0ChartDTO = new Serie0ChartDTO();
        serie0ChartDTO.setName( TypeNPS.PROMOTER);
        serie0ChartDTO.setColor(Constant.PROMOTOR_COLOR);
        serie0ChartDTO.setData(resultSerie.get(TypeNPS.PROMOTER));
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
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, TypeNPS.DETRACTOR,  start,  end, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, TypeNPS.PASSIVE,  start,  end, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, TypeNPS.PROMOTER,  start,  end, company);

        }else if  (start != null) {
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   TypeNPS.DETRACTOR,  start, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   TypeNPS.PASSIVE,  start, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   TypeNPS.PROMOTER,  start, company);
        }else if  (end != null) {
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   TypeNPS.DETRACTOR,  end, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   TypeNPS.PASSIVE,  end, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   TypeNPS.PROMOTER,  end, company);
        }else  if (start == null && end == null){
            detractores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     TypeNPS.DETRACTOR, company);
            pasivos = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     TypeNPS.PASSIVE, company);
            promotores = alertRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     TypeNPS.PROMOTER, company);
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



        result.put( TypeNPS.DETRACTOR,detractorValue);
        result.put( TypeNPS.PASSIVE,pasivosValue);
        result.put( TypeNPS.PROMOTER,promotoresValue);
        return result;

    }



}
