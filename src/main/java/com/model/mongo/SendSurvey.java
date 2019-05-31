package com.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

/**
 * Created by simon on 5/22/2019.
 */
@Document
public class SendSurvey {


    @Id
    private String id;
    private Date createdAt;
    private Date resentAt;
    private int countResent = 0;
    private Boolean answered;
    private Boolean emailViewed;
    private String name;
    private String lastName;
    private String email;
  /*  private String divisionTerritorial;
    private String divisionServicios;*/
    private String codigoEncuesta;

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



    public int getCountResent() {
        return countResent;
    }

    public void setCountResent(int countResent) {
        this.countResent = countResent;
    }

    public String getCodigoEncuesta() {
        return codigoEncuesta;
    }

    public void setCodigoEncuesta(String codigoEncuesta) {
        this.codigoEncuesta = codigoEncuesta;
    }

    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SendSurvey other = (SendSurvey) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
