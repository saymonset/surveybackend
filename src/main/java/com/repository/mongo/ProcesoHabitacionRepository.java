package com.repository.mongo;

import com.model.mongo.ProcesoHabitacionResponse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/31/2019.
 */
public interface ProcesoHabitacionRepository extends CrudRepository<ProcesoHabitacionResponse, String> {
}
