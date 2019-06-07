package com.dto;

/**
 * Created by simon on 6/1/2019.
 */
public class FilterCHARTDTO {
    private String territorialNode;
    private String  servicioNode;
    private String  dateBegin;
    private String  dateEnd;
    private String codeCompany;

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
}
