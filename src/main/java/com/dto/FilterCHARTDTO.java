package com.dto;

import com.model.mongo.Company;
import com.model.mongo.TreeModelServicio;
import com.model.mongo.TreeModelTerritorial;

import java.util.Date;
import java.util.List;

/**
 * Created by simon on 6/1/2019.
 */
public class FilterCHARTDTO {
    private String territorialNode;
    private String  servicioNode;
    private String  dateBegin;
    private String  dateEnd;
    private String codeCompany;
    private List<String> servicios = null;
    private List<String> territorios = null;
    private Date start = null;
    private Date end = null;
    private boolean isProcesar ;
  private Company company;
    public String getTerritorialNode() {
        return territorialNode;
    }

    public void setTerritorialNode(String territorialNode) {
        this.territorialNode = territorialNode;
    }

    public String getServicioNode() {
        return servicioNode;
    }

    public void setServicioNode(String servicioNode) {
        this.servicioNode = servicioNode;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCodeCompany() {
        return codeCompany;
    }

    public void setCodeCompany(String codeCompany) {
        this.codeCompany = codeCompany;
    }


    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    public List<String> getTerritorios() {
        return territorios;
    }

    public void setTerritorios(List<String> territorios) {
        this.territorios = territorios;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isProcesar() {
        return isProcesar;
    }

    public void setProcesar(boolean procesar) {
        isProcesar = procesar;
    }
}
