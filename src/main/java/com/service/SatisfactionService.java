package com.service;

import com.dto.FilterCHARTDTO;
import com.dto.KeyValueCHARTDTO;
import com.dto.piecircle.TitleCHARTDTO;
import com.model.mongo.*;
import com.repository.mongo.*;
import com.tools.Calc;
import com.tools.Constant;
import com.tools.TypeNPS;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by simon on 5/30/2019.
 */
@Service

public class SatisfactionService {


    @Inject
    private SatisfactionRepository satisfactionRepository;

    private int acumdetractor = 0;
    private int acumpromotores = 0;
    private int acumpasivos = 0;
    private float detractorValue = 0f;
    private float promotoresValue = 0f;
    private float pasivosValue = 0f;






    public com.dto.piecircle.SatisfactionGeneralCHARTDTO searchSatisfactionGeneralSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {

        Map<String,Object> npsMap = sgCalc(filterCHARTDTO.getTerritorios(),  filterCHARTDTO.getServicios(), filterCHARTDTO.getStart(),  filterCHARTDTO.getEnd(), filterCHARTDTO.getCompany());
        com.dto.piecircle.SatisfactionGeneralCHARTDTO sg = null;
        if (filterCHARTDTO.isProcesar() ){
            sg =  searchSatisfactionGeneralSurvey((Float)npsMap.get(TypeNPS.DETRACTOR),
                    (Float)npsMap.get(TypeNPS.PROMOTER), (Float)npsMap.get(TypeNPS.PASSIVE)) ;
        }

        return sg;
    }
    public com.dto.piecircle.SatisfactionGeneralCHARTDTO searchSatisfactionGeneralSurvey(float detractores, float promotores, float pasivos) {
        com.dto.piecircle.SatisfactionGeneralCHARTDTO sg = new com.dto.piecircle.SatisfactionGeneralCHARTDTO();
        List<com.dto.piecircle.Serie0ChartDTO> series = new ArrayList<com.dto.piecircle.Serie0ChartDTO>();

        com.dto.piecircle.Serie0ChartDTO nerie0ChartDTO = new com.dto.piecircle.Serie0ChartDTO();
        nerie0ChartDTO.setName("");



        List<Object> operationsummaryRescue2 = new ArrayList<>();

        operationsummaryRescue2.add(new KeyValueCHARTDTO(TypeNPS.PASSIVE, pasivos, Constant.PASIVO_COLOR));
        operationsummaryRescue2.add( new KeyValueCHARTDTO(TypeNPS.PROMOTER, promotores, Constant.PROMOTOR_COLOR));
        operationsummaryRescue2.add(new KeyValueCHARTDTO(TypeNPS.DETRACTOR, detractores, Constant.DETRACTOR_COLOR));

        nerie0ChartDTO.setData(operationsummaryRescue2);
        //sg.setTitle(new com.dto.piecircle.TitleCHARTDTO("Satisfaccion General"));
        series.add(nerie0ChartDTO);
        com.dto.piecircle.TitleCHARTDTO titleCHARTDTO = new TitleCHARTDTO();
        titleCHARTDTO.setText("Satisfaccion General");
        sg.setTitle(titleCHARTDTO);
        sg.setSeries(series);
      /*  ChartCHARTDTO chartCHARTDTO = new ChartCHARTDTO();
        chartCHARTDTO.setPlotBackgroundColor(18l);*/
        return sg;
    }
    public Map<String,Object> sgCalc(List<String> territorials, List<String> marcas, Date start, Date end, Company company) {
        Map<String,Object> result = new HashMap<>();
        List<SatisfactionResponse> detractores = null;
        List<SatisfactionResponse> pasivos = null;
        List<SatisfactionResponse> promotores = null;
        int contPromotor = 0;

        if (start != null && end != null) {
            detractores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, TypeNPS.DETRACTOR,  start,  end, company);
            pasivos = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, TypeNPS.PASSIVE,  start,  end, company);
            promotores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(territorials, marcas, TypeNPS.PROMOTER,  start,  end, company);

        }else if  (start != null) {
            detractores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   TypeNPS.DETRACTOR,  start, company);
            pasivos = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   TypeNPS.PASSIVE,  start, company);
            promotores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(territorials, marcas,   TypeNPS.PROMOTER,  start, company);
        }else if  (end != null) {
            detractores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   TypeNPS.DETRACTOR,  end, company);
            pasivos = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   TypeNPS.PASSIVE,  end, company);
            promotores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(territorials, marcas,   TypeNPS.PROMOTER,  end, company);
        }else  if (start == null && end == null){
            detractores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     TypeNPS.DETRACTOR, company);
            pasivos = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     TypeNPS.PASSIVE, company);
            promotores = satisfactionRepository.findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(territorials, marcas,     TypeNPS.PROMOTER, company);
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



        result.put( TypeNPS.DETRACTOR,detractorValue);
        result.put( TypeNPS.PASSIVE,pasivosValue);
        result.put( TypeNPS.PROMOTER,promotoresValue);
        return result;

    }
}
