/**
 * 
 */
package com.genth.kkdc.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.genth.kkdc.domain.BillpaymentNotice;
import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_CTRL;
import com.genth.kkdc.domain.KK_Document_ForKKGENBIZ;
import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.model.BillpaymentNoticeModel;
import com.genth.kkdc.model.GenericModel;
import com.genth.kkdc.model.KK_DocumentModel;
import com.genth.kkdc.service.ResourceConfig;
import com.genth.kkdc.service.SendEmailService;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("BillpaymentNotice")
public class BillpaymentNoticeController {
	
	private static Logger logger = Logger.getLogger("controller");
	private static final String INPROGRESS = "In Progress";
	private static final String FOLLOW_UP = "Follow Up";
	private static final String NEW = "NEW";
	 
	private static final String COMPLETED = "Completed";
	private static final String IN_COMPLETED = "Incompleted";
	private static final String NOT_RECEIVED = "Not Received";
	private static final String WAIVED = "Waived";
	private static final String REJECTED = "Rejected";

	private static final String ISCOMPLETE_SENDMAIL = "Y";
	
	@Resource(name = "KK_DocumentDao")
	private KK_DocumentDao kkDocumentDao;
	
	@Resource(name = "KK_Document_CTRLDao")
	private KK_Document_CTRLDao kkDocumentCTRLDao;
	
	@Resource(name = "KK_Document_LogDao")
	private KK_Document_LogDao kkDocumentLogDao;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody BillpaymentNoticeModel getList(
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "max", required = true, defaultValue = "10") Integer max,
			@RequestParam(value = "idtest", required = true, defaultValue = "0") String idtest) {
		
		List<BillpaymentNotice> kkList = new ArrayList<BillpaymentNotice>();
		BillpaymentNoticeModel model = new BillpaymentNoticeModel();
		 		
		//System.out.println("1==>"+idtest);

		try {
			
			kkList = kkDocumentDao.getBillpaymentNotice(idtest); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Assign the total number of records found. This is used for paging
		model.setRecords(kkList.size());
		
		//Assign a dummy maximum rows per pages
		model.setMax(max);
		
		// Assign a page
		if (page == 0 && !kkList.isEmpty()) {
			model.setPage(1);
		} else if (page > 0 && !kkList.isEmpty()) {
			model.setPage(page);
		} else {
			model.setPage(0);
		}
		
		// Calculate rows per page
		final int startIdx = (page - 1) * max;
		final int endIdx = kkList.size();
		model.setRows(kkList.subList(startIdx, endIdx));

		return model;
		
	}
	 
	@RequestMapping(value = "sendBillpaymentNotice", method = RequestMethod.POST)
	public @ResponseBody GenericModel sendBillpaymentNotice(@RequestBody Object obj) {

		GenericModel model = new GenericModel();

		try {
			ObjectMapper mapper = new ObjectMapper();
			BillpaymentNotice bill = mapper.convertValue(obj, new TypeReference<BillpaymentNotice>() { });
			 
			List<String> da = new ArrayList<String>();
			da.add(bill.getAppNo());
			
			kkDocumentDao.updatePolicyNumber(bill.getAppNo(),bill.getPolicyNo());
			
			bill.setSubject("เรื่อง แจ้งผลการพิจารณาเบื้องต้น");
			
			String mailBody = "เรียน "+bill.getAgentName()+" สาขา "+bill.getBranchStr();
			mailBody += "<br><br>ผู้ขอเอาประกันภัย "+bill.getCustomerFirstName();
			mailBody += "<br>ใบคำขอเอาประกันภัยเลขที่ "+bill.getAppNo();
			mailBody += "<br>กรมธรรม์เลขที่ "+bill.getPolicyNo();
			bill.setBody(mailBody);
			
			SendEmailService.sendMailBillpaymentNotify(bill);
			
			model.setMessage(da);
			model.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.registerCustomEditor(String.class, 
										new StringTrimmerEditor(
										false));
	}
}
