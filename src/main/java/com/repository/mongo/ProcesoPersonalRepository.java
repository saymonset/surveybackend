package com.repository.mongo;

import com.model.mongo.ProcesoPersonalResponse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/31/2019.
 */
public interface ProcesoPersonalRepository  extends CrudRepository<ProcesoPersonalResponse, String> {
}
