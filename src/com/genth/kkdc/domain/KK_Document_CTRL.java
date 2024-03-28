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
@Table(name="KK_Document_CTRL")
public class KK_Document_CTRL implements Serializable {
	private static final long serialVersionUID = 1L;
 	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@Column(name="channel", nullable=false)
	private String channel;
	
	@Column(name="item", nullable=false)
	private String item;

	@Column(name="Item_description", nullable=false)
	private String itemDescription;
	 
	@Column(name="CREATEBY", nullable=false)
	private String createdBy;
	
	@Column(name="CREATEDATE", nullable=false)
	private Timestamp createdDate;

	
    public KK_Document_CTRL() {
    }


	public String getChannel() {
		return channel;
	}


	public void setChannel(String channel) {
		this.channel = channel;
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


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
  
	
}