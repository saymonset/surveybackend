package com.repository.mongo;

import com.model.mongo.ProcesoComidaResponse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/31/2019.
 */
public interface ProcesoComidaRepository   extends CrudRepository<ProcesoComidaResponse, String> {
}
