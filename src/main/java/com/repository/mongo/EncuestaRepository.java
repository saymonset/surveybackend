package com.repository.mongo;

import com.model.mongo.Company;
import com.model.mongo.Survey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by simon on 5/22/2019.
 */
public interface EncuestaRepository  extends CrudRepository<Survey, String> {
    Survey findByFileEncuestaAndDivisionTerritorialAndDivisionServiciosAndCompanyAndCodigoEncuesta(String fileEncuesta, String divisionTerritorial, String divisionServicios, Company company,String codigoEncuesta);

    Survey findByCodigoEncuestaAndCompany(String codigoEncuesta,  Company company);

    List<Survey> findByCompany(Company company);
}