package com.example.policy.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.entity.MemberPolicy;

public interface MemberPolicyRepository extends JpaRepository<MemberPolicy, Long> {
    MemberPolicy findByPolicyNumberAndMemberId(String policyNumber, String memberId);
}
