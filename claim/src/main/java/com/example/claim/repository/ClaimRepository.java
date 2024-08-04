package com.example.claim.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.claim.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Claim findByClaimNumber(String claimNumber);
}
