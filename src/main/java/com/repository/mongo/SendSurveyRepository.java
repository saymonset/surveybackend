package com.repository.mongo;

import com.model.mongo.SendSurvey;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/23/2019.
 */
public interface SendSurveyRepository extends CrudRepository<SendSurvey, String> {
}
