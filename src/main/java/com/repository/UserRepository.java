package com.repository;

/**
 * Created by simon on 3/25/2019.
 */

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}