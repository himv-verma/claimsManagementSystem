package com.example.policy.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.entity.ProviderPolicy;

public interface ProviderPolicyRepository extends JpaRepository<ProviderPolicy, Long> {
    List<ProviderPolicy> findByPolicyNumber(String policyNumber);
}
