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

import com.genth.kkdc.common.util.StringUtil;


/**
 * The persistent class for the TB_M_MARKETER database table.
 * 
 */
@Entity
@Table(name="KK_UL_Funds_Selection")
public class KK_UL_Fund_Selection implements Serializable {
	private static final long serialVersionUID = 1L;
 	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@Column(name="SISNo", nullable=false)
	private String sisno;
	
	@Column(name="fundName", nullable=false)
	private String fundName;
	
	@Column(name="percentSelect", nullable=false)
	private Integer percentSelect;

	@Column(name="createBy", nullable=false)
	private String createdBy;
	
	@Column(name="createDate", nullable=false)
	private String createdDate;
  
	@Transient
	private String fundLevel;
	
	
    public KK_UL_Fund_Selection() {
    }

    public KK_UL_Fund_Selection(Object obj[], int startIndex) {
    	
//    	this.setId          (Integer.valueOf(obj[startIndex++].toString()));
//    	this.setCreatedDate(obj[startIndex++].toString());
//    	this.setCustomerName(obj[startIndex++].toString());
//    	this.setMobileNo(obj[startIndex++].toString());
//    	this.setStatus(obj[startIndex++].toString());
//    	this.setAgentCode(obj[startIndex++].toString());
//    	this.setAgentName(obj[startIndex++].toString());
//    	this.setBranchName(obj[startIndex++].toString());
//    	this.setPlanCode(obj[startIndex++].toString());
//    	this.setPremium(obj[startIndex++].toString()); 
//    	this.setSisno(obj[startIndex++].toString());
    	
//    	this.setUwFlag(obj[startIndex++].toString());
//    	this.setKkFlag(obj[startIndex++].toString());
//    	this.setUwTimestamp( StringUtil.formatTimestamp( obj[startIndex++].toString() , "yyyyMMdd" ) );
//    	this.setKkTimestamp( StringUtil.formatTimestamp( obj[startIndex++].toString() , "yyyyMMdd" ) );
//    	this.setCreatedBy(obj[startIndex++].toString());
//    	this.setCreatedDate( StringUtil.formatTimestamp( obj[startIndex++].toString() , "yyyyMMdd" ) );
    	 
    	 
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

	public String getSisno() {
		return sisno;
	}

	public void setSisno(String sisno) {
		this.sisno = sisno;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public Integer getPercentSelect() {
		return percentSelect;
	}

	public void setPercentSelect(Integer percentSelect) {
		this.percentSelect = percentSelect;
	}

	public String getFundLevel() {
		return fundLevel;
	}

	public void setFundLevel(String fundLevel) {
		this.fundLevel = fundLevel;
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
 
}