package com.rest;

import com.dto.SurveyDTO;
import com.payload.UploadFileResponse;
import com.service.SendSurveyService;
import com.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 5/24/2019.
 */
@RestController
@RequestMapping("/survey")
public class SendSurveyRest {

    private Logger logger =  LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    private SendSurveyService mandoEncuestaService;

    @PostMapping("/send")
    public UploadFileResponse sendSurvey(@RequestParam("file") MultipartFile file) {

        try {
            File file1 = FileTool.convert(file);
            mandoEncuestaService.sendSurvey("mandarEncuesta",4,file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UploadFileResponse();
    }


    @RequestMapping(value = "/sent", method = RequestMethod.GET)
    public SurveyDTO procesar(@RequestParam String codigoEncuesta, @RequestParam String email, @RequestParam String lang) {
        SurveyDTO  surveyDTO = new SurveyDTO();
        surveyDTO.setCodigoEncuesta(codigoEncuesta);
        surveyDTO.setEmail(email);
        surveyDTO.setJson(lang);
        surveyDTO.setLang(lang);
        return  surveyDTO;
    }

    /**
     * Metodo que realiza la busqueda de survey segun el idioma
     * @param surveyId id de survey
     * @param lang el idioma de la plantilla
     * @return ResponseEntity La respuesta a la vista
     */
    public ResponseEntity<?> requestCommon(@PathVariable String email,String lang) {

        ResponseEntity<?> response;

        Map<String, Object> result = new HashMap<>();
        result.put("lang", lang);
        result.put("email", email);
        result.put("viewed", true);
        response =  new ResponseEntity<>(result, HttpStatus.CREATED);
        return response;

    }


    @RequestMapping(value = "/sent/result",method = RequestMethod.POST)
    public ResponseEntity<?> response(@RequestBody Map<String, Object> response) {
        SurveyDTO  surveyDTO = new SurveyDTO();
     //   surveyResponseService.saveResponse(response);
        Map<String, Object> result = (Map<String, Object>) response.get("result");
        return new ResponseEntity<>(surveyDTO,HttpStatus.CREATED);
    }


}
