package com.rest;

import com.dto.*;
import com.dto.barrtreestado.AlertCHARTDTO;
import com.model.mongo.Company;
import com.model.mongo.TreeModelServicio;
import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.CompanyRepository;
import com.service.*;
import com.tools.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 5/31/2019.
 */
@RestController
@RequestMapping("/widget")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class WidgetRest {
    @Inject
    private NetPromoterScoreService netPromoterScoreService;
    @Inject
    private SatisfactionService satisfactionService;
    @Inject
    private TreeTerritorialService treeTerritorialService;
    @Inject
    private TreeServicioService treeServicioService;
    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private AlertService alertService;


    @PostMapping("/nps")
    public NpsChartDTO searchNpsSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {
        filterCHARTDTO = SearchFiltro(filterCHARTDTO);
        return netPromoterScoreService.searchNpsSurvey(filterCHARTDTO);
    }

    @PostMapping("/satisfactionGeneral")
    public com.dto.piecircle.SatisfactionGeneralCHARTDTO searchSatisfactionGeneralSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {
        filterCHARTDTO = SearchFiltro(filterCHARTDTO);
        return satisfactionService.searchSatisfactionGeneralSurvey(filterCHARTDTO);
    }

    @PostMapping("/alerts")
    public AlertCHARTDTO alerts(@RequestBody FilterCHARTDTO filterCHARTDTO) {
        filterCHARTDTO = SearchFiltro(filterCHARTDTO);
        return alertService.alerts(filterCHARTDTO);
    }


    public FilterCHARTDTO SearchFiltro(@RequestBody FilterCHARTDTO filterCHARTDTO) {

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
            marcaTerritorios = treeTerritorialService.getMarcaChildsNietos(filterCHARTDTO.getTerritorialNode(), company);
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
            marcaServicios = treeServicioService.getMarcaChildsNietos(filterCHARTDTO.getServicioNode(), company);
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
        filterCHARTDTO.setProcesar(isProcesar);
        filterCHARTDTO.setTerritorios(territorials);
        filterCHARTDTO.setServicios(marcas);
        filterCHARTDTO.setStart(start);
        filterCHARTDTO.setEnd(end);
        filterCHARTDTO.setCompany(company);

        return filterCHARTDTO;
    }



}
/*
 ['Detractores', 13.29],
         ['Promotores', 13],
         ['pasivos', 3.78]*/
