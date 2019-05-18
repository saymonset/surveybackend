package com.repository;

import com.model.TreeModelMongo;
import com.model.UserMongo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */

public interface TreeModelMongoRepository extends CrudRepository<TreeModelMongo, String> {
    List<TreeModelMongo> findAll();
    List<TreeModelMongo> findByParent(long parent);
    TreeModelMongo findById(long node);
}
