package com.rest;

import com.payload.UploadFileResponse;
import com.service.SendSurveyService;
import com.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by simon on 5/24/2019.
 */
@RestController
@RequestMapping("/send")
public class SendSurveyRest {

    private Logger logger =  LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    private SendSurveyService mandoEncuestaService;

    @PostMapping("/survey")
    public UploadFileResponse sendSurvey(@RequestParam("file") MultipartFile file) {

        try {
            File file1 = FileTool.convert(file);
            mandoEncuestaService.sendSurvey("mandarEncuesta",5,file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UploadFileResponse();
    }



}
