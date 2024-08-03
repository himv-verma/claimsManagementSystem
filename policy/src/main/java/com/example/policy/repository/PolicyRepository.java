package com.example.policy.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Policy findByPolicyNumber(String policyNumber);
}
