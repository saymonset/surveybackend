package com.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by simon on 5/31/2019.
 */
@Document
public class RawSurveyResponse {
    @Id
    private String id;
    @DBRef
    private SendSurvey sendSurvey;
    private Map<String, Object> origin;
    private Map<String, Object> result;
    private List<Map<String, Object>> questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SendSurvey getSendSurvey() {
        return sendSurvey;
    }

    public void setSendSurvey(SendSurvey sendSurvey) {
        this.sendSurvey = sendSurvey;
    }

    public Map<String, Object> getOrigin() {
        return origin;
    }

    public void setOrigin(Map<String, Object> origin) {
        this.origin = origin;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public List<Map<String, Object>> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Map<String, Object>> questions) {
        this.questions = questions;
    }
}
