package com.example.member.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.member.model.Claim;

@Service
public class MemberService {

    @Value("${claim.service.url}")
    private String claimServiceUrl;

    private final RestTemplate restTemplate;

    public MemberService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String viewBills(String memberId, String policyId, String token) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.AUTHORIZATION, token);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                claimServiceUrl + "/viewBills?memberId=" + memberId + "&policyId=" + policyId,
//                HttpMethod.GET,
//                entity,
//                String.class
//        );
//
//        return response.getBody();
    	return "BILL - flat file";
    }

    public String getClaimStatus(String claimNumber, String policyId, String memberId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                claimServiceUrl + "/getClaimStatus?claimNumber=" + claimNumber,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }

    public String submitClaim(String policyNumber, String memberId, Claim claimDetails, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpEntity<Claim> entity = new HttpEntity<>(claimDetails, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                claimServiceUrl + "/submitClaim?policyNumber=" + policyNumber + "&memberId=" + memberId,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }
}
