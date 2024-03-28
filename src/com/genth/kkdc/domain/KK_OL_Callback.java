package com.genth.kkdc.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the TB_M_MARKETER database table.
 * 
 */
@Entity
@Table(name="KK_OL_Callback")
public class KK_OL_Callback implements Serializable {
	private static final long serialVersionUID = 1L;
 	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@Column(name="sisno", nullable=false)
	private String sisno;
	
	@Column(name="uw_flag", nullable=false)
	private String uwFlag;
	
	@Column(name="kk_flag", nullable=false)
	private String kkFlag;

	@Column(name="uw_timestamp", nullable=false)
	private Timestamp uwTimestamp;
	
	@Column(name="kk_timestamp", nullable=false)
	private Timestamp kkTimestamp;

	@Column(name="first_review_timestamp", nullable=false)
	private String firstReviewTimestamp;
	
	@Column(name="kk_note", nullable=false)
	private String kkNote;
	
	@Column(name="note_from_branch", nullable=false)
	private String noteFromBranch;
	
	@Column(name="q1", nullable=false)
	private String q1;
	@Column(name="q2", nullable=false)
	private String q2;
	@Column(name="q3", nullable=false)
	private String q3;
	@Column(name="q4", nullable=false)
	private String q4;
	@Column(name="q5", nullable=false)
	private String q5;
	@Column(name="q6", nullable=false)
	private String q6;
	@Column(name="q7", nullable=false)
	private String q7;
	@Column(name="q8", nullable=false)
	private String q8;
	@Column(name="q9", nullable=false)
	private String q9;
	@Column(name="q10", nullable=false)
	private String q10;
	
	@Column(name="createby", nullable=false)
	private String createdBy;
	
	@Column(name="createdate", nullable=false)
	private String createdDate;
	@Column(name="callBy", nullable=false)
	private String callBy;
 
	@Transient
	private String customerName;
	@Transient
	private String mobileNo; 
	@Transient
	private String agentCode;
	@Transient
	private String agentName;
	@Transient
	private String agentLicenseCode;
	@Transient
	private String agentConsultantCode;
	@Transient
	private String branchName;
	@Transient
	private String planCode;
	@Transient
	private String planName;
	@Transient
	private String premium;
	@Transient
	private String sumInsured;
	@Transient
	private String status;
	@Transient
	private String gender;
	@Transient
	private String age;
	@Transient
	private String frontEndFee;
	@Transient
	private String coiFee;
	
	@Transient
	private String fundSelectList;
	@Transient
	private String percentSelectList;
	@Transient
	private String riskSelectList;
	@Transient
	private String callDate;
	@Transient
	private String callTime;
	@Transient
	private String isConfirm;
	@Transient
	private String appNo;
	@Transient
	private String sisDate;
	@Transient
	private String kkRefNo;
	@Transient
	private String subStandardValue;
	
	//Product Feature
	@Transient
	private String pf_planCode;
	@Transient
	private String pf_planName;
	@Transient
	private String pf_coverageTerm;
	@Transient
	private String pf_premiumTerm;
	@Transient
	private String pf_refundText; 
	@Transient
	private String premiumRider;
	@Transient
	private String totalPremium;
	@Transient
	private String paymentMode;
	@Transient
	private String premiumHB;
	@Transient
	private String premiumCI;
	@Transient
	private String premiumWP;
//	@Transient
//	private String premiumADD;
	@Transient
	private String premiumADB;
	@Transient
	private String saHB;
	@Transient
	private String saCI;
	@Transient
	private String saWP;
//	@Transient
//	private String saADD;
	@Transient
	private String saADB;
	@Transient
	private String checkRider;
	
	  
	
    public KK_OL_Callback() {
    }

