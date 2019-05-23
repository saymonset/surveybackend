package com.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by simon on 5/22/2019.
 */
@Document
public class MandoEncuesta {
    private Date createdAt;
    private Date resentAt;
    private int countResent = 0;
    private Boolean answered;
    private Boolean emailViewed;
    private String name;
    private String lastName;
    private String email;
    private String divisionTerritorial;
    private String divisionServicios;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getResentAt() {
        return resentAt;
    }

    public void setResentAt(Date resentAt) {
        this.resentAt = resentAt;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public Boolean getEmailViewed() {
        return emailViewed;
    }

    public void setEmailViewed(Boolean emailViewed) {
        this.emailViewed = emailViewed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDivisionTerritorial() {
        return divisionTerritorial;
    }

    public void setDivisionTerritorial(String divisionTerritorial) {
        this.divisionTerritorial = divisionTerritorial;
    }

    public String getDivisionServicios() {
        return divisionServicios;
    }

    public void setDivisionServicios(String divisionServicios) {
        this.divisionServicios = divisionServicios;
    }

    public int getCountResent() {
        return countResent;
    }

    public void setCountResent(int countResent) {
        this.countResent = countResent;
    }
}
