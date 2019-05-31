package com.repository.mongo;

import com.model.mongo.ProcesoSalidaResponse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/31/2019.
 */
public interface ProcesoSalidaRepository   extends CrudRepository<ProcesoSalidaResponse, String> {
}
