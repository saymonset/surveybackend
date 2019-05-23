package com.repository.mongo;

import com.model.mongo.Encuesta;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/22/2019.
 */
public interface EncuestaRepository  extends CrudRepository<Encuesta, String> {
    Encuesta findByFileEncuestaAndDivisionTerritorialAndDivisionServicios(String fileEncuesta, String divisionTerritorial,String divisionServicios);
}