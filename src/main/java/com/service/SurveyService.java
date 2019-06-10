package com.service;

import com.dto.SurveyDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.EmailService;
import com.model.mongo.*;
import com.repository.mongo.*;
import com.tools.Constant;
import com.tools.ToJson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.io.*;
import java.util.*;

/**
 * Created by simon on 5/24/2019.
 */
@Service
@Transactional
public class SurveyService {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Inject
    private SendSurveyRepository mandoEncuestaRepository;
    @Inject
    private SatisfactionService satisfactionService;
    @Inject
    private EmailService emailService;
    private  SurveyDTO  surveyDTOResult = new SurveyDTO();
    @Inject
    private EncuestaRepository encuestaRepository;
    @Inject
    private ToJson toJson;
    @Inject
    private NetPromoterScoreRepository netPromoterScoreRepository;
    @Inject
    private SatisfactionRepository satisfactionRepository;
    @Inject
    private SurveyRepository surveyRepository;
    @Inject
    private RawSurveyRepository rawSurveyRepository;
    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private AlertService alertService;

    @Inject
    private ProcesoRegistroRepository procesoRegistroRepository;

    @Inject
    private ProcesoHabitacionRepository procesoHabitacionRepository;

    @Inject
    private ProcesoPersonalRepository procesoPersonalRepository;
    @Inject
    private ProcesoComidaRepository procesoComidaRepository;

    @Inject
    private ProcesoSalidaRepository procesoSalidaRepository;

    @Inject
    private ToolsSurvey toolsSurvey;


