package com.repository.mongo;

import com.model.mongo.TreeModelTerritorial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */

public interface TreeModelRepository extends CrudRepository<TreeModelTerritorial, String> {
    List<TreeModelTerritorial> findAll();
    List<TreeModelTerritorial> findByParentNode(String parentNode);
    TreeModelTerritorial findByNode(String node);
}
