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
import com.genth.kkdc.domain.RejectByAppno;
import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_CTRL;
import com.genth.kkdc.domain.KK_Document_ForKKGENBIZ;
import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.model.GenericModel;
import com.genth.kkdc.model.KK_DocumentModel;
import com.genth.kkdc.model.RejectByAppnoModel;
import com.genth.kkdc.service.ResourceConfig;
import com.genth.kkdc.service.SendEmailService;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("RejectByAppno")
public class RejectByAppnoController {
	
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
	
	@Resource(name = "KK_DocumentDao")
	private KK_DocumentDao kkDocumentDao;
	
	@Resource(name = "KK_Document_CTRLDao")
	private KK_Document_CTRLDao kkDocumentCTRLDao;
	
	@Resource(name = "KK_Document_LogDao")
	private KK_Document_LogDao kkDocumentLogDao;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody RejectByAppnoModel getList(
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "max", required = true, defaultValue = "10") Integer max,
			@RequestParam(value = "idtest", required = true, defaultValue = "0") String idtest,
			@RequestParam(value = "searchStatus", required = true, defaultValue = "") String searchStatus) {
		
		List<RejectByAppno> kkList = new ArrayList<RejectByAppno>();
		RejectByAppnoModel model = new RejectByAppnoModel();
		 		
//		System.out.println("1==>"+searchStatus);

		try {
			
			kkList = kkDocumentDao.getRejectByAppno(idtest,searchStatus); 
			
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
	
	@RequestMapping(value = "sendRejectByAppno", method = RequestMethod.POST)
	public @ResponseBody String sendRejectByAppno(@RequestBody Object obj) {

		String status = "";
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			ObjectMapper mapper = new ObjectMapper();
			RejectByAppno bill = mapper.convertValue(obj, new TypeReference<RejectByAppno>() { });
			 
			KK_Document doc = new KK_Document();
			doc.setId(bill.getId());
			doc.setCustomerFirstName(bill.getCustomerFirstName());
			doc.setBranchStr(bill.getBranchStr());
			doc.setAppNo(bill.getAppNo());
			doc.setStatus(bill.getStatus());
			doc.setStaffEmail(bill.getStaffEmail());
			doc.setAgentName(bill.getAgentName());
			
			try {
				// Update KK Document Start
				KK_Document updateDoc = new KK_Document(kkDocumentDao.get(doc.getId()));
				 
				updateDoc.setUpdatedBy(auth.getName());
				updateDoc.setStatus(bill.getStatus());
	
				kkDocumentDao.editKKGenBiz(updateDoc, "updateKKDocStatus");
				kkDocumentDao.edit        (updateDoc, "updateKKDocStatus");
				// Update KK Document End
				
				
				/* insert kk_upload_edit for Webservice Edit Transaction */
				//call store procedure here
				kkDocumentDao.popupEditWebServiceData(updateDoc.getAppNo());
				
				
				/* insert kk_document_log for Rejected-SA-Rrem */
				if( REJECTED_SA_PREM.equals(updateDoc.getStatus()) ){
					kkDocumentLogDao.insertKKDocumentLog(updateDoc.getId(),updateDoc.getAppNo());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				String message = messageResolver.getMessage("system.error", 
	 				    new Object[] { e.getMessage() });

				logger.error(message, e);
				
				List<String> errors = new ArrayList<String>();
				errors.add(message);
				return "error001";
			}
			
			try {
				String rejectSubject = "-------";
				if( REJECTED.equals( bill.getStatus() )){
					
					rejectSubject = ResourceConfig.getCommonProperty("subReject1");						
					status = "success";
				}else if( REJECTED_COF.equals( bill.getStatus() )){
					
					rejectSubject = ResourceConfig.getCommonProperty("subReject2");
					status = "success";
				}else if( REJECTED_SA_PREM.equals( bill.getStatus() )){
					
					rejectSubject = ResourceConfig.getCommonProperty("subReject3");
					status = "success";
					
				}else if( COMPLETED.equals( bill.getStatus() )){
					
					rejectSubject = ResourceConfig.getCommonProperty("subReject4");
					status = "success001";
					
				}
				
				// Completed doc no need send mail.
				if(! COMPLETED.equals( bill.getStatus() )){
				
					String rejectBody = "-------";
					
					rejectBody = "เรียน "+doc.getAgentName()+" สาขา "+doc.getBranchStr();
					rejectBody += "<br><br>ผู้ขอเอาประกันภัย "+doc.getCustomerFirstName();
					rejectBody += "<br>ใบคำขอเอาประกันภัยเลขที่ "+doc.getAppNo();
					
					doc.setSubject(rejectSubject);
					doc.setBody(rejectBody);
					
					SendEmailService mail = new SendEmailService(doc,bill.getStatus());
					Thread trThread = new Thread(mail);
					trThread.start();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				String message = messageResolver.getMessage("system.error", 
	 				    new Object[] { e.getMessage() });

				logger.error(message, e);
				
				List<String> errors = new ArrayList<String>();
				errors.add(message);

				return "error002.";
			}
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String message = messageResolver.getMessage("system.error", 
 				    new Object[] { e.getMessage() });

			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
			return "Error:" + message;
		}
		
		return status;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.registerCustomEditor(String.class, 
										new StringTrimmerEditor(
										false));
	}
}
