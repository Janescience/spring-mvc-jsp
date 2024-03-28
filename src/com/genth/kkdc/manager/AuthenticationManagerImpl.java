/**
 * 
 */
package com.genth.kkdc.manager;

import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.dao.UserDao;
import com.genth.kkdc.domain.*;

/**
 * @author Thanompong.W
 * 
 *         A custom authentication manager that allows access if the user
 *         details exist in the database and if the username and password are
 *         not the same. Otherwise, throw a {@link BadCredentialsException}
 */
public class AuthenticationManagerImpl implements AuthenticationManager {

	protected static Logger logger = Logger.getLogger("service");

	// Our custom DAO layer
	@Resource(name = "userDao")
	private UserDao userDao;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;

	// We need an Md5 encoder since our passwords in the database are Md5
	// encoded.
	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	public AuthenticationManagerImpl() {
	}

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		logger.info("Performing custom authentication");

		// Init a database user object
		Timestamp lastLoginDatetime = new Timestamp(System.currentTimeMillis());
		User user = null;
		try {
			// Retrieve user details from database
			user = userDao.findByUserName(auth.getName());
			
			if (user == null) {
				String msg = messageResolver.getMessage("login.user.invalid");
				
				logger.error(msg);
				throw new BadCredentialsException(msg);
			}
		} catch (Exception e) {
			
			String msg = messageResolver.getMessage("login.user.invalid");
			
			logger.error(msg, e);
			throw new BadCredentialsException(msg, e);
		}
		
		if (user.getInvalidLogin() >= 3) {
			String msg = messageResolver.getMessage("login.user.locked");
			
			logger.error(msg);
			throw new BadCredentialsException(msg);
		}
		
		if (user.getStatus().getId() == 2) {
			String msg = messageResolver.getMessage("login.user.inactive");
			
			logger.error(msg);
			throw new BadCredentialsException(msg);
		}

		// Compare passwords
		// Make sure to encode the password first before comparing
		if (!passwordEncoder.isPasswordValid(user.getPassword(),
											 (String) auth.getCredentials(), 
											 null)) {
			
			String msg = messageResolver.getMessage("login.password.invalid");
			
			logger.error(msg);
			
			user.setInvalidLogin(user.getInvalidLogin() + 1);
			user.setUpdatedDate(lastLoginDatetime);
			user.setUpdatedBy(auth.getName());
			
			if (user.getInvalidLogin() == 3) {
				user.setStatus(new Status(2, ""));
			}
			
			userDao.update(user);
			
			throw new BadCredentialsException(msg);
		}

		// Here's the main logic of this custom authentication manager
		logger.info("User details are good and ready to go");
		
		user.setInvalidLogin(0);
		user.setLastLoginTime(lastLoginDatetime);
		user.setUpdatedDate(lastLoginDatetime);
		user.setUpdatedBy(auth.getName());
		
		userDao.update(user);
		
		return new UsernamePasswordAuthenticationToken(auth.getName(),
													   auth.getCredentials(), 
													   getAuthorities(user.getUserRoles()));
	}

	/**
	 * Retrieves the correct ROLE type depending on the access level, where
	 * access level is an Integer. Basically, this interprets the access value
	 * whether it's for a regular user or admin or supervisor.
	 * 
	 * @param list of UserRole userRole.
	 * @return collection of granted authorities.
	 */
	public Collection<GrantedAuthority> getAuthorities(List<UserRole> userRoles) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		
		// Get user role to granted access
		logger.info("Grant User Role to this user");		
		for (UserRole userRole : userRoles) {
			authList.add(new GrantedAuthorityImpl(userRole.getRole().getName()));
		}

		// Return list of granted authorities
		return authList;
	}

}
