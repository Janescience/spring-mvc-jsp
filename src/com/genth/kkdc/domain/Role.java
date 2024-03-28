package com.genth.kkdc.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the TB_M_ROLE database table.
 * 
 */
@Entity
@Table(name="KK_TB_M_ROLE")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="ROLE_ID", unique=true, nullable=false)
	private Integer id;

	@Column(name="ROLE_NAME", nullable=false)
	private String name;

	@Column(name="ROLE_DESCRIPTION", nullable=true)
	private String description;

    public Role() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		if(description != null){
		    return description;
		}else{
			return "===== NULL =====";
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}