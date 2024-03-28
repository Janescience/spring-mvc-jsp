/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.List;

import com.genth.kkdc.domain.*;

/**
 * @author Thanompong.W 
 * 
 * A custom DAO for accessing data from the database.
 * 
 */
public interface UserDao {

	/**
	 * Simulates retrieval of data from a database.
	 */
	public User findByUserName(String userName);
	
	/**
	* Retrieves all user
	* 
	* @return list of user
	*/
	public List<User> getAll();
	
	/**
	* Retrieves all user status
	* 
	* @return list of Status
	*/
	public List<Status> getUserStatusList();

	
	/**
	* Retrieves all user role
	* 
	* @return list of Role
	*/
	public List<Role> getRoleList();
	 
	/**
	* Add a new user
	* 
	* @param user the new user
	*/
	public void add(User user);
	 
	/**
	* Delete an existing user
	* 
	* @param id String user Id
	*/
	public void delete(Integer id);
	 
	/**
	* Edit an existing user
	* 
	* @param user the new user
	*/
	public void edit(User user);
	 
	/**
	* Update an user info
	* 
	* @param user the new user
	*/
	public void update(User user);
	 
	/**
	* Reset Password an user info
	* 
	* @param user the new user
	*/
	public void resetPassword(User user);
	
	/**
	* Retrieves all user Authorities
	* 
	* @param id String user Id
	*/
	public List<User> getQaStaff();

	public List<User> getSupervisorList();
	
	public List<UserRole> getAuthorities(Integer id);
}
