package com.model.mongo;

import java.util.Date;

/**
 * Created by simon on 5/31/2019.
 */
public class ProcesoMatrix {

     private String type;
     private Date responsedate;
     private String divisionTerritorial;
     private String divisionServicios;
     private String codigoEncuesta;

     private int point;



     public int getPoint() {
          return point;
     }

     public void setPoint(int point) {
          this.point = point;
     }


     public String getType() {
          return type;
     }

     public void setType(String type) {
          this.type = type;
     }




     public Date getResponsedate() {
          return responsedate;
     }

     public void setResponsedate(Date responsedate) {
          this.responsedate = responsedate;
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
}
