package com.repository.mongo;

import com.model.mongo.Company;
import com.model.mongo.TreeModelServicio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by simon on 5/22/2019.
 */
public interface TreeModelServicioRepository extends CrudRepository<TreeModelServicio, String> {
    List<TreeModelServicio> findByCompany(Company company);
    List<TreeModelServicio> findByParentNodeAndCompany(String parentNode, Company company);
    TreeModelServicio findByNodeAndCompany(String node, Company company);
}