    public KK_OL_Callback(Object obj[], int startIndex) {
    	
    	this.setId          (Integer.valueOf(obj[startIndex++].toString()));
    	this.setCreatedDate(obj[startIndex++].toString());
    	this.setCustomerName(obj[startIndex++].toString());
    	this.setMobileNo(obj[startIndex++].toString());
    	this.setStatus(obj[startIndex++].toString());
    	this.setAgentCode(obj[startIndex++].toString());
    	this.setAgentName(obj[startIndex++].toString());
    	this.setAgentLicenseCode(obj[startIndex++].toString());
    	this.setAgentConsultantCode(obj[startIndex++].toString());
    	this.setBranchName(obj[startIndex++].toString());
    	this.setPlanCode(obj[startIndex++].toString());
    	this.setPlanName(obj[startIndex++].toString());
    	this.setPremium(obj[startIndex++].toString()); 
    	this.setPremiumRider(obj[startIndex++].toString());
    	this.setTotalPremium(obj[startIndex++].toString());
    	this.setSumInsured(obj[startIndex++].toString()); 
    	this.setGender(obj[startIndex++].toString());
    	this.setAge(obj[startIndex++].toString());
    	this.setSisno(obj[startIndex++].toString());
    	this.setAppNo(obj[startIndex++].toString());
    	this.setSisDate(obj[startIndex++].toString());
    	this.setKkRefNo(obj[startIndex++].toString());
    	this.setKkNote(obj[startIndex++].toString());
    	this.setNoteFromBranch(obj[startIndex++].toString());

    	this.setQ1(obj[startIndex++].toString());
    	this.setQ2(obj[startIndex++].toString());
    	this.setQ3(obj[startIndex++].toString());
    	this.setQ4(obj[startIndex++].toString());
    	this.setQ5(obj[startIndex++].toString());
    	this.setQ6(obj[startIndex++].toString());
    	this.setQ7(obj[startIndex++].toString());
    	this.setQ8(obj[startIndex++].toString());
    	this.setQ9(obj[startIndex++].toString());
    	this.setQ10(obj[startIndex++].toString());
 
    	this.setCallDate(obj[startIndex++].toString());
    	this.setCallTime(obj[startIndex++].toString());
    	this.setCallBy(obj[startIndex++].toString());
    	this.setIsConfirm(obj[startIndex++].toString());
    	this.setFirstReviewTimestamp(obj[startIndex++].toString());
    	
    	// Set Product Feature Value
    	this.setPf_planCode(obj[startIndex++].toString());
    	this.setPf_planName(obj[startIndex++].toString());
    	this.setPf_coverageTerm(obj[startIndex++].toString());
    	this.setPf_premiumTerm(obj[startIndex++].toString());
    	this.setPf_refundText (obj[startIndex++].toString());
    	this.setPaymentMode(obj[startIndex++].toString());
    	this.setPremiumHB(obj[startIndex++].toString());
    	this.setPremiumCI(obj[startIndex++].toString());
    	this.setPremiumWP(obj[startIndex++].toString());
    	//this.setPremiumADD(obj[startIndex++].toString());
    	this.setPremiumADB(obj[startIndex++].toString());
    	this.setSaHB(obj[startIndex++].toString()); 
    	this.setSaCI(obj[startIndex++].toString());
    	this.setSaWP(obj[startIndex++].toString());
    	//this.setSaADD(obj[startIndex++].toString());
    	this.setSaADB(obj[startIndex++].toString());
    	this.setCheckRider(obj[startIndex++].toString());
    }
     
    
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckRider() {
		return checkRider;
	}

	public void setCheckRider(String checkRider) {
		this.checkRider = checkRider;
	}

	public String getSisno() {
		return sisno;
	}

	public void setSisno(String sisno) {
		this.sisno = sisno;
	}

	public String getUwFlag() {
		return uwFlag;
	}

	public void setUwFlag(String uwFlag) {
		this.uwFlag = uwFlag;
	}

	public String getKkFlag() {
		return kkFlag;
	}

	public void setKkFlag(String kkFlag) {
		this.kkFlag = kkFlag;
	}

	public Timestamp getUwTimestamp() {
		return uwTimestamp;
	}

	public void setUwTimestamp(Timestamp uwTimestamp) {
		this.uwTimestamp = uwTimestamp;
	}

	public Timestamp getKkTimestamp() {
		return kkTimestamp;
	}

