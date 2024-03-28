package com.genth.kkdc.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.dao.EmailMasterDao;
import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.domain.User;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.model.EmailMasterModel;
import com.genth.kkdc.model.GenericModel;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("emailMaster")
public class EmailMasterController {
	
	private static Logger logger = Logger.getLogger("controller");
	 
	@Resource(name = "emailMasterDao")
	private EmailMasterDao emailMasterDao;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody EmailMasterModel getList(
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "max", required = true, defaultValue = "10") Integer max) {
		
		List<EmailMaster> emailList = new ArrayList<EmailMaster>();
		EmailMasterModel model = new EmailMasterModel();
		
		// Retrieve all systemValues from the service
		try {
			emailList = emailMasterDao.getAll();
		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Assign the total number of records found. This is used for paging
		model.setRecords(emailList.size());
		
		//Assign a dummy maximum rows per pages
		model.setMax(max);
		
		// Assign a page
		if (page == 0 && !emailList.isEmpty()) {
			model.setPage(1);
		} else if (page > 0 && !emailList.isEmpty()) {
			model.setPage(page);
		} else {
			model.setPage(0);
		}
		
		// Calculate rows per page
		final int startIdx = (page - 1) * max;
	    //final int endIdx = Math.min(startIdx + max, garageList.size());
		final int endIdx = emailList.size();
		model.setRows(emailList.subList(startIdx, endIdx));
		 
		return model;
		
	}
	 
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public @ResponseBody GenericModel edit(@RequestParam("id") Integer id, 
										   @ModelAttribute("editEmailMaster") EmailMaster email,
			 							   BindingResult result,
			 							   SessionStatus status) {
		
		GenericModel model = new GenericModel();
		
		try {
			  
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			email.setId(id);
			email.setAgentNumber(email.getAgentNumber());
			email.setReinsurerName(email.getReinsurerName());
			email.setFrom(  		 email.getFrom());
			email.setTo(  		 email.getTo());
			email.setCc(	  	 email.getCc());
			email.setBcc(		 email.getBcc());
			email.setMessage(	 email.getMessage());
			
			email.setUpdatedBy(auth.getName());
			email.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
			// Call service to edit
			emailMasterDao.edit(email);
			
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
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public @ResponseBody GenericModel add(
			@ModelAttribute("newEmailMaster") EmailMaster email,
		    BindingResult result,
		    SessionStatus status) {

		GenericModel model = new GenericModel();
		try {
			 
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			email.setAgentNumber(email.getAgentNumber());
			email.setReinsurerName(email.getReinsurerName());
			email.setFrom(  	   email.getFrom());
			email.setTo(  	   email.getTo());
			email.setCc(	       email.getCc());
			email.setBcc(		   email.getBcc());
			email.setMessage(	   email.getMessage());
			
			email.setCreatedBy(auth.getName());
			email.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			
			// Call service to add
			emailMasterDao.add(email);
			
			// Success. Return a custom model
			model.setSuccess(true);
			status.setComplete();
		
		} catch(Exception e) {
			e.printStackTrace();
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
			emailMasterDao.delete(id);
			
			// Success. Return a custom model
			model.setSuccess(true);
		} catch(Exception e) {
			
			String message = "";
			 
			message = messageResolver.getMessage("system.error", 
										 			 new Object[] { e.getMessage() });	
			 
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			model.setMessage(errors);
			model.setSuccess(false);
		}
		
		return model;
	}
	/*
	@RequestMapping(value = "getEmailList", method = RequestMethod.GET)
	public @ResponseBody String getEmailList() {

		List<EmailMaster> svList;
		try {
			svList = emailMasterDao.getEmailList();
		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// Initialize our custom agent model wrapper
		StringBuilder comboList = new StringBuilder();
		
		// Assign the result from the service to this model
		int round = 0;
		for (EmailMaster sv : svList) {
			round++;
			comboList.append(sv.getId().toString());
			comboList.append(":");
			comboList.append(sv.getEmailName());
			if (round < svList.size()) {
				comboList.append(";");
			}
		}
		return comboList.toString();
	}
	*/
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.registerCustomEditor(String.class, 
										new StringTrimmerEditor(
										false));
	}
}
