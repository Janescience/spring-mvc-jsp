/**
 * 
 */
package com.genth.kkdc.controller;
 
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.genth.kkdc.dao.UserDao;
import com.genth.kkdc.domain.*;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("getUserLoginInfo")
public class UserLoginInfoController {
	
	private static Logger logger = Logger.getLogger("UserController");

	@Resource(name = "userDao")
	private UserDao userDao;
	
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
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.setDisallowedFields(new String[] { "id" });

		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
}