	public void setKkTimestamp(Timestamp kkTimestamp) {
		this.kkTimestamp = kkTimestamp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentLicenseCode() {
		return agentLicenseCode;
	}

	public void setAgentLicenseCode(String agentLicenseCode) {
		this.agentLicenseCode = agentLicenseCode;
	}

	public String getAgentConsultantCode() {
		return agentConsultantCode;
	}

	public void setAgentConsultantCode(String agentConsultantCode) {
		this.agentConsultantCode = agentConsultantCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKkNote() {
		return kkNote;
	}

	public void setKkNote(String kkNote) {
		this.kkNote = kkNote;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCoiFee() {
		return coiFee;
	}

	public void setCoiFee(String coiFee) {
		this.coiFee = coiFee;
	}

	public String getFrontEndFee() {
		return frontEndFee;
	}

	public void setFrontEndFee(String frontEndFee) {
		this.frontEndFee = frontEndFee;
	}

	public String getFundSelectList() {
		return fundSelectList;
	}

	public void setFundSelectList(String fundSelectList) {
		this.fundSelectList = fundSelectList;
	}

	public String getPercentSelectList() {
		return percentSelectList;
	}

	public void setPercentSelectList(String percentSelectList) {
		this.percentSelectList = percentSelectList;
	}

	public String getRiskSelectList() {
		return riskSelectList;
	}

	public void setRiskSelectList(String riskSelectList) {
		this.riskSelectList = riskSelectList;
	}

	public String getNoteFromBranch() {
		return noteFromBranch;
	}

	public void setNoteFromBranch(String noteFromBranch) {
		this.noteFromBranch = noteFromBranch;
	}

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getQ2() {
		return q2;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public String getQ3() {
		return q3;
	}

	public void setQ3(String q3) {
		this.q3 = q3;
	}

	public String getQ4() {
		return q4;
	}

	public void setQ4(String q4) {
		this.q4 = q4;
	}

	public String getQ5() {
		return q5;
	}

	public void setQ5(String q5) {
		this.q5 = q5;
	}

	public String getQ6() {
		return q6;
	}

	public void setQ6(String q6) {
		this.q6 = q6;
	}

	public String getQ7() {
		return q7;
	}

	public void setQ7(String q7) {
		this.q7 = q7;
	}

	public String getQ8() {
		return q8;
	}

	public void setQ8(String q8) {
		this.q8 = q8;
	}

	public String getQ9() {
		return q9;
	}

	public void setQ9(String q9) {
		this.q9 = q9;
	}

	public String getQ10() {
		return q10;
	}

	public void setQ10(String q10) {
		this.q10 = q10;
	}

	public String getCallBy() {
		return callBy;
	}

	public void setCallBy(String callBy) {
		this.callBy = callBy;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getSisDate() {
		return sisDate;
	}

	public void setSisDate(String sisDate) {
		this.sisDate = sisDate;
	}

	public String getKkRefNo() {
		return kkRefNo;
	}

	public void setKkRefNo(String kkRefNo) {
		this.kkRefNo = kkRefNo;
	}

	public String getSubStandardValue() {
		return subStandardValue;
	}

	public void setSubStandardValue(String subStandardValue) {
		this.subStandardValue = subStandardValue;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getFirstReviewTimestamp() {
		return firstReviewTimestamp;
	}

	public void setFirstReviewTimestamp(String firstReviewTimestamp) {
		this.firstReviewTimestamp = firstReviewTimestamp;
	}

	public String getPf_planCode() {
		return pf_planCode;
	}

	public void setPf_planCode(String pf_planCode) {
		this.pf_planCode = pf_planCode;
	}

	public String getPf_planName() {
		return pf_planName;
	}

	public void setPf_planName(String pf_planName) {
		this.pf_planName = pf_planName;
	}

	public String getPf_coverageTerm() {
		return pf_coverageTerm;
	}

	public void setPf_coverageTerm(String pf_coverageTerm) {
		this.pf_coverageTerm = pf_coverageTerm;
	}

	public String getPf_premiumTerm() {
		return pf_premiumTerm;
	}

	public void setPf_premiumTerm(String pf_premiumTerm) {
		this.pf_premiumTerm = pf_premiumTerm;
	}

	public String getPf_refundText() {
		return pf_refundText;
	}

	public void setPf_refundText(String pf_refundText) {
		this.pf_refundText = pf_refundText;
	}

	public String getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getPremiumRider() {
		return premiumRider;
	}

	public void setPremiumRider(String premiumRider) {
		this.premiumRider = premiumRider;
	}

	public String getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPremiumHB() {
		return premiumHB;
	}

	public void setPremiumHB(String premiumHB) {
		this.premiumHB = premiumHB;
	}

	public String getPremiumCI() {
		return premiumCI;
	}

	public void setPremiumCI(String premiumCI) {
		this.premiumCI = premiumCI;
	}

	public String getSaHB() {
		return saHB;
	}

	public void setSaHB(String saHB) {
		this.saHB = saHB;
	}

	public String getSaCI() {
		return saCI;
	}

	public void setSaCI(String saCI) {
		this.saCI = saCI;
	}

	public String getPremiumWP() {
		return premiumWP;
	}

	public void setPremiumWP(String premiumWP) {
		this.premiumWP = premiumWP;
	}

//	public String getPremiumADD() {
//		return premiumADD;
//	}
//
//	public void setPremiumADD(String premiumADD) {
//		this.premiumADD = premiumADD;
//	}

	public String getPremiumADB() {
		return premiumADB;
	}

	public void setPremiumADB(String premiumADB) {
		this.premiumADB = premiumADB;
	}

	public String getSaWP() {
		return saWP;
	}

	public void setSaWP(String saWP) {
		this.saWP = saWP;
	}

//	public String getSaADD() {
//		return saADD;
//	}
//
//	public void setSaADD(String saADD) {
//		this.saADD = saADD;
//	}

	public String getSaADB() {
		return saADB;
	}

	public void setSaADB(String saADB) {
		this.saADB = saADB;
	}

	
	
	
}