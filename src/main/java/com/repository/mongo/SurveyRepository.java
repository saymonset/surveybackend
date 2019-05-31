package com.repository.mongo;

import com.model.mongo.Survey;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/31/2019.
 */
public interface SurveyRepository extends CrudRepository<Survey, String> {
    Survey findByCodigoEncuesta(String codigoEncuesta);
}
