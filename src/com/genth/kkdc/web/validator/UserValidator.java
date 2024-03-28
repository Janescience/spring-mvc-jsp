/**
 * 
 */
package com.genth.kkdc.web.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.*;

import com.genth.kkdc.dao.UserDao;
import com.genth.kkdc.domain.User;

/**
 * @author Thanompong.W
 *
 */
@Component("userValidator")
public class UserValidator implements Validator {
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	/**
	 * 
	 */
	public UserValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
												  "userName", 
												  "required",
												  new Object[] { "User Name" });
		

		User user = (User) model;

		if (!errors.hasErrors()) {
			
			if (errors.getObjectName() != null 
					&& errors.getObjectName().equals("newUser")) {
				User existingUser = userDao.findByUserName(user.getUserName());
				if (existingUser != null) {
					errors.reject("validation.exists", 
								  new Object[] { "User Name", user.getUserName() }, 
								  "User Name : " 
								  + existingUser.getUserName() 
								  + "already exists");
				}				
			}
		}
		
		if (!errors.hasErrors()) {
		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
													  "password", 
													  "required",
													  new Object[] { "Password" });
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
													  "confirmPassword", 
													  "required",
													  new Object[] { "Confirm Password" });
			
			if (!errors.hasErrors()) {
				
				if (!user.getPassword().equals(user.getConfirmPassword())) {
					errors.reject("validation.mismath.password");
				}
				
				if (!errors.hasErrors()) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
															  "firstName", 
															  "required",
															  new Object[] { "First Name" });
					
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
															  "lastName", 
															  "required",
															  new Object[] { "Last Name" });
					
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
															  "role", 
															  "required",
															  new Object[] { "Role" });
					
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
															  "status.name", 
															  "required",
															  new Object[] { "Status" });
				}
			}
		}
	}

}
