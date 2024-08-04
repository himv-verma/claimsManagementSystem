package com.example.policy.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy.entity.ProviderPolicy;
import com.example.policy.service.PolicyService;

@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/getChainOfProviders")
    public List<ProviderPolicy> getChainOfProviders(@RequestParam String policyNumber) {
        return policyService.getChainOfProviders(policyNumber);
    }

    @GetMapping("/getEligibleBenefits")
    public String getEligibleBenefits(@RequestParam String policyNumber, @RequestParam String memberId) {
        return policyService.getEligibleBenefits(policyNumber, memberId);
    }

    @GetMapping("/getEligibleClaimAmount")
    public Double getEligibleClaimAmount(@RequestParam String policyNumber, @RequestParam String memberId, @RequestParam String benefitId) {
        return policyService.getEligibleClaimAmount(policyNumber, memberId, benefitId);
    }
}
