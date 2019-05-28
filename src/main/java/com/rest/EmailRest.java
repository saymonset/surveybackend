package com.rest;

import com.mail.EmailService;
import com.payload.UploadFileResponse;
import com.tools.FileTool;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 5/24/2019.
 */
@RestController
@RequestMapping("/sendEmail")
public class EmailRest {
    @Inject
    private EmailService emailService;

    private Logger logger =  LoggerFactory.getLogger(this.getClass().getName());


    @PostMapping("/survey")
    public UploadFileResponse sendSurvey() {

/*
Map<String,Object> map = new HashMap();
map.put("clientName","SIMON ALBERTO RODRIGUEZ PACHECIO");
*/
             VelocityContext context = new VelocityContext();
            context.put("clientName","SIMON ALBERTO RODRIGUEZ PACHECIO");
        try {
            emailService.send("saymon_set@hotmail.com", "saymon_set@hotmail.com", "saymon_set@hotmail.com",  "saymon_set@hotmail.com", "template/invitacionSurvey.vsl", context) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UploadFileResponse();
    }

}