    public void sendSurvey(String codeCompany, String sheet1, int numCol, File file0) throws Exception {
        Company company =   companyRepository.findByCode(codeCompany);
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
                        context.put("urlSurvey","http://localhost:8180/survey?codigoEncuesta="+codigoEncuesta+"&email="+email+"&lang=es" + "&codeCompany="+codeCompany);
                        if (enc == null ){


                            try {
                                boolean sendMail = false;
                                boolean resent = false;

                                SendSurvey sendSurvey = mandoEncuestaRepository.findByCodigoEncuestaAndEmailAndAnsweredAndCompany(codigoEncuesta,email,false, company);
                                if (sendSurvey == null){
                                    sendMail = true;
                                }else if (sendSurvey != null){
                                    resent = true;
                                }
                                if (resent || sendMail){
                                   emailService.send("ecologicalpaper", email, email,  "ecologicalpaper.com", "template/invitacionSurvey.vsl", context) ;
                                }
                                if (sendMail){
                                    mandoEncuesta = new SendSurvey();

                                    mandoEncuesta.setCompany( company);
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




    public Boolean findByCompany(String codeCompany) {
        boolean isExist = false;
        Company company =   companyRepository.findByCode(codeCompany);

        List<Survey> encs  = encuestaRepository.
                findByCompany(company);
        if (encs != null && encs.size() >  0 ){
            isExist = true;
        }
        return isExist;
    }


    public SurveyDTO searchSurvey(@RequestParam String codigoEncuesta, @RequestParam String email, @RequestParam String lang, String codeCompany) {
        Company company =   companyRepository.findByCode(codeCompany);
        SurveyDTO surveyDTO = new SurveyDTO();
        Survey enc  = encuestaRepository.
                findByCodigoEncuestaAndCompany(codigoEncuesta, company);
        if (enc != null){
            String nameFile = enc.getFileEncuesta().substring(0,enc.getFileEncuesta().lastIndexOf("."));
            Resource resource = toJson.getResource(nameFile, lang);
            Map<String, Object> map = toJson.stringToMap(toJson.resourceToString(resource));
            ObjectMapper mapperObj = new ObjectMapper();
            try {
                String jsonResp = mapperObj.writeValueAsString(map);
                surveyDTO.setJson(jsonResp);
                surveyDTO.setLang(lang);
                surveyDTO.setEmail(email);
                surveyDTO.setCodigoEncuesta(codigoEncuesta);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            /**La encuesta la vio*/
            SendSurvey sendSurvey = mandoEncuestaRepository.findByCodigoEncuestaAndEmailAndAnsweredAndCompany(codigoEncuesta,email,false, company);
            sendSurvey.setEmailViewed(true);
            mandoEncuestaRepository.save(sendSurvey);
        }
        return surveyDTO;
    }


    public SurveyDTO procesar( Map<String, Object> response) {

        Map<String, Object> result = (Map<String, Object>) response.get("result");
        Map<String, Object> origin = (Map<String, Object>) response.get("origin");
        Map<String, Object> surveyDTOs = (Map<String, Object>) response.get("surveyDTO");

        String codeCompany = (String )response.get("codeCompany");
        Company company =   companyRepository.findByCode(codeCompany);

        List<Map<String, Object>> questions = toolsSurvey.questions(origin);
        surveyDTOs.forEach((k,v)->
                {
                    if (k.equalsIgnoreCase("email")){
                        surveyDTOResult.setEmail((String)v);
                    }
                    if (k.equalsIgnoreCase("lang")){
                        surveyDTOResult.setLang((String)v);
                    }
                    if (k.equalsIgnoreCase("json")){
                        surveyDTOResult.setJson((String)v);
                    }
                    if (k.equalsIgnoreCase("codigoEncuesta")){
                        surveyDTOResult.setCodigoEncuesta((String)v);
                    }
                   // System.out.println("Item : " + k + " Count : " + v);

                }
        );
        /**La respondio la vio*/
        SendSurvey sendSurvey = mandoEncuestaRepository.findByCodigoEncuestaAndEmailAndAnsweredAndCompany(surveyDTOResult.getCodigoEncuesta(),surveyDTOResult.getEmail(),false, company);
        sendSurvey.setAnswered(true);
        mandoEncuestaRepository.save(sendSurvey);
        /** se almacena la encuesta tal y como viene*/
        RawSurveyResponse rawSurveyResponse = new RawSurveyResponse();
        rawSurveyResponse.setCompany(company);
        rawSurveyResponse.setOrigin(origin);
        rawSurveyResponse.setResult(result);
        rawSurveyResponse.setSendSurvey(sendSurvey);
        rawSurveyResponse.setQuestions(questions);
        rawSurveyRepository.save(rawSurveyResponse);
        /** End se almacena la encuesta tal y como viene*/

        Survey survey = surveyRepository.findByCodigoEncuestaAndCompany(surveyDTOResult.getCodigoEncuesta(), company);

        List<Map<String, Object>> simplifySurvey = toolsSurvey.simplifyAll(result, questions);

        /**Averiguamos las alarmas*/
        List<String> goods = toolsSurvey.getAlerta(simplifySurvey,  Constant.ALARMA_GOOD);
        List<String> pasivos = toolsSurvey.getAlerta(simplifySurvey,  Constant.ALARMA_PASIVO);
        List<String> detractors = toolsSurvey.getAlerta(simplifySurvey,  Constant.ALARMA_DETRACTOR);

        AlertResponse alertResponse = new AlertResponse();
        alertResponse.setSendSurvey(sendSurvey);
        alertResponse.setCompany(company);
        alertResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
        alertResponse.setDivisionServicios(survey.getDivisionServicios());
        alertResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
        alertResponse.setResponsedate(new Date());


        if (goods!=null && goods.size() > 0){
            alertResponse.setType(Constant.ALARMA_GOOD);
            alertResponse.setComment(goods.get(0));
            alertService.save(alertResponse);
        }

        if (pasivos!=null && pasivos.size() > 0){
            alertResponse.setType(Constant.ALARMA_PASIVO);
            alertResponse.setComment(pasivos.get(0));
            alertService.save(alertResponse);
        }

        if (detractors!=null && detractors.size() > 0){
            alertResponse.setType(Constant.ALARMA_DETRACTOR);
            alertResponse.setComment(detractors.get(0));
            alertService.save(alertResponse);
        }


        /**
         *
         *
         procesoRegistroResponse.setType(toolsSurvey.typeSatisfactionGeneralNps(procesoRegistroResponse.getPoint()));
         * */

        /**/


        /**Solo se trata la informacion de la encuesta donde tipo es matrix*/
        getSatisfactionMatrix(simplifySurvey, Constant.PROCESO_REGISTRO, sendSurvey, survey, company);

        getSatisfactionMatrix(simplifySurvey, Constant.PROCESO_HABITACION, sendSurvey, survey, company);

        getSatisfactionMatrix(simplifySurvey, Constant.PROCESO_PERSONAL, sendSurvey, survey, company);

        getSatisfactionMatrix(simplifySurvey, Constant.PROCESO_COMIDA, sendSurvey, survey, company);

        getSatisfactionMatrix(simplifySurvey, Constant.PROCESO_SALIDA, sendSurvey, survey, company);
        /**Fin Solo se trata la informacion de la encuesta donde tipo es matrix*/

        /**Se calcula el nps y satisfaction general rating**/
        int point = toolsSurvey.getSatisfaction(simplifySurvey, Constant.SATISFACTION,"rating") ;
        if (point > 0){
            SatisfactionResponse satisfactionResponse = new SatisfactionResponse();
            satisfactionResponse.setCompany(company);
            satisfactionResponse.setSendSurvey(sendSurvey);
            satisfactionResponse.setCodigoEncuesta(survey.getCodigoEncuesta());
            satisfactionResponse.setDivisionServicios(survey.getDivisionServicios());
            satisfactionResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
            satisfactionResponse.setPoint(point);
            satisfactionResponse.setResponsedate(new Date());
            satisfactionResponse.setType(toolsSurvey.typeSatisfactionGeneralNps(point));
            satisfactionRepository.save(satisfactionResponse);
        }

        NetPromoterScore netPromoterScore = new NetPromoterScore();
        point = toolsSurvey.getSatisfaction(simplifySurvey, Constant.NPS_SCORE,"rating") ;
        if (point > 0) {
            netPromoterScore.setCompany(company);
            netPromoterScore.setSendSurvey(sendSurvey);
            netPromoterScore.setCodigoEncuesta(survey.getCodigoEncuesta());
            netPromoterScore.setDivisionServicios(survey.getDivisionServicios());
            netPromoterScore.setDivisionTerritorial(survey.getDivisionTerritorial());
            netPromoterScore.setPoint(point);
            netPromoterScore.setResponsedate(new Date());
            netPromoterScore.setType(toolsSurvey.typeNpsParent(point));
            netPromoterScoreRepository.save(netPromoterScore);
        }
        /**Fin Se calcula el nps y satisfaction general rating**/

        return surveyDTOResult;
    }

//  getSatisfactionMatrix(simplifySurvey, Constant.PROCESO_REGISTRO, sendSurvey, survey, company);
    public void getSatisfactionMatrix(List<Map<String, Object>> simplifySurvey, String evaluar, SendSurvey sendSurvey, Survey survey, Company company ) {
        float count = 0;
        float satisfaction = 0;
        boolean exist = false;
        String type = "matrix" ;
        for (Map<String, Object> map : simplifySurvey) {

            if (map.containsKey("observer") && String.valueOf(map.get("observer")).equals(evaluar) && String.valueOf(map.get("type")).equals(type)) {
                        String value = (String )map.get("value");
                        StringTokenizer stk = new StringTokenizer(value,"|");
                        while (stk.hasMoreTokens()){
                            String vars = stk.nextToken();
                            StringTokenizer stkPoint = new StringTokenizer(vars,":");
                            while (stkPoint.hasMoreTokens()){
                                String text = stkPoint.nextToken();
                                String value0 = stkPoint.nextToken();
                                int  point = Integer.valueOf(stkPoint.nextToken());
                                if (Constant.PROCESO_REGISTRO.equalsIgnoreCase(evaluar)){
                                    ProcesoRegistroResponse procesoRegistroResponse = new ProcesoRegistroResponse();
                                    procesoRegistroResponse.setCompany(company);
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(toolsSurvey.typeSatisfactionGeneralNps(procesoRegistroResponse.getPoint()));
                                    procesoRegistroRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_HABITACION.equalsIgnoreCase(evaluar)){

                                    ProcesoHabitacionResponse procesoRegistroResponse = new ProcesoHabitacionResponse();
                                    procesoRegistroResponse.setCompany(company);
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(toolsSurvey.typeSatisfactionGeneralNps(procesoRegistroResponse.getPoint()));
                                    procesoHabitacionRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_PERSONAL.equalsIgnoreCase(evaluar)){
                                    ProcesoPersonalResponse procesoRegistroResponse = new ProcesoPersonalResponse();
                                    procesoRegistroResponse.setCompany(company);
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(toolsSurvey.typeSatisfactionGeneralNps(procesoRegistroResponse.getPoint()));
                                    procesoPersonalRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_COMIDA.equalsIgnoreCase(evaluar)){
                                    ProcesoComidaResponse procesoRegistroResponse = new ProcesoComidaResponse();
                                    procesoRegistroResponse.setCompany(company);
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(toolsSurvey.typeSatisfactionGeneralNps(procesoRegistroResponse.getPoint()));
                                    procesoComidaRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_SALIDA.equalsIgnoreCase(evaluar)){
                                    ProcesoSalidaResponse procesoRegistroResponse = new ProcesoSalidaResponse();
                                    procesoRegistroResponse.setCompany(company);
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(toolsSurvey.typeSatisfactionGeneralNps(procesoRegistroResponse.getPoint()));
                                    procesoSalidaRepository.save(procesoRegistroResponse);
                                }

                            }
                        }
            }
        }
    }






}
