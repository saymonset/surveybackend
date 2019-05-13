package com.auth0.samples.authapi.springbootauthupdated.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTestRepository extends JpaRepository<TaskTest, Long> {
}