package com.genth.kkdc.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TB_M_USER database table.
 * 
 */
@Entity
@Table(name="KK_TB_M_USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="USER_ID", unique=true, nullable=false)
	private Integer id;

	@Column(name="CREATED_BY", nullable=false)
	private String createdBy;

	@Column(name="CREATED_DT", nullable=false)
	@OrderBy
	private Timestamp createdDate;

	@Column(name="EMAIL")
	private String email;

	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;

	@Column(name="INVALID_LOGIN", nullable=false)
	private Integer invalidLogin = 0;

	@Column(name="LAST_LOGIN_TIME")
	private Timestamp lastLoginTime;

	@Column(name="LAST_NAME", nullable=false)
	private String lastName;

	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@Transient
	private String confirmPassword;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DT")
	private Timestamp updatedDate;

	@Column(name="USER_NAME", nullable=false)
	private String userName;

	//bi-directional one-to-one association to Status
	@OneToOne
	@JoinColumn(name="STATUS", nullable=false)
	private Status status;

	//bi-directional one-to-one association to PasswordStatus
	@OneToOne
	@JoinColumn(name="PASSWORD_STATUS", nullable=false)
	private PasswordStatus passwordStatus;
	
	@Transient
	private String role;
	/*
	@Transient
	private String roleDescription;
	*/
	@Transient
	private String mode;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name="USER_ID")
	private List<UserRole> userRoles;

    public User() {
    }
    public User(Integer id) {
    	this.id = id;
    }
    public User(Object[] obj,int startIndex) {
    	this.id        = Integer.valueOf(obj[startIndex++].toString());
    	this.userName  = (String)obj[startIndex++];
        this.password  = (String)obj[startIndex++];
        this.firstName = (String)obj[startIndex++];
        this.lastName  = (String)obj[startIndex++];
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the invalidLogin
	 */
	public Integer getInvalidLogin() {
		return invalidLogin;
	}

	/**
	 * @param invalidLogin the invalidLogin to set
	 */
	public void setInvalidLogin(Integer invalidLogin) {
		this.invalidLogin = invalidLogin;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {

		if (mode == null) {
			if (this.password != null) {
				confirmPassword = this.password;
			}
		}
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the passwordStatus
	 */
	public PasswordStatus getPasswordStatus() {
		return passwordStatus;
	}

	/**
	 * @param passwordStatus the passwordStatus to set
	 */
	public void setPasswordStatus(PasswordStatus passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	/**
	 * @return the userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		if (mode == null) {
			StringBuilder role = new StringBuilder();
			if (userRoles != null && !userRoles.isEmpty()) {
				for (UserRole userRole : userRoles) {
					role.append(userRole.getId().getRoleId());
					role.append(",");
				}
				
				role.replace(role.lastIndexOf(","), role.lastIndexOf(",") + 1, "");
			}else{
				role.append("Error GetRole");
			}
			
			return role.toString();
		} else {
			return role;
		}
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public String getRoleDescription() {
		String roleDescription = "";
		try {
			if (mode == null) {
				StringBuilder roleDesc = new StringBuilder();
				if (userRoles!= null && !userRoles.isEmpty()) {
					for (UserRole userRole : userRoles) {
						System.out.print(userRole);
						if(userRole.getRole() != null && userRole.getRole().getDescription() != null){
							roleDesc.append(userRole.getRole().getDescription());
							roleDesc.append(", ");
						}else{
							roleDesc.append("Error GetRoleDescription");
						}
					}
					
					roleDesc.replace(roleDesc.lastIndexOf(", "), roleDesc.lastIndexOf(", ") + 1, "");
				}else{
					roleDesc.append("Error GetRoleDescription");
				}
				roleDescription = roleDesc.toString();
				
			} 
			return roleDescription;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "=========Exception==========";
		}
	}
}