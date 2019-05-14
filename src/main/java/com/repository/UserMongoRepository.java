package com.repository;

import com.model.UserMongo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 5/14/2019.
 */
public interface UserMongoRepository extends CrudRepository<UserMongo, String> {
    UserMongo findByUsername(String username);
}