package com.repository.mongo;

import com.model.mongo.TreeModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */

public interface TreeModelRepository extends CrudRepository<TreeModel, String> {
    List<TreeModel> findAll();
    List<TreeModel> findByParentNode(long parentNode);
    TreeModel findByNode(long node);
}
