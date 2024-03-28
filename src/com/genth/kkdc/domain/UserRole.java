package com.genth.kkdc.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the TB_M_USER_ROLE database table.
 * 
 */
@Entity
@Table(name="KK_TB_M_USER_ROLE")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private UserRolePK id;

	@Column(name="CREATED_BY", nullable=false)
	private String createdBy;

	@Column(name="CREATED_DT", nullable=false)
	private Timestamp createdDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DT")
	private Timestamp updatedDate;

	//uni-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ROLE_ID", nullable=false, insertable=false, updatable=false)
	private Role role;

    public UserRole() {
    }

	/**
	 * @return the id
	 */
	public UserRolePK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UserRolePK id) {
		this.id = id;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
}