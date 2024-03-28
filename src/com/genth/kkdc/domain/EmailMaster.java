package com.genth.kkdc.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the TB_S_JOBS database table.
 * 
 */
@Entity
@Table(name="TB_M_EMAIL")
public class EmailMaster implements Serializable {
	private static final long serialVersionUID = 1L;
 	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="EMAIL_ID", unique=true, nullable=false)
	private Integer id;

	@Column(name="AGNT_NUM", nullable=false)
	private String agentNumber;

	@Column(name="REINSURER_NM", nullable=false)
	private String reinsurerName;

	@Column(name="SEND_FROM", nullable=false)
	private String from;
	
	@Column(name="SEND_TO", nullable=false)
	private String to;
	
	@Column(name="CC", nullable=false)
	private String cc;
	
	@Column(name="BCC")
	private String bcc;
	
	@Column(name="MESSAGE")
	private String message;	 
	
	@Column(name="CREATED_BY", nullable=false)
	private String createdBy;
	
	@Column(name="CREATED_DT", nullable=false)
	private Timestamp createdDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DT")
	private Timestamp updatedDate;
	
    public EmailMaster() {
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
	
	public String getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getReinsurerName() {
		return reinsurerName;
	}

	public void setReinsurerName(String reinsurerName) {
		this.reinsurerName = reinsurerName;
	}
 
}