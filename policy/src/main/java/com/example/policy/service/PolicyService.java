package com.example.policy.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.policy.entity.MemberPolicy;
import com.example.policy.entity.ProviderPolicy;
import com.example.policy.repository.MemberPolicyRepository;
import com.example.policy.repository.PolicyRepository;
import com.example.policy.repository.ProviderPolicyRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private MemberPolicyRepository memberPolicyRepository;

    @Autowired
    private ProviderPolicyRepository providerPolicyRepository;

    public List<ProviderPolicy> getChainOfProviders(String policyNumber) {
        return providerPolicyRepository.findByPolicyNumber(policyNumber);
    }

    public String getEligibleBenefits(String policyNumber, String memberId) {
        MemberPolicy memberPolicy = memberPolicyRepository.findByPolicyNumberAndMemberId(policyNumber, memberId);
        return memberPolicy != null ? memberPolicy.getBenefits() : null;
    }

    public Double getEligibleClaimAmount(String policyNumber, String memberId, String benefitId) {
        MemberPolicy memberPolicy = memberPolicyRepository.findByPolicyNumberAndMemberId(policyNumber, memberId);
        // Assuming benefitId is part of the benefits and capAmount is the eligible amount.
        return memberPolicy != null ? memberPolicy.getCapAmount() : null;
    }
}
