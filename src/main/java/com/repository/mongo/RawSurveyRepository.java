package com.repository.mongo;

import com.model.mongo.RawSurveyResponse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/31/2019.
 */
public interface RawSurveyRepository  extends CrudRepository<RawSurveyResponse, String> {
}
