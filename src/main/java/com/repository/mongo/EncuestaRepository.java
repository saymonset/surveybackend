package com.repository.mongo;

import com.model.mongo.Company;
import com.model.mongo.Survey;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/22/2019.
 */
public interface EncuestaRepository  extends CrudRepository<Survey, String> {
    Survey findByFileEncuestaAndDivisionTerritorialAndDivisionServiciosAndCompany(String fileEncuesta, String divisionTerritorial, String divisionServicios, Company company);

    Survey findByCodigoEncuesta(String codigoEncuesta);
}