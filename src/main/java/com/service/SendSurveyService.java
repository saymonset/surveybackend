package com.service;

import com.dto.SurveyDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.EmailService;
import com.model.mongo.SendSurvey;
import com.model.mongo.Survey;
import com.repository.mongo.EncuestaRepository;
import com.repository.mongo.SendSurveyRepository;
import com.tools.ResourcePaths;
import com.tools.ToJson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by simon on 5/24/2019.
 */
@Service
public class SendSurveyService {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Inject
    private SendSurveyRepository mandoEncuestaRepository;
    @Inject
    private EmailService emailService;

    @Inject
    private EncuestaRepository encuestaRepository;
    @Inject
    private ToJson toJson;

    public void sendSurvey(String sheet1, int numCol, File file0) throws Exception {
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        FileInputStream file = null;
        String parentStr = null;
        String parentNode = null;
        String value = null;
        List<String[]> rowFound = new ArrayList<String[]>();
        SendSurvey mandoEncuesta = null;
        int i=0;
        try {
            file = new FileInputStream(file0);
            // Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            // Get first sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(sheet1);
            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();
            Cell cell = null;
            boolean titleRow = true;
            while (rowIterator.hasNext()) {

                Row row = (Row) rowIterator.next();
                if (row == null){
                    break;
                }

                int node = 0;

                /**Si son los titulos , que es la [rimera fila.. descartar*/
                if (titleRow){
                    titleRow = false;
                    continue;
                }
                while (node < numCol) {
                    // Update the value of cell
                    cell = row.getCell(node);
                    if (cell == null){
                        break;
                    }

                    if (cell!=null){


                        /**Si no existe el parentNode.. lo creamos*/
                        String name = row.getCell(node++).getStringCellValue();
                        String lastName = row.getCell( node++).getStringCellValue();
                        String email = row.getCell( node++).getStringCellValue();
                        String codigoEncuesta = row.getCell( node++).getStringCellValue();
                        SendSurvey enc  = null;//mandoEncuestaRepository.findByNode("-1");
                        VelocityContext context = new VelocityContext();
                        context.put("clientName",lastName + ", " + name);
                        context.put("urlSurvey","http://localhost:8180/survey?codigoEncuesta="+codigoEncuesta+"&email="+email+"&lang=es");
                        if (enc == null ){


                            try {
                                boolean sendMail = false;
                                boolean resent = false;

                                SendSurvey sendSurvey = mandoEncuestaRepository.findByCodigoEncuestaAndEmailAndAnswered(codigoEncuesta,email,false);
                                if (sendSurvey == null){
                                    sendMail = true;
                                }else if (sendSurvey != null){
                                    resent = true;
                                }





                                if (resent || sendMail){
                                    emailService.send("saymon_set@hotmail.com", email, email,  "ecologicalpaper.com", "template/invitacionSurvey.vsl", context) ;
                                }





                                if (sendMail){
                                    mandoEncuesta = new SendSurvey();
                                    mandoEncuesta.setName(name);
                                    mandoEncuesta.setLastName(lastName);
                                    mandoEncuesta.setEmail(email);
                                    mandoEncuesta.setCodigoEncuesta(codigoEncuesta);
                                    mandoEncuesta.setCreatedAt(new Date());
                                    mandoEncuesta.setAnswered(false);
                                    mandoEncuesta.setEmailViewed(false);
                                    mandoEncuestaRepository.save(mandoEncuesta);
                                }else if (resent){
                                    Optional<SendSurvey> ssendSurvey =  mandoEncuestaRepository.findById(sendSurvey.getId());
                                    /**
                                     * Optional<Foo> result = repository.findById(…);

                                     result.ifPresent(it -> …); // do something with the value if present
                                     result.map(it -> …); // map the value if present
                                     Foo foo = result.orElse(null); // if you want to continue just like before
                                     * */
                                    ssendSurvey.ifPresent(it -> {
                                        sendSurvey.setCountResent(sendSurvey.getCountResent() + 1);
                                        sendSurvey.setResentAt(new Date());
                                        mandoEncuestaRepository.save(sendSurvey);
                                    });

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                        node += 5;
                    }
                }
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }

    public SurveyDTO procesar(@RequestParam String codigoEncuesta, @RequestParam String email, @RequestParam String lang) {
        SurveyDTO surveyDTO = new SurveyDTO();
        Survey enc  = encuestaRepository.
                findByCodigoEncuesta(codigoEncuesta);
        if (enc != null){
            String nameFile = enc.getFileEncuesta().substring(0,enc.getFileEncuesta().lastIndexOf("."));
            Resource resource = toJson.getResource(nameFile, lang);
            Map<String, Object> map = toJson.stringToMap(toJson.resourceToString(resource));
            ObjectMapper mapperObj = new ObjectMapper();
            try {
                String jsonResp = mapperObj.writeValueAsString(map);
                surveyDTO.setJson(jsonResp);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            surveyDTO.setLang(lang);
            surveyDTO.setEmail(email);
            /**La encuesta la vio*/
            SendSurvey sendSurvey = mandoEncuestaRepository.findByCodigoEncuestaAndEmailAndAnswered(codigoEncuesta,email,false);
            sendSurvey.setEmailViewed(true);
            mandoEncuestaRepository.save(sendSurvey);
        }
        return surveyDTO;
    }





}
