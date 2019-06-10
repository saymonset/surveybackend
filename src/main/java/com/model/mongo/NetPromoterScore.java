package com.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 5/30/2019.
 */
@Document
public class NetPromoterScore extends DataMandatory {
    @Id
    private String id;
    @DBRef
    private SendSurvey sendSurvey;
    @DBRef
    private Company company;

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


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
