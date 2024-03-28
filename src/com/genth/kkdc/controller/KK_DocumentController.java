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
import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_CTRL;
import com.genth.kkdc.domain.KK_Document_ForKKGENBIZ;
import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.model.GenericModel;
import com.genth.kkdc.model.KK_DocumentModel;
import com.genth.kkdc.service.ResourceConfig;
import com.genth.kkdc.service.SendEmailService;

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("KK_Document")
public class KK_DocumentController {
	
	private static Logger logger = Logger.getLogger("controller");
	private static final String INPROGRESS = "In Progress";
	private static final String FOLLOW_UP = "Follow Up";
	private static final String NEW = "NEW";
	 
	private static final String COMPLETED 			= "Completed";
	private static final String IN_COMPLETED 		= "Incompleted";
	private static final String NOT_RECEIVED 		= "Not Received";
	private static final String WAIVED 				= "Waived";
	private static final String REJECTED 			= "Rejected";
	private static final String REJECTED_COF 		= "Rejected-COF";
	private static final String REJECTED_SA_PREM 	= "Rejected-SA_Prem";
	private static final String RESOLVED 	= "Resolved";

	private static final String ISCOMPLETE_SENDMAIL = "Y";
	
	@Resource(name = "KK_DocumentDao")
	private KK_DocumentDao kkDocumentDao;
	
	@Resource(name = "KK_Document_CTRLDao")
	private KK_Document_CTRLDao kkDocumentCTRLDao;
	
