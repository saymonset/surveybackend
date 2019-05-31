package com.repository.mongo;

import com.model.mongo.SatisfactionResponse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/31/2019.
 */
public interface SatisfactionRepository  extends CrudRepository<SatisfactionResponse, String> {
}
