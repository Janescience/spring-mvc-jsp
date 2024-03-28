package com.genth.kkdc.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_M_PASSWORD_STATUS database table.
 * 
 */
@Entity
@Table(name="KK_TB_M_PASSWORD_STATUS")
public class PasswordStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STATUS_ID", unique=true, nullable=false)
	private Integer id;

	@Column(name="STATUS_DESC")
	private String name;
	
	public PasswordStatus() {
		
	}

    public PasswordStatus(Integer id, String name) {
    	this.id = id;
    	this.name = name;
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}