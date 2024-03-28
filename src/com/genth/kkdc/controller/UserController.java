/**
 * 
 */
package com.genth.kkdc.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.genth.kkdc.common.util.EncodingResolver;
import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.dao.UserDao;
import com.genth.kkdc.domain.*;
import com.genth.kkdc.model.GenericModel;
import com.genth.kkdc.model.UserModel;
import com.genth.kkdc.web.validator.UserValidator;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	private static Logger logger = Logger.getLogger("UserController");

	@Resource(name = "userDao")
	private UserDao userDao;
	
	@Autowired
	private UserValidator validator;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	@Resource(name = "encodingService")
	private EncodingResolver encoding;

	// We need an Md5 encoder since our passwords in the database are Md5
	// encoded.
	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody UserModel getAll(@RequestParam(value = "page", 
														 required = true, 
														 defaultValue = "0") Integer page,
			 							   @RequestParam(value = "max", 
			 								  		     required = true, 
			 								  			 defaultValue = "10") Integer max) {

		// Retrieve all users from the service
		List<User> users = userDao.getAll();

		// Initialize our custom User model wrapper
		UserModel model = new UserModel();
		// Assign the total number of records found. This is used for paging
		model.setRecords(users.size());
		
		//Assign a dummy maximum rows per pages
		model.setMax(max);
		// Assign a page
		if (page == 0 && !users.isEmpty()) {
			model.setPage(1);
		} else if (page > 0 && !users.isEmpty()) {
			model.setPage(page);
		} else {
			model.setPage(0);
		}
		
		// Calculate rows per page
		final int startIdx = (page - 1) * max;
	    final int endIdx = Math.min(startIdx + max, users.size());

		// Assign the result from the service to this model
		model.setRows(users.subList(startIdx, endIdx));

		// Return the model
		// Spring will automatically convert our UserModel as JSON object.
		// This is triggered by the @ResponseBody annotation.
		// It knows this because the JqGrid has set the headers to accept JSON
		// format when it made a request
		// Spring by default users to convert the object to JSON
		return model;
	}

	@RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
	public @ResponseBody User getUserInfo(@RequestParam String userName) {

		// Retrieve all users from the service
		User user = userDao.findByUserName(userName);

		// Return the model
		// Spring will automatically convert our UserModel as JSON object.
		// This is triggered by the @ResponseBody annotation.
		// It knows this because the JqGrid has set the headers to accept JSON
		// format when it made a request
		// Spring by default users to convert the object to JSON
		return user;
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public @ResponseBody GenericModel add(@ModelAttribute("newUser") User user,
				 						  BindingResult result,
				 						  SessionStatus status) {

		GenericModel model = new GenericModel();
		try {
			// Do custom validation here or in your service
			validator.validate(user, result);
			if (result.hasErrors()) {
				// A failure. Return a custom model as well				
				model.setMessage(messageResolver.handleErrorMsg(result));
				model.setSuccess(false);
			} else {
	
				// Construct our user object
				// Assign the values from the parameters
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
				user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
				user.setFirstName(encoding.convertToThai(user.getFirstName()));
				user.setLastName(encoding.convertToThai(user.getLastName()));
				user.setInvalidLogin(0);
				user.setPasswordStatus(new PasswordStatus(2, ""));
				user.setCreatedBy(auth.getName());
				user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				
				Integer statusId = Integer.parseInt(user.getStatus().getName());
				user.setStatus(new Status(statusId, null));
				
				// Call service to add
				userDao.add(user);

				// Success. Return a custom model
				model.setSuccess(true);
				status.setComplete();
			}
		} catch(Exception e) {
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			model.setMessage(errors);
			model.setSuccess(false);
		}
		
		return model;
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public @ResponseBody GenericModel validate(@ModelAttribute("newUser") User user,
				 						       BindingResult result,
				 						       SessionStatus status) {

		GenericModel model = new GenericModel();
		try {
			// Do custom validation here or in your service
			validator.validate(user, result);
			if (result.hasErrors()) {
				// A failure. Return a custom model as well				
				model.setMessage(messageResolver.handleErrorMsg(result));
				model.setSuccess(false);
			} else {

				// Success. Return a custom model
				model.setSuccess(true);
				status.setComplete();
			}
		} catch(Exception e) {
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			model.setMessage(errors);
			model.setSuccess(false);
		}
		
		return model;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public @ResponseBody GenericModel edit(@ModelAttribute("editUser") User user,
			 							   BindingResult result,
			 							   SessionStatus status) {
		
		GenericModel model = new GenericModel();
		
		try {

			// Do custom validation here or in your service
			validator.validate(user, result);
			if (result.hasErrors()) {
				// A failure. Return a custom model as well				
				model.setMessage(messageResolver.handleErrorMsg(result));
				model.setSuccess(false);
			} else {

				// Construct our user object
				// Assign the values for update User
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				user.setUpdatedBy(auth.getName());
				user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
				user.setFirstName(encoding.convertToThai(user.getFirstName()));
				user.setLastName(encoding.convertToThai(user.getLastName()));
				
				Integer statusId = Integer.parseInt(user.getStatus().getName());
				user.setStatus(new Status(statusId, null));
				// Call service to edit
				userDao.edit(user);
				
				// Success. Return a custom model
				model.setSuccess(true);
			}		
		} catch(Exception e) {
			String message = messageResolver.getMessage("system.error", 
									 				    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			model.setMessage(errors);
			model.setSuccess(false);
		}
		
		return model;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody GenericModel delete(@RequestParam("id") Integer id) {
		
		GenericModel model = new GenericModel();
		
		try {
			
			// Call service to delete
			userDao.delete(id);
			
			// Success. Return a custom model
			model.setSuccess(true);
		} catch(Exception e) {
			
			String message = "";
			
			if (e.getMessage().indexOf("Error Code: 547") != -1) {
				message = messageResolver.getMessage("validation.delete.user");	
				
			} else {
				message = messageResolver.getMessage("system.error", 
										 			 new Object[] { e.getMessage() });	
			}
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			model.setMessage(errors);
			model.setSuccess(false);
		}
		
		return model;
	}
	
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	public @ResponseBody GenericModel resetPassword(@RequestBody Object obj) {
		
		GenericModel model = new GenericModel();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.convertValue(obj, new TypeReference<User>() { });
			// Do custom validation here or in your service
			// Construct our user object
			// Assign the values for update User
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
			user.setUpdatedBy(auth.getName());
			user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
			// Call service to edit
			userDao.resetPassword(user);
			
			// Success. Return a custom model
			model.setSuccess(true);	
		} catch(Exception e) {
			String message = messageResolver.getMessage("system.error", 
									 				    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			model.setMessage(errors);
			model.setSuccess(false);
		}
		
		return model;
	}
	
	@RequestMapping(value = "resetPasswordByUser", method = RequestMethod.POST)
	public @ResponseBody GenericModel resetPasswordByUser(@RequestBody Object obj) {
		
		GenericModel model = new GenericModel();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.convertValue(obj, new TypeReference<User>() { });
			// Do custom validation here or in your service
			// Construct our user object
			// Assign the values for update User
			
			User userx = userDao.findByUserName(user.getUserName());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			user.setId(userx.getId());
			user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
			user.setUpdatedBy(auth.getName());
			user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
			// Call service to edit
			userDao.resetPassword(user);
			
			// Success. Return a custom model
			model.setSuccess(true);	
		} catch(Exception e) {
			String message = messageResolver.getMessage("system.error", 
									 				    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			model.setMessage(errors);
			model.setSuccess(false);
		}
		
		return model;
	}
	
	@RequestMapping(value = "roleList", method = RequestMethod.GET)
	public @ResponseBody String getRoleList() {

		// Retrieve all agents from the service
		List<Role> roleList = userDao.getRoleList();

		// Initialize our custom agent model wrapper
		StringBuilder comboList = new StringBuilder();
		
		// Assign the result from the service to this model
		int round = 0;
		for (Role role : roleList) {
			round++;
			comboList.append(role.getId().toString());
			comboList.append(":");
			comboList.append(role.getName());
			if (round < roleList.size()) {
				comboList.append(";");
			}
		}

		// Return the model
		// Spring will automatically convert our ComboBoxModel as JSON object.
		// This is triggered by the @ResponseBody annotation.
		// It knows this because the JqGrid has set the headers to accept JSON
		// format when it made a request
		// Spring by default agents to convert the object to JSON
		return comboList.toString();
	}
	
	@RequestMapping(value = "statusList", method = RequestMethod.GET)
	public @ResponseBody String getStatusList() {

		// Retrieve all agents from the service
		List<Status> statusList = userDao.getUserStatusList();

		// Initialize our custom agent model wrapper
		StringBuilder comboList = new StringBuilder();
		
		// Assign the result from the service to this model
		int round = 0;
		for (Status status : statusList) {
			round++;
			comboList.append(status.getId().toString());
			comboList.append(":");
			comboList.append(status.getName());
			if (round < statusList.size()) {
				comboList.append(";");
			}
		}

		// Return the model
		// Spring will automatically convert our ComboBoxModel as JSON object.
		// This is triggered by the @ResponseBody annotation.
		// It knows this because the JqGrid has set the headers to accept JSON
		// format when it made a request
		// Spring by default agents to convert the object to JSON
		return comboList.toString();
	}
	
	@RequestMapping(value = "qaStaffList", method = RequestMethod.GET)
	public @ResponseBody String getQaStaffList() {

		// Retrieve all agents from the service
		List<User> userList = userDao.getQaStaff();

		// Initialize our custom agent model wrapper
		StringBuilder comboList = new StringBuilder();
		
		// Assign the result from the service to this model
		int round = 0;
		for (User user : userList) {
			round++;
			comboList.append('"'+user.getId().toString()+'"');
			comboList.append(":");
			comboList.append('"'+user.getFirstName() + " " + user.getLastName()+'"');
			if (round < userList.size()) {
				comboList.append(",");
			}
		}
		return comboList.toString();
	}
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.setDisallowedFields(new String[] { "id" });

		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
}
