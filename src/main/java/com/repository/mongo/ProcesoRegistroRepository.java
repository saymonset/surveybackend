package com.repository.mongo;

import com.model.mongo.ProcesoRegistroResponse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/30/2019.
 */
public interface ProcesoRegistroRepository  extends CrudRepository<ProcesoRegistroResponse, String> {
}
