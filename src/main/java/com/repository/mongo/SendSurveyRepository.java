package com.repository.mongo;

import com.model.mongo.SendSurvey;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by simon on 5/23/2019.
 */
public interface SendSurveyRepository extends CrudRepository<SendSurvey, String> {

       SendSurvey findByCodigoEncuestaAndEmailAndAnswered(String codigoEncuesta, String email, boolean answered);
       List<SendSurvey>   findByCodigoEncuestaAndEmailAndCreatedAt(String codigoEncuesta, String email, Date Date);

}
