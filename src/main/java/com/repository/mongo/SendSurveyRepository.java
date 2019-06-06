package com.repository.mongo;

import com.model.mongo.Company;
import com.model.mongo.SendSurvey;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by simon on 5/23/2019.
 */
public interface SendSurveyRepository extends CrudRepository<SendSurvey, String> {

       SendSurvey findByCodigoEncuestaAndEmailAndAnsweredAndCompany(String codigoEncuesta, String email, boolean answered, Company company);
       List<SendSurvey>   findByCodigoEncuestaAndEmailAndCreatedAtAndCompany(String codigoEncuesta, String email, Date Date, Company company);

}
