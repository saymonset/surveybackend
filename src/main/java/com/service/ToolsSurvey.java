package com.service;

import com.tools.typeNPS;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by simon on 6/10/2019.
 */
@Service
public class ToolsSurvey {

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

    public List<String> getAlerta(List<Map<String, Object>> simplifySurvey, String alerta) {
        List<String> result = new ArrayList<>();
        for (Map<String, Object> map : simplifySurvey) {
            if (String.valueOf(map.get("type")).equals("comment") && map.containsKey("alarma") && String.valueOf(map.get("alarma")).equals(alerta)) {
                result.add(String.valueOf(map.get("value")));
            }
        }
        return result;
    }

    public String typeSatisfactionGeneralNps(float value){
        String type = "";
        if (value == 5){
            type = typeNPS.promotores;
        }else if (value == 3 || value == 4){
            type = typeNPS.pasivos;
        }else if (value < 3){
            type = typeNPS.detractores;
        }
        return type;
    }

    public String typeNpsParent(float value){
        String npsType =typeNPS.pasivos;
        if (value < 7) {
            npsType =  typeNPS.detractores;
        }
        if (value >= 9) {
            npsType = typeNPS.promotores;
        }


        return npsType;
    }
}
