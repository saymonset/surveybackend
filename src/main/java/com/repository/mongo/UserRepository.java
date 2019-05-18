package com.repository.mongo;

import com.model.mongo.User;
import com.repository.inject.MongoBd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by simon on 5/14/2019.
 */
@Repository
@MongoBd
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}