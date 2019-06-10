package com.rest;

import com.dto.SurveyDTO;
import com.payload.UploadFileResponse;
import com.service.SurveyService;
import com.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.util.Map;

/**
 * Created by simon on 5/24/2019.
 */
@RestController
@RequestMapping("/survey")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class SurveyRest {

    private Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    private  SurveyDTO  surveyDTOResult = new SurveyDTO();
    @Inject
    private SurveyService sendSurveyService;

    @PostMapping("/send")
    public UploadFileResponse sendSurvey(@RequestParam("file") MultipartFile file, @RequestParam("codeCompany") String codeCompany) {

        try {
            File file1 = FileTool.convert(file);
            //sendSurvey(String codeCompany, String sheet1, int numCol, File file0) throws Exception {
            sendSurveyService.sendSurvey(codeCompany,"mandarEncuesta",4,file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UploadFileResponse();
    }


    @RequestMapping(value = "/searchSurvey", method = RequestMethod.GET)
    public SurveyDTO searchSurvey(@RequestParam String codigoEncuesta, @RequestParam String email, @RequestParam String lang,@RequestParam String codeCompany ) {
        SurveyDTO surveyDTO = sendSurveyService.searchSurvey(codigoEncuesta, email,  lang, codeCompany);
        return  surveyDTO;
    }

    @RequestMapping(value = "/existSurveyBd", method = RequestMethod.GET)
    public Boolean existSurveyBd(@RequestParam String codeCompany ) {
        boolean result = sendSurveyService.findByCompany(codeCompany );
        return result;
    }

    @RequestMapping(value = "/sent/result",method = RequestMethod.POST)
    public ResponseEntity<?> procesar(@RequestBody Map<String, Object> response) {

        surveyDTOResult = sendSurveyService.procesar( response);

        return new ResponseEntity<>(surveyDTOResult,HttpStatus.CREATED);
    }


}
