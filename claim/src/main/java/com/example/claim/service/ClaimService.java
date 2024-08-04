package com.example.claim.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.claim.entity.Claim;
import com.example.claim.repository.ClaimRepository;

@Service
public class ClaimService {

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${policy.service.url}")
	private String policyServiceUrl;

	public Claim getClaimStatus(String claimNumber) {
		return claimRepository.findByClaimNumber(claimNumber);
	}

	@Transactional
	public Claim submitClaim(String policyNumber, String memberId, Claim claimDetails, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);

		// Get Chain of Providers
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<ProviderPolicy[]> providerResponse = restTemplate.exchange(
				policyServiceUrl + "/getChainOfProviders?policyNumber=" + policyNumber, HttpMethod.GET, entity,
				ProviderPolicy[].class);
		List<ProviderPolicy> providers = Arrays.asList(providerResponse.getBody());

		// Get Eligible Benefits
		ResponseEntity<String> benefitsResponse = restTemplate.exchange(
				policyServiceUrl + "/getEligibleBenefits?policyNumber=" + policyNumber + "&memberId=" + memberId,
				HttpMethod.GET, entity, String.class);
		String eligibleBenefits = benefitsResponse.getBody();

		// Get Eligible Claim Amount
		ResponseEntity<Double> amountResponse = restTemplate
				.exchange(
						policyServiceUrl + "/getEligibleClaimAmount?policyNumber=" + policyNumber + "&memberId="
								+ memberId + "&benefitId=" + claimDetails.getBenefitDetails(),
						HttpMethod.GET, entity, Double.class);
		Double eligibleClaimAmount = amountResponse.getBody();

		// Check eligibility
		boolean isEligibleProvider = false;
		for (ProviderPolicy provider : providers) {
			if (provider.getProviderName().equals(claimDetails.getHospitalDetails())) {
				isEligibleProvider = true;
				break;
			}
		}

		boolean isEligibleBenefit = eligibleBenefits != null
				&& eligibleBenefits.contains(claimDetails.getBenefitDetails());

		boolean isEligibleAmount = eligibleClaimAmount != null
				&& claimDetails.getAmountClaimed() <= eligibleClaimAmount;

		if (isEligibleProvider && isEligibleBenefit && isEligibleAmount) {
			claimDetails.setStatus("Pending Action");
		} else if (!isEligibleProvider || !isEligibleBenefit) {
			claimDetails.setStatus("Claim Rejected");
		} else {
			claimDetails.setStatus("Insufficient Claim Details");
		}

		return claimRepository.save(claimDetails);
	}

	 public static class ProviderPolicy {
		 private Long id;
		    private String policyNumber;
		    private String providerName;
		    private String location;
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public String getPolicyNumber() {
				return policyNumber;
			}
			public void setPolicyNumber(String policyNumber) {
				this.policyNumber = policyNumber;
			}
			public String getProviderName() {
				return providerName;
			}
			public void setProviderName(String providerName) {
				this.providerName = providerName;
			}
			public String getLocation() {
				return location;
			}
			public void setLocation(String location) {
				this.location = location;
			}

	
	}

}
