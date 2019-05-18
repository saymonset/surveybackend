package com.repository.mysql;

/**
 * Created by simon on 3/25/2019.
 */

import com.model.mysql.User;
import com.repository.inject.MysqlBd;
import org.springframework.data.jpa.repository.JpaRepository;
@MysqlBd
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}