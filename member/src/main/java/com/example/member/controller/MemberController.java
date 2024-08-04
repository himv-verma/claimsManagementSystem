package com.example.member.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.member.model.Claim;
import com.example.member.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/viewBills")
    public ResponseEntity<String> viewBills(@RequestParam String memberId, @RequestParam String policyId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String response = memberService.viewBills(memberId, policyId, token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getClaimStatus")
    public ResponseEntity<String> getClaimStatus(@RequestParam String claimNumber, @RequestParam String policyId, @RequestParam String memberId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String response = memberService.getClaimStatus(claimNumber, policyId, memberId, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitClaim")
    public ResponseEntity<String> submitClaim(@RequestParam String policyNumber, @RequestParam String memberId, @RequestBody Claim claimDetails, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String response = memberService.submitClaim(policyNumber, memberId, claimDetails, token);
        return ResponseEntity.ok(response);
    }
}
