package com.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * Created by simon on 5/22/2019.
 */
@Document
public class Survey {
    @Id
    private String id;
    private String codigoEncuesta;
    private String fileEncuesta;
    private String divisionTerritorial;
    private String divisionServicios;
    @DBRef
    private Company company;
    public String getFileEncuesta() {
        return fileEncuesta;
    }

    public void setFileEncuesta(String fileEncuesta) {
        this.fileEncuesta = fileEncuesta;
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

    public String getCodigoEncuesta() {
        return codigoEncuesta;
    }

    public void setCodigoEncuesta(String codigoEncuesta) {
        this.codigoEncuesta = codigoEncuesta;
    }

    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.getId());
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
        final Survey other = (Survey) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
