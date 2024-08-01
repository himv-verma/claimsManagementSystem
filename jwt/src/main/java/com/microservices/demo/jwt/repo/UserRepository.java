package com.microservices.demo.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.demo.jwt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
