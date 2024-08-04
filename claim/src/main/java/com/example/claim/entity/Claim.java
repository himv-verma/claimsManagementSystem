package com.example.claim.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String claimNumber;
    private String status;
    private String remarks;
    private String policyNumber;
    private String benefitDetails;
    private String hospitalDetails;
    private double amountClaimed;
    private double amountSettled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getBenefitDetails() {
		return benefitDetails;
	}
	public void setBenefitDetails(String benefitDetails) {
		this.benefitDetails = benefitDetails;
	}
	public String getHospitalDetails() {
		return hospitalDetails;
	}
	public void setHospitalDetails(String hospitalDetails) {
		this.hospitalDetails = hospitalDetails;
	}
	public double getAmountClaimed() {
		return amountClaimed;
	}
	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	public double getAmountSettled() {
		return amountSettled;
	}
	public void setAmountSettled(double amountSettled) {
		this.amountSettled = amountSettled;
	}

}