	@Resource(name = "KK_Document_LogDao")
	private KK_Document_LogDao kkDocumentLogDao;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
//	@Resource(name = "mailService")
//	private SendEmailService mail;
  
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody KK_DocumentModel getList(
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "max", required = true, defaultValue = "10") Integer max) {
		
		List<KK_Document> kkList = new ArrayList<KK_Document>();
		KK_DocumentModel model = new KK_DocumentModel();
		
		// Retrieve all systemValues from the service
		try {
//			kkList = kkDocumentDao.getAll();
			kkList = kkDocumentDao.getByKKDocStatus(" 'NEW' , 'In Progress' , 'ResolvedNEW' , 'ResolvedRejected' , 'ResolvedRejected-SA_Prem' " );
//			KK_Document xx = kkDocumentDao.getFromKKGENBIZ(1);
//			System.out.println("Status = "+xx.getStatus()+", CreateBy = "+ xx.getCreatedBy());
		} catch (CommonException e) {
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
	    //final int endIdx = Math.min(startIdx + max, garageList.size());
		final int endIdx = kkList.size();
		// Assign the result from the service to this model
		model.setRows(kkList.subList(startIdx, endIdx));
		
		// Return the model
		// Spring will automatically convert our AgentResponse as JSON object.
		// This is triggered by the @ResponseBody annotation.
		// It knows this because the JqGrid has set the headers to accept JSON
		// format when it made a request
		// Spring by default configvalues to convert the object to JSON
		return model;
		
	}
	 
	@RequestMapping(value = "listHistory", method = RequestMethod.GET)
	public @ResponseBody KK_DocumentModel getListHistory(
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "max", required = true, defaultValue = "10") Integer max) {
		
		List<KK_Document> kkList = new ArrayList<KK_Document>();
		KK_DocumentModel model = new KK_DocumentModel();
		
		// Retrieve all systemValues from the service
		try { 
			kkList = kkDocumentDao.getAll();
//			kkList = kkDocumentDao.getByKKDocStatus(" 'NEW' , 'In Progress' ");
//			KK_Document xx = kkDocumentDao.getFromKKGENBIZ(1);
//			System.out.println("Status = "+xx.getStatus()+", CreateBy = "+ xx.getCreatedBy());
		} catch (CommonException e) {
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
	
	@RequestMapping(value = "verifyDocument", method = RequestMethod.POST)
	public @ResponseBody byte[] verifyDocument(@RequestBody Object obj) {

		GenericModel model = new GenericModel();
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			KK_Document doc = mapper.convertValue(obj, new TypeReference<KK_Document>() { });
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			// update kk document to Inprogress (other user can not view)
			KK_Document updateDoc = new KK_Document(kkDocumentDao.get(doc.getId()));
			
			Collection<GrantedAuthority> authorities = auth.getAuthorities();

//			System.out.println("Admin ="+isRolePresent(authorities, "ROLE_UW_ADMIN") + ", Status="+updateDoc.getStatus());
			
			if( ! INPROGRESS.equals(updateDoc.getStatus()) || isRolePresent(authorities, "ROLE_UW_ADMIN") || isRolePresent(authorities, "ROLE_ADMIN") ){
				
				/* NEW, ResolvedXXX  can verify Document */
				
				updateDoc.setUpdatedBy(auth.getName());
				updateDoc.setStatus(INPROGRESS);
				
				kkDocumentDao.edit(updateDoc, "verifyDocument");
			}else{
				/* User can not allow verify INPROGRESS Document */
				if(INPROGRESS.equals(updateDoc.getStatus())){
					return "เกิดข้อผิดพลาก : เอกสารนี้ถูกใช้โดยผู้ใช้ท่านอื่นอยู่.".getBytes("UTF-8");
				}else{
					return "เกิดข้อผิดพลาก : เอกสารนี้รีวิวเรียบร้อยแล้ว.".getBytes("UTF-8");
				}
			}
			
//			if( NEW.equals(updateDoc.getStatus()) ||
//				updateDoc.getStatus().indexOf(RESOLVED) > -1 ||
//				isRolePresent(authorities, "ROLE_UW_ADMIN") || 
//				isRolePresent(authorities, "ROLE_ADMIN")){
//	 			
//				updateDoc.setUpdatedBy(auth.getName());
//				updateDoc.setStatus(INPROGRESS);
//				
//				kkDocumentDao.edit(updateDoc, "verifyDocument");
//				
//			}else if(INPROGRESS.equals(updateDoc.getStatus())){
//				return "เกิดข้อผิดพลาก : เอกสารนี้ถูกใช้โดยผู้ใช้ท่านอื่นอยู่.".getBytes("UTF-8");
//			}else{
//				return "เกิดข้อผิดพลาก : เอกสารนี้รีวิวเรียบร้อยแล้ว.".getBytes("UTF-8");
//			}
			
			
		} catch(Exception e) { 	
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			e.printStackTrace();
			
			try {
				return ("Error : "+message).getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try {
			return "success".getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean isRolePresent(Collection<GrantedAuthority> authorities, String role) {
	    boolean isRolePresent = false;
	    for (GrantedAuthority grantedAuthority : authorities) {
	      isRolePresent = grantedAuthority.getAuthority().equals(role);
	      if (isRolePresent) break;
	    }
	    return isRolePresent;
	  }
	
	@RequestMapping(value = "getDocCheckList", method = RequestMethod.POST)
	public @ResponseBody String getDocCheckList(@RequestBody Object obj) {

		GenericModel model = new GenericModel();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			KK_Document docMap = mapper.convertValue(obj, new TypeReference<KK_Document>() { });
			
			List<KK_Document_Log> logList = kkDocumentLogDao.getByDocId(docMap.getId().toString());

			// Initialize our custom agent model wrapper
			StringBuilder comboList = new StringBuilder();

			comboList.append("<table>");
			int round = 0;
			for (KK_Document_Log doc : logList) {
				
				if( (IN_COMPLETED+NOT_RECEIVED+REJECTED+REJECTED_COF+REJECTED_SA_PREM).indexOf(doc.getStatus()) > -1 ){
					comboList.append("<tr style=\"display:none\" class=\""+doc.getDocId()+"\">");
					comboList.append("	<td>"+doc.getItemDescription()+"</td>");
					comboList.append("</tr>");
					comboList.append("<tr style=\"display:none\" class=\""+doc.getDocId()+"\">");
					comboList.append("	<td>");				
					comboList.append("		<select id=\""+doc.getDocId()+"combobox\" class=\"docStatusCombobox\">");
					comboList.append("		<option value=\""+COMPLETED+"\">"+COMPLETED+"</option>");
//					if(IN_COMPLETED.equals(doc.getStatus())){
//						comboList.append("		<option selected=\"selected\" value=\""+IN_COMPLETED+"\">"+IN_COMPLETED+"</option>");
//					}else{
						comboList.append("		<option value=\""+IN_COMPLETED+"\">"+IN_COMPLETED+"</option>");
//					}
					
//					if(NOT_RECEIVED.equals(doc.getStatus())){
						comboList.append("		<option selected=\"selected\" value=\""+NOT_RECEIVED+"\">"+NOT_RECEIVED+"</option>");
//					}else{
//						comboList.append("		<option value=\""+NOT_RECEIVED+"\">"+NOT_RECEIVED+"</option>");
//					}
					
					comboList.append("		<option value=\""+WAIVED+"\">"+WAIVED+"</option>");
					
					/* เฉพาะ ใบคำขอเอาประกัน เท่านั้นที่มี Reject*/
					if( "ใบคำขอเอาประกัน".equals( doc.getItemDescription() ) ){
						
//						if(REJECTED.equals(doc.getStatus())){
//							comboList.append("		<option selected=\"selected\" value=\""+REJECTED+"\">"+REJECTED+"</option>");
//						}else{
							comboList.append("		<option value=\""+REJECTED+"\">"+REJECTED+"</option>");
//						}
						//comboList.append("		<option value=\""+REJECTED_COF+"\">"+REJECTED_COF+"</option>");
						//comboList.append("		<option value=\""+REJECTED_SA_PREM+"\">"+REJECTED_SA_PREM+"</option>");
					}
									
					comboList.append("		</select>");
					comboList.append("	</td>");
					comboList.append("</tr>");
					comboList.append("<tr style=\"display:none\" class=\""+doc.getDocId()+"\">");	
					comboList.append("	<td><input id=\""+doc.getDocId()+"remark\" class=\"docTextBox\" type=\"text\" value=\"\" placeholder=\"Input Remark.\"/>");
				    comboList.append("      <input id=\""+doc.getDocId()+"docLogId\" type=\"hidden\" value=\""+doc.getId()+"\"  /></td>");				
					comboList.append("</tr>");
				}
				
			} 
			comboList.append("</table>");

			return comboList.toString();
		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping(value = "cancelVerify", method = RequestMethod.POST)
	public @ResponseBody String cancelVerify(@RequestBody Object obj) {

		GenericModel model = new GenericModel();
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			KK_Document doc = mapper.convertValue(obj, new TypeReference<KK_Document>() { });
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			KK_Document updateDoc = new KK_Document(kkDocumentDao.get(doc.getId()));
			 
			updateDoc.setUpdatedBy(auth.getName());
			updateDoc.setStatus(NEW);
			
			kkDocumentDao.edit(updateDoc, "cancelVerify");
			
		} catch(Exception e) { 	
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			 
			
			return "Error : "+message;
		}
		
		return "success";
	}
	
	@RequestMapping(value = "updateKKDocStatus", method = RequestMethod.POST)
	public @ResponseBody String updateKKDocStatus(@RequestBody Object obj) {

		GenericModel model = new GenericModel();
		try {
			String subject = ResourceConfig.getCommonProperty("EMAIL_SUBJECT");
			String email01 = ResourceConfig.getCommonProperty("EMAIL_01");
			
			ObjectMapper mapper = new ObjectMapper();
			KK_Document doc = mapper.convertValue(obj, new TypeReference<KK_Document>() { });
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						
			String strBodyMessage = "";
			String kkDocStatus = "ERROR";
			String docLogStatus = doc.getDocLogStatus();
			String docRemark    = doc.getDocRemark();
			String docLogId    = doc.getDocLogIdArr();
			
			int cntNotReceived = 0;
			String rejectSubject = "-------";
			String rejectBody = "-------";
			
			if(docLogStatus != null && !"".equals(docLogStatus)){
				
				String[] stsArr = docLogStatus.split(",",-1);
				int isComplete = 0;
				int isReject = 0; // For Edit Transaction Requirement
				int isRejectCOF = 0; // For Edit Transaction Requirement
				int isRejectSAPrem = 0; // For Edit Transaction Requirement
				
				for(int i=0; i<stsArr.length; i++){
					if(IN_COMPLETED.equals(stsArr[i]) || NOT_RECEIVED.equals(stsArr[i]) ){
						if(NOT_RECEIVED.equals(stsArr[i])){
							cntNotReceived++;
						}
						kkDocStatus = FOLLOW_UP;
						
					}else if( REJECTED.equals(stsArr[i]) ){
						
						isReject++;
						rejectSubject = ResourceConfig.getCommonProperty("subReject1");						
						
					}else if( REJECTED_COF.equals(stsArr[i]) ){
						
						isRejectCOF++;
						rejectSubject = ResourceConfig.getCommonProperty("subReject2");
						
					}else if( REJECTED_SA_PREM.equals(stsArr[i]) ){
						
						isRejectSAPrem++;
						rejectSubject = ResourceConfig.getCommonProperty("subReject3");
						
					}else{
						isComplete++;
					}
				}
				
				if(stsArr.length == cntNotReceived){
					KK_Document updateDoc = new KK_Document(kkDocumentDao.get(doc.getId()));
					 
					updateDoc.setUpdatedBy(auth.getName());
					updateDoc.setStatus(NEW);
					
					kkDocumentDao.edit(updateDoc, "cancelVerify");
					// Error : user save with status not received all
					return "error001";
				}
				
				if( isComplete == stsArr.length){					
					
					kkDocStatus = COMPLETED;
					
				}else if( isReject > 0 ){
					
					kkDocStatus = REJECTED;
					
					
				}else if( isRejectCOF > 0 ){
					
					kkDocStatus = REJECTED_COF;
					
				}else if( isRejectSAPrem > 0 ){
					
					kkDocStatus = REJECTED_SA_PREM;
					
				}else{
					//Send mail for notification
					
					strBodyMessage = "";
				}
				
				// Update KK Document Log Start
				String[] stsRemarkArr = docRemark.split(",",-1);
				String[] stsdocLogIdArr = docLogId.split(",",-1);
				List<KK_Document_Log> docLogList = kkDocumentLogDao.getByDocId(doc.getId().toString());
//				int iSts = 0;
				int iDocNoti = 0;
				
				for(int iSts=0; iSts<stsdocLogIdArr.length; iSts++){
					for(KK_Document_Log log : docLogList){
						
						if( log.getId().toString().equals(stsdocLogIdArr[iSts])){
							 
							if(stsRemarkArr.length == 0){
								log.setRemark("");
							}else{
								log.setRemark(stsRemarkArr[iSts]);
							}
							log.setStatus(stsArr[iSts]);
							
							// Remove for new Requirement (Send All Status of 'Document Log')
							//if(IN_COMPLETED.equals(log.getStatus()) || NOT_RECEIVED.equals(log.getStatus())){
								iDocNoti++;
								
								strBodyMessage += "    " + iDocNoti + ". " + log.getItemDescription() + " : สถานะ " + log.getStatus();
								if(!"".equals(log.getRemark()) && log.getRemark().length() > 0){
									strBodyMessage += " เนื่องจาก : " + log.getRemark();
								}
								strBodyMessage += "<br>";
							//}
							
							kkDocumentLogDao.editKKGenBiz(log,"updateKKDocStatus");
							kkDocumentLogDao.edit        (log,"updateKKDocStatus");
						}
						
					}
				}
				// Update KK Document Log End
				
				doc.setBody(strBodyMessage);
				
				//System.out.println("size = "+docLogList.size());
			}
			
			// Update KK Document Start
			KK_Document updateDoc = new KK_Document(kkDocumentDao.get(doc.getId()));
			 
			updateDoc.setUpdatedBy(auth.getName());
			updateDoc.setStatus(kkDocStatus);

			kkDocumentDao.editKKGenBiz(updateDoc, "updateKKDocStatus");
			kkDocumentDao.edit        (updateDoc, "updateKKDocStatus");
			// Update KK Document End
			
			
			/* insert kk_upload_edit for Webservice Edit Transaction */
			//call store procedure here
			kkDocumentDao.popupEditWebServiceData(updateDoc.getAppNo());
			
			if(COMPLETED.equals(kkDocStatus) ){
				//Mark KK_Document Complete
				kkDocumentDao.completeDocument(updateDoc);
				kkDocumentDao.completeKKGENBIZDocument(updateDoc);
				 
			}

			
			if( REJECTED.equals(kkDocStatus) ||
				REJECTED_COF.equals(kkDocStatus) || 
				REJECTED_SA_PREM.equals(kkDocStatus) ){
				
				try {
					
					rejectBody = "เรียน "+doc.getAgentName()+" สาขา "+doc.getBranchStr();
					rejectBody += "<br><br>ผู้ขอเอาประกันภัย "+doc.getCustomerFirstName();
					rejectBody += "<br>ใบคำขอเอาประกันภัยเลขที่ "+doc.getAppNo();
					
					doc.setSubject(rejectSubject);
					doc.setBody(rejectBody);
					
					SendEmailService mail = new SendEmailService(doc,kkDocStatus);
					Thread trThread = new Thread(mail);
					trThread.start();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					String message = messageResolver.getMessage("system.error", 
						    new Object[] { e.getMessage() });
					return "Error : "+message;
				}
				
			}else if(ISCOMPLETE_SENDMAIL.equals(doc.getIsCompleteSendMail()) ||
			   !COMPLETED.equals(kkDocStatus) ){
				// Send email : Report Status
				try {
					subject = subject + " : " + doc.getAppNo() + " " + email01 + doc.getCustomerFirstName() + " " + doc.getCustomerLastname() + "]";
					doc.setSubject(subject);
					
					SendEmailService mail = new SendEmailService(doc);
					Thread trThread = new Thread(mail);
					trThread.start();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					String message = messageResolver.getMessage("system.error", 
							    new Object[] { e.getMessage() });
					return "Error : "+message;
				}
			}
			
			
			
		} catch(Exception e) { 	
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			//logger.error(message, e);
			 e.printStackTrace();
			
			return "Error : "+message;
		}
		
		return "success";
	}
	  
	@RequestMapping(value = "getAllDocument", method = RequestMethod.POST)
	public @ResponseBody String getAllDocument(@RequestBody Object obj) {

		GenericModel model = new GenericModel();
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			KK_Document doc = mapper.convertValue(obj, new TypeReference<KK_Document>() { });
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
			List<String[]> allDocList = kkDocumentDao.getAllDocument(doc.getAppNo());
			 
			return StringUtils.join(allDocList, ',');
			
			
		} catch(Exception e) { 	
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			//logger.error(message, e);
			 e.printStackTrace();
			
			return "Error : "+message;
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.registerCustomEditor(String.class, 
										new StringTrimmerEditor(
										false));
	}
}
