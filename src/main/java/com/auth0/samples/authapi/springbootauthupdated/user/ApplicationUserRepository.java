package com.auth0.samples.authapi.springbootauthupdated.user;

/**
 * Created by simon on 3/25/2019.
 */

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}