package com.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 5/22/2019.
 */
@Document
public class Encuesta {
    private String fileEncuesta;
    private String divisionTerritorial;
    private String divisionServicios;

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
}
