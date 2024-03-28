package com.genth.kkdc.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the TB_M_MARKETER database table.
 * 
 */
@Entity
@Table(name="KK_Document")
public class RejectByAppno implements Serializable {
	private static final long serialVersionUID = 1L;
 	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@Column(name="AppNo", nullable=false)
	private String appNo;
	
	@Column(name="Location", nullable=false)
	private String location;

	@Column(name="Status", nullable=false)
	private String status;
	 
	@Column(name="CREATEBY", nullable=false)
	private String createdBy;
	
	@Column(name="CREATEDATE", nullable=false)
	private Timestamp createdDate;

	@Column(name="UPDATEBY")
	private String updatedBy;

	@Column(name="UPDATEDATE")
	private Timestamp updatedDate;
	 
	@Transient
	private String productType;
	@Transient
	private String createdDateStr;
	@Transient
	private String branchStr;
	@Transient
	private String customerFirstName;
	@Transient
	private String customerLastname;
	@Transient
	private String customerIdCard;
	@Transient
	private String sumAssured;
	@Transient
	private String premium;
	@Transient
	private String productName;
	@Transient
	private String agentCode;
	@Transient
	private String agentName;
	@Transient
	private String sisId;
	@Transient
	private String docType;
	@Transient
	private String isCompleteSendMail;
	
	@Transient
	private String docRemark;
	@Transient
	private String docLogStatus;

	@Transient
	private String fileServer;
	@Transient
	private String staffEmail;
	@Transient
	private String cntPdf;
	@Transient
	private String subject;
	@Transient
	private String body;
	@Transient
	private String kkRefNo;
	@Transient
	private String appCuStatus;
	@Transient
	private String email;
	@Transient
	private String policyNo;

	
    public RejectByAppno() {
    }

    public RejectByAppno(Object obj[], int startIndex) {
    	
    	this.setId          (Integer.valueOf(obj[startIndex++].toString()));
    	this.setKkRefNo(			obj[startIndex++].toString());
    	this.setAppNo(		obj[startIndex++].toString());
    	this.setBranchStr(			obj[startIndex++].toString());
    	this.setCreatedDateStr(	obj[startIndex++].toString());
    	this.setCustomerFirstName(	obj[startIndex++].toString());
    	this.setCustomerLastname(	obj[startIndex++].toString());
    	this.setCustomerIdCard(		obj[startIndex++].toString());
    	this.setSumAssured(			obj[startIndex++].toString());
    	this.setPremium(			obj[startIndex++].toString());
    	this.setProductName(		obj[startIndex++].toString());
    	this.setAgentCode(			obj[startIndex++].toString());
    	this.setAgentName(			obj[startIndex++].toString());
    	this.setEmail(			obj[startIndex++].toString());
    	this.setAppCuStatus(		obj[startIndex++].toString());
    	this.setStatus(			obj[startIndex++].toString());
    	
    	
    }
    
    public RejectByAppno(RejectByAppno doc) {
    	
    	this.id =  doc.getId();
    	this.productType = doc.getProductType();
    	this.createdDateStr=doc.getCreatedDateStr();
    	this.branchStr=doc.getBranchStr();
    	this.customerFirstName=doc.getCustomerFirstName();
    	this.customerLastname=doc.getCustomerLastname();
    	this.customerIdCard=doc.getCustomerIdCard();
    	this.sumAssured=doc.getSumAssured();
    	this.premium=doc.getPremium();
    	this.productName=doc.getProductName();
    	this.agentCode=doc.getAgentCode();
    	this.agentName=doc.getAgentName();
    	this.appNo=doc.getAppNo();
    	this.location=doc.getLocation();
    	this.createdBy=doc.getCreatedBy();
    	this.status=doc.getStatus();
    	this.sisId=doc.getSisId();
    	this.docType=doc.getDocType();
    	this.isCompleteSendMail=doc.getIsCompleteSendMail();

    	this.docRemark=doc.getDocRemark();
    	this.docLogStatus=doc.getDocLogStatus();
    	
    	this.fileServer=doc.getFileServer();
    	this.staffEmail=doc.getStaffEmail();
    	this.cntPdf=doc.getCntPdf();
    	this.kkRefNo=doc.getKkRefNo();
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

	public String getKkRefNo() {
		return kkRefNo;
	}

	public void setKkRefNo(String kkRefNo) {
		this.kkRefNo = kkRefNo;
	}

	public String getIsCompleteSendMail() {
		return isCompleteSendMail;
	}

	public void setIsCompleteSendMail(String isCompleteSendMail) {
		this.isCompleteSendMail = isCompleteSendMail;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCntPdf() {
		return cntPdf;
	}

	public void setCntPdf(String cntPdf) {
		this.cntPdf = cntPdf;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getFileServer() {
		return fileServer;
	}

	public void setFileServer(String fileServer) {
		this.fileServer = fileServer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
  

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getBranchStr() {
		return branchStr;
	}

	public void setBranchStr(String branchStr) {
		this.branchStr = branchStr;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastname() {
		return customerLastname;
	}

	public void setCustomerLastname(String customerLastname) {
		this.customerLastname = customerLastname;
	}

	public String getCustomerIdCard() {
		return customerIdCard;
	}

	public void setCustomerIdCard(String customerIdCard) {
		this.customerIdCard = customerIdCard;
	}

	public String getSumAssured() {
		return sumAssured;
	}

	public void setSumAssured(String sumAssured) {
		this.sumAssured = sumAssured;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSisId() {
		return sisId;
	}

	public void setSisId(String sisId) {
		this.sisId = sisId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocRemark() {
		return docRemark;
	}

	public void setDocRemark(String docRemark) {
		this.docRemark = docRemark;
	}

	public String getDocLogStatus() {
		return docLogStatus;
	}

	public void setDocLogStatus(String docLogStatus) {
		this.docLogStatus = docLogStatus;
	}

	public String getStaffEmail() {
		return staffEmail;
	}

	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}

	public String getAppCuStatus() {
		return appCuStatus;
	}

	public void setAppCuStatus(String appCuStatus) {
		this.appCuStatus = appCuStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
 
	
}