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
@Table(name="KK_Document_Log")
public class KK_Document_Log_ForKKGENBIZ implements Serializable {
	private static final long serialVersionUID = 1L;
 	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@Column(name="AppNo", nullable=false)
	private String appNo;
	
	@Column(name="Item", nullable=false)
	private String item;
	
	@Column(name="item_Description", nullable=false)
	private String itemDescription;
	
	@Column(name="Status", nullable=false)
	private String status;
	 
	@Column(name="ReviewBy", nullable=false)
	private String reviewBy;
	
	@Column(name="ReviewDate", nullable=false)
	private Timestamp reviewDate;
	
	@Column(name="Remark", nullable=false)
	private String remark;
	
	@Column(name="FollowupDate", nullable=false)
	private Timestamp followupDate;
	
	@Column(name="CompleteBy", nullable=false)
	private String completeBy;
	
	@Column(name="CompleteDate", nullable=false)
	private Timestamp completeDate;
	
	@Column(name="CREATEBY", nullable=false)
	private String createdBy;
	
	@Column(name="CREATEDATE", nullable=false)
	private Timestamp createdDate;

	@Column(name="UPDATEBY")
	private String updatedBy;

	@Column(name="UPDATEDATE")
	private Timestamp updatedDate;
	 
    public KK_Document_Log_ForKKGENBIZ() {
    }
    /*
    public KK_Document_Log(Object obj[], int startIndex) {
    	
    	this.setId          (Integer.valueOf(obj[startIndex++].toString()));
    	this.setProductType(obj[startIndex++].toString());
    	this.setCreatedDateStr(obj[startIndex++].toString());
    	this.setBranchStr(obj[startIndex++].toString());
    	this.setCustomerFirstName(obj[startIndex++].toString());
    	this.setCustomerLastname(obj[startIndex++].toString());
    	this.setCustomerIdCard(obj[startIndex++].toString());
    	this.setSumAssured(obj[startIndex++].toString());
    	this.setPremium(obj[startIndex++].toString());
    	this.setProductName(obj[startIndex++].toString());
    	this.setAgentCode(obj[startIndex++].toString());
    	this.setAgentName(obj[startIndex++].toString());
    	this.setAppNo(obj[startIndex++].toString());
    	this.setLocation(obj[startIndex++].toString());
    	this.setCreatedBy(obj[startIndex++].toString());
    	this.setStatus(obj[startIndex++].toString());
    	this.setSisId(obj[startIndex++].toString());
    	this.setDocType(obj[startIndex++].toString());
    	
    }
    */
    public KK_Document_Log_ForKKGENBIZ(KK_Document_Log_ForKKGENBIZ doc) {
    	
    	this.id 	     = doc.getId();
    	this.appNo 	     = doc.getAppNo();
    	this.item 		 = doc.getItem();
    	this.itemDescription = doc.getItemDescription();
    	this.status 	  = doc.getStatus();
    	this.reviewBy 	  = doc.getReviewBy();
    	this.reviewDate   = doc.getReviewDate();
    	this.remark 	  = doc.getRemark();
    	this.followupDate = doc.getFollowupDate();
    	this.completeBy   = doc.getCompleteBy();
    	this.completeDate = doc.getCompleteDate();
    	this.createdBy	  = doc.getCreatedBy();
    	this.createdDate  = doc.getCreatedDate();
    	this.updatedBy	  = doc.getUpdatedBy();
    	this.updatedDate  = doc.getUpdatedDate();
    	
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
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReviewBy() {
		return reviewBy;
	}
	public void setReviewBy(String reviewBy) {
		this.reviewBy = reviewBy;
	}
	public Timestamp getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Timestamp reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getFollowupDate() {
		return followupDate;
	}
	public void setFollowupDate(Timestamp followupDate) {
		this.followupDate = followupDate;
	}
	public String getCompleteBy() {
		return completeBy;
	}
	public void setCompleteBy(String completeBy) {
		this.completeBy = completeBy;
	}
	public Timestamp getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Timestamp completeDate) {
		this.completeDate = completeDate;
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
	
}