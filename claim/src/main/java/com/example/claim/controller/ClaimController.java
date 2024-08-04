package com.example.claim.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.claim.entity.Claim;
import com.example.claim.service.ClaimService;

@RestController
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping("/getClaimStatus")
    public Claim getClaimStatus(@RequestParam String claimNumber) {
        return claimService.getClaimStatus(claimNumber);
    }

    @PostMapping("/submitClaim")
    public Claim submitClaim(@RequestParam String policyNumber, @RequestParam String memberId, @RequestBody Claim claimDetails, @RequestHeader("Authorization") String token) {
        return claimService.submitClaim(policyNumber, memberId, claimDetails, token);
    }
}
