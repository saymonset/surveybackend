package com.service;

import com.model.mongo.*;
import com.repository.mongo.*;
import com.tools.Constant;
import com.tools.typeNPS;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by simon on 5/30/2019.
 */
@Service
public class SatisfactionService {
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

    public String typeNps(float value){
        String type = "";
        if (value >= 3){
            type = typeNPS.promotores;
        }else if (value == 2){
            type = typeNPS.pasivos;
        }else if (value == 1){
            type = typeNPS.detractores;
        }
        return type;
    }


    public void getSatisfactionMatrix(List<Map<String, Object>> simplifySurvey, String evaluar, SendSurvey sendSurvey, Survey survey ) {
        float count = 0;
        float satisfaction = 0;
        boolean exist = false;
        String type = "matrix" ;
        for (Map<String, Object> map : simplifySurvey) {

            if (map.containsKey("observer") && String.valueOf(map.get("observer")).equals(evaluar) && String.valueOf(map.get("type")).equals(type)) {
                switch (type) {

                    case "matrix":

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
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(typeNps(procesoRegistroResponse.getPoint()));
                                    procesoRegistroRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_HABITACION.equalsIgnoreCase(evaluar)){

                                    ProcesoHabitacionResponse procesoRegistroResponse = new ProcesoHabitacionResponse();
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(typeNps(procesoRegistroResponse.getPoint()));
                                    procesoHabitacionRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_PERSONAL.equalsIgnoreCase(evaluar)){
                                    ProcesoPersonalResponse procesoRegistroResponse = new ProcesoPersonalResponse();
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(typeNps(procesoRegistroResponse.getPoint()));
                                    procesoPersonalRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_COMIDA.equalsIgnoreCase(evaluar)){
                                    ProcesoComidaResponse procesoRegistroResponse = new ProcesoComidaResponse();
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(typeNps(procesoRegistroResponse.getPoint()));
                                    procesoComidaRepository.save(procesoRegistroResponse);
                                }

                                if (Constant.PROCESO_SALIDA.equalsIgnoreCase(evaluar)){
                                    ProcesoSalidaResponse procesoRegistroResponse = new ProcesoSalidaResponse();
                                    procesoRegistroResponse.setCodigoEncuesta(sendSurvey.getCodigoEncuesta());
                                    procesoRegistroResponse.setDivisionServicios(survey.getDivisionServicios());
                                    procesoRegistroResponse.setDivisionTerritorial(survey.getDivisionTerritorial());
                                    procesoRegistroResponse.setPoint(point);
                                    procesoRegistroResponse.setResponsedate(new Date());
                                    procesoRegistroResponse.setSendSurvey(sendSurvey);
                                    procesoRegistroResponse.setText(text);
                                    procesoRegistroResponse.setValue(value0);
                                    procesoRegistroResponse.setType(typeNps(procesoRegistroResponse.getPoint()));
                                    procesoSalidaRepository.save(procesoRegistroResponse);
                                }

                            }
                        }

                        break;
                    default:
                        break;
                }
            }
        }


    }

    public Integer getSatisfaction(List<Map<String, Object>> simplifySurvey, String evaluar, String type) {
        float count = 0;
        float satisfaction = 0;
        boolean exist = false;
        int point = 0;

        for (Map<String, Object> map : simplifySurvey) {
          /*  if (map.containsKey("observer") && String.valueOf(map.get("observer")).equals(evaluar) && String.valueOf(map.get("type")).equals(type)) {
                count++;
                satisfaction += Float.valueOf(String.valueOf(map.get("value")));
                exist = true;
            }*/
            if (map.containsKey("observer") && String.valueOf(map.get("observer")).equals(evaluar) && String.valueOf(map.get("type")).equals(type)) {
                switch (type) {
                    case "radiogroup":
                        ArrayList choicesRadiogroup = (ArrayList) map.get("choices");
                        for (Object choice : choicesRadiogroup) {
                            Map<String, Object> choiceMap = (Map<String, Object>) choice;
                            count++;
                           // satisfaction += Float.valueOf(String.valueOf(choiceMap.get("value")));
                            try {
                                satisfaction +=  Float.valueOf(String.valueOf(choiceMap.get("value")));
                            }catch(Exception e){}

                        }
                        break;
                    case "text":
                        count++;
                       // satisfaction += Float.valueOf(String.valueOf(map.get("value")));
                        try {
                            satisfaction += Float.valueOf(String.valueOf(map.get("value")));
                        }catch(Exception e){}
                        break;
                    case "comment":
                        //result.put("value", value);
                        break;
                    case "html":
                        return null;
                    case "rating":
                        count++;
                        try {
                            satisfaction += Float.valueOf(String.valueOf(map.get("value")));
                        }catch(Exception e){}

                        break;
                    case "matrix":

                        String value = (String )map.get("value");
                        System.out.println("");
                        ArrayList columns = (ArrayList) map.get("columns");
                        for (Object col : columns) {
                            Map<String, Object> data = (Map<String, Object>) col;

                            satisfaction += Float.valueOf((Integer)data.get("value"));
                            count++;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
/*
        case "matrix":
        ArrayList rows = (ArrayList) response.get("rows");*/

        if (count > 0) {
            Float calc = count != 0 ? satisfaction / count : 0;
            point = calc.intValue();
            return point;
        }
        return 0;
    }
    public List<Map<String, Object>> simplifyAll(Map<String, Object> responses, List<Map<String, Object>> questions) {
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> nameSet = responses.keySet();
        for (String name : nameSet) {
            for (Map<String, Object> question : questions) {
                if (question.get("name").equals(name)) {
                    Map<String, Object> simplify = simplify(question, String.valueOf(responses.get(name)),responses, name);
                    if (simplify != null) {
                        result.add(simplify);
                    }
                }
            }
        }
       // result = validaTypeMatrix(responses, result);
        return result;
    }



    public Map<String, Object> simplify(Map<String, Object> simplyfy, String value, Map<String, Object> response, String key) {
        Map<String, Object> result = simplyfy;
        if (String.valueOf(simplyfy.get("name")).equals("alerta_no_contactar")) {
            result.put("value", "No quiero ser contactado");
        }
        switch (String.valueOf(simplyfy.get("type"))) {
            case "radiogroup":
                ArrayList choicesRadiogroup = (ArrayList) simplyfy.get("choices");
                for (Object choice : choicesRadiogroup) {
                    Map<String, Object> choiceMap = (Map<String, Object>) choice;
                    if (String.valueOf(choiceMap.get("value")).endsWith(value)) {
                        result.put("value", String.valueOf(choiceMap.get("value")));
                    }
                }
                break;
            case "text":
                result.put("value", value);
                break;
            case "comment":
                result.put("value", value);
                break;
            case "html":
                return null;
            case "rating":
                result.put("value", Integer.valueOf(value));
                break;
            case "matrix":
                ArrayList rows = (ArrayList) simplyfy.get("rows");
                StringBuilder sbPoint = new StringBuilder();
                StringBuilder sbRow = new StringBuilder();
                StringBuilder sbValueRow = new StringBuilder();
                StringBuilder sbCol = new StringBuilder();
                StringBuilder sbValueCol = new StringBuilder();
                /**Valores  que ya tengo de la encuesta.. su key y value selccionados por el usuario**/
                Object valueSurveyMap= response.get(key);
                Map<String, Object> valueSurvey = (Map<String, Object>) valueSurveyMap;

                Set<String> nameSet = valueSurvey.keySet();
                StringBuilder str = new StringBuilder();
                boolean isPrimeraVez=true;
                ArrayList columnsObj = null;
                String textRow = "";
                String value0Row = "";
                String valueCol = "";
                String texCol = "";
                String point = "0";
                for (String responseKey : nameSet) {
                    for (Object row : rows) {
                        Map<String,Object> rowMap = (Map<String,Object>)row;

                        if (rowMap.get("value").equals(responseKey)){
                            textRow = (String)rowMap.get("text");
                            value0Row = (String)rowMap.get("value");
                            point = (String)valueSurvey.get(responseKey);
                            if (!isPrimeraVez){
                                sbPoint.append("| ");
                            }
                            sbPoint.append(textRow).append(":")
                                    .append(value0Row).append(":")
                                    .append(point);
                            isPrimeraVez = false;
                        }




                        //responses.get(mapa.get("value"));

                    }

                }


                result.put("value", sbPoint.toString());
                break;
            default:
                break;
        }
        return result;
    }

    public List<Map<String, Object>> questions(Map<String, Object> survey) {
        List<Map<String, Object>> result = new ArrayList<>();
        ArrayList pages = (ArrayList) survey.get("pages");
        for (Object page : pages) {
            Map<String, Object> pageMap = (Map<String, Object>) page;
            ArrayList elements = (ArrayList) pageMap.get("elements");
            elements.forEach((element) -> {
                result.add((Map<String, Object>) element);
            });
        }
        return result;
    }
}
