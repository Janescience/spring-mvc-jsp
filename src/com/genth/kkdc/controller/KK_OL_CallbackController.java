/**
 * 
 */
package com.genth.kkdc.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.dao.KK_DocumentDao;
import com.genth.kkdc.dao.KK_Document_CTRLDao;
import com.genth.kkdc.dao.KK_Document_LogDao;
import com.genth.kkdc.dao.KK_OL_CallbackDao;
import com.genth.kkdc.domain.RejectByAppno;
import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_CTRL;
import com.genth.kkdc.domain.KK_Document_ForKKGENBIZ;
import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.domain.KK_OL_Callback;
import com.genth.kkdc.domain.KK_UL_Fund_Selection;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.model.GenericModel;
import com.genth.kkdc.model.KK_DocumentModel;
import com.genth.kkdc.model.KK_OL_CallbackModel;
import com.genth.kkdc.model.RejectByAppnoModel;
import com.genth.kkdc.service.CallbackReportWriter;
import com.genth.kkdc.service.ResourceConfig;
import com.genth.kkdc.service.SendEmailService;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("KK_OL_Callback")
public class KK_OL_CallbackController {
	
	private static Logger logger = Logger.getLogger("controller");
	private static final String INPROGRESS = "In Progress";
	private static final String FOLLOW_UP = "Follow Up";
	private static final String NEW = "NEW";
	 
	private static final String COMPLETED = "Completed";
	private static final String IN_COMPLETED = "Incompleted";
	private static final String NOT_RECEIVED = "Not Received";
	private static final String WAIVED = "Waived";
	private static final String REJECTED 			= "Rejected";
	private static final String REJECTED_COF 		= "Rejected-COF";
	private static final String REJECTED_SA_PREM 	= "Rejected-SA_Prem";

	private static final String ISCOMPLETE_SENDMAIL = "Y";
	
	@Resource(name = "KK_OL_CallbackDao")
	private KK_OL_CallbackDao kkUlCallbackDao;
	 
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody KK_OL_CallbackModel getList(
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "max", required = true, defaultValue = "10") Integer max,
			@RequestParam(value = "custNameSearch", required = true, defaultValue = "0") String custNameSearch,
			@RequestParam(value = "searchStatus", required = true, defaultValue = "") String searchStatus) {
		
		List<KK_OL_Callback> kkCallBackList = new ArrayList<KK_OL_Callback>();
		KK_OL_CallbackModel model = new KK_OL_CallbackModel();
		 		
//		System.out.println("1==>"+searchStatus);

		try {
			
			kkCallBackList = kkUlCallbackDao.getAll(custNameSearch,searchStatus); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Assign the total number of records found. This is used for paging
		model.setRecords(kkCallBackList.size());
		
		//Assign a dummy maximum rows per pages
		model.setMax(max);
		
		// Assign a page
		if (page == 0 && !kkCallBackList.isEmpty()) {
			model.setPage(1);
		} else if (page > 0 && !kkCallBackList.isEmpty()) {
			model.setPage(page);
		} else {
			model.setPage(0);
		}
		
		// Calculate rows per page
		final int startIdx = (page - 1) * max;
		final int endIdx = kkCallBackList.size();
		model.setRows(kkCallBackList.subList(startIdx, endIdx));

		return model;
		
	}
	
	@RequestMapping(value = "getFundList", method = RequestMethod.POST)
	public @ResponseBody String getFundList(@RequestBody Object obj) {
		
		String returnTable = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			KK_UL_Fund_Selection fund = mapper.convertValue(obj, new TypeReference<KK_UL_Fund_Selection>() { });
			
			returnTable = kkUlCallbackDao.getUlFunSelection(fund.getSisno()); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnTable;
	}
	
	@RequestMapping(value = "userConfirm", method = RequestMethod.POST)
	public @ResponseBody String userConfirm(@RequestBody Object obj) {
		
		String returnTable = "";
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			ObjectMapper mapper = new ObjectMapper();
			KK_OL_Callback cb = mapper.convertValue(obj, new TypeReference<KK_OL_Callback>() { });
			cb.setCallBy(auth.getName());
			
			kkUlCallbackDao.editStatus(cb, cb.getStatus()); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnTable;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.registerCustomEditor(String.class, 
										new StringTrimmerEditor(
										false));
	}
}
