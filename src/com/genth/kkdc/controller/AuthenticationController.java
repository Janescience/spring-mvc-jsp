package com.genth.kkdc.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("auth")
public class AuthenticationController {

	protected static Logger logger = Logger.getLogger("controller");

	public AuthenticationController() {

	}

	/**
	 * Handles and retrieves the login JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(
			@RequestParam(value = "error", required = false) boolean error,
			ModelMap model) {
		logger.info("Received request to show login page");

		// Add an error message to the model if login is unsuccessful
		// The 'error' parameter is set to true based on the when the
		// authentication has failed.
		// We declared this under the authentication-failure-url attribute
		// inside the spring-security.xml
		/*
		 * See below: <form-login login-page="/auth/login.html"
		 * authentication-failure-url="/auth/login.html?error=true"
		 * default-target-url="/main.html"/>
		 */

		// This will resolve to /WEB-INF/jsp/login.jsp
		return "login";
	}

	/**
	 * Handles and retrieves the denied JSP page. This is shown whenever a
	 * regular user tries to access an admin only page.
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String getDeniedPage() {
		logger.debug("Received request to show denied page");

		// This will resolve to /WEB-INF/jsp/accessDenied.jsp
		return "accessDenied";
	}
	
	@RequestMapping(value = "/loginPopup", method = RequestMethod.GET)
	public String getLoginPopupPage(
			@RequestParam(value = "error", required = false) boolean error,
			ModelMap model) {
		logger.info("Received request to show login page");

		// Add an error message to the model if login is unsuccessful
		// The 'error' parameter is set to true based on the when the
		// authentication has failed.
		// We declared this under the authentication-failure-url attribute
		// inside the spring-security.xml
		/*
		 * See below: <form-login login-page="/auth/login.html"
		 * authentication-failure-url="/auth/login.html?error=true"
		 * default-target-url="/main.html"/>
		 */

		// This will resolve to /WEB-INF/jsp/loginPopup.jsp
		return "loginPopup";
	}

}
