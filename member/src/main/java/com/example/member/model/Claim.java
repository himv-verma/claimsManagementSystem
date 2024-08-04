package com.example.member.model;
public class Claim {
    private String claimNumber;
    private String benefitDetails;
    private String hospitalDetails;
    private Double amountClaimed;

    // Getters and Setters
    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
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

    public Double getAmountClaimed() {
        return amountClaimed;
    }

    public void setAmountClaimed(Double amountClaimed) {
        this.amountClaimed = amountClaimed;
    }
}
