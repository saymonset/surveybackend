package com.rest;

import com.dto.*;
import com.security.SecurityConstants;
import com.security_delete.SecurityUtils;
import com.service.NetPromoterScoreService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by simon on 5/31/2019.
 */
@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class SearchRest {
    @Inject
    private NetPromoterScoreService searchService;



    //@RequestMapping(value = "/nps", method = RequestMethod.POST)
    @PostMapping("/nps")
   // public NpsChartDTO searchSurvey(@RequestParam String territorialNode, @RequestParam String servicioNode, @RequestParam Date dateBegin, @RequestParam Date dateEnd) {
    public NpsChartDTO searchNpsSurvey(@RequestBody FilterCHARTDTO filterCHARTDTO) {
        String login = SecurityUtils.getCurrentLogin();
        return searchService.searchNpsSurvey(filterCHARTDTO);
    }




}
/*
 ['Detractores', 13.29],
         ['Promotores', 13],
         ['pasivos', 3.78]*/
