package com.repository.mongo;

import com.model.mongo.Company;
import com.model.mongo.TreeModelTerritorial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */

public interface TreeModelTerritorialRepository extends CrudRepository<TreeModelTerritorial, String> {
    List<TreeModelTerritorial> findByCompany(Company company);
    List<TreeModelTerritorial> findByParentNodeAndCompany(String parentNode, Company company);
    TreeModelTerritorial findByNodeAndCompany(String node, Company company);
}
