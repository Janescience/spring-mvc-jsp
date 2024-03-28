package com.genth.kkdc.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the TB_M_USER_ROLE database table.
 * 
 */
@Embeddable
public class UserRolePK implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Column(name = "USER_ID", unique = true, nullable = false)
	private Integer userId;  
	
	@Column(name = "ROLE_ID", unique = true, nullable = false)
	private Integer roleId;

	public UserRolePK() {
	}

	public UserRolePK(Integer userId, Integer roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	/*
	 * @see java.lang.Object#equals(Object)
	 */	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof UserRolePK)) {
			return false;
		}
		UserRolePK other = (UserRolePK) o;
		return true
			&& (getUserId() == null ? other.getUserId() == null : getUserId().equals(other.getUserId()))
			&& (getRoleId() == null ? other.getRoleId() == null : getRoleId().equals(other.getRoleId()));
	}
	
	/*	 
	 * @see java.lang.Object#hashCode()
	 */	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getUserId() == null ? 0 : getUserId().hashCode());
		result = prime * result + (getRoleId() == null ? 0 : getRoleId().hashCode());
		return result;
	}
   
}
