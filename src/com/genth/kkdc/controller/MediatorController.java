/**
 * 
 */
package com.genth.kkdc.controller;

import java.util.Collection;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
@Controller
public class MediatorController {
	 
	/**
	 * Retrieves the JSP index page.
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getIndexPage() {
		
		return "login";
	}
	
	/**
	 * Retrieves the JSP home page.
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String getHomePage() {
		
		return "main";
	}
	 
	
	/**Handles and retrieves the main userMaintenance JSP page.
	 * Retrieves the JSP page that contains our JqGrid.
	 *
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/userMaintenance", method = RequestMethod.GET)
	public String getUserPage() {
		
		// This will resolve to /WEB-INF/jsp/userMaintenance.jsp page
		return "userMaintenance";
	}
	
	@RequestMapping(value = "/roleMaintenance", method = RequestMethod.GET)
	public String getRolePage() {
		
		// This will resolve to /WEB-INF/jsp/roleMaintenance.jsp page
		return "roleMaintenance";
	}
	  
	@RequestMapping(value = "/KK_Document", method = RequestMethod.GET)
	public String getKK_DocumentPage() {
		
		// This will resolve to /WEB-INF/jsp/KK_Document.jsp page
		return "KK_Document";
	}
	@RequestMapping(value = "/verifyDocument", method = RequestMethod.GET)
	public String getVerifyDocumentPage() {
		
		// This will resolve to /WEB-INF/jsp/verifyDocument.jsp page
		return "verifyDocument";
	}
	@RequestMapping(value = "/KK_Document_History", method = RequestMethod.GET)
	public String getKK_Document_HistoryPage() {
		
		// This will resolve to /WEB-INF/jsp/KK_Document_History.jsp page
		return "KK_Document_History";
	}
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String getResetPasswordPage() {
		
		// This will resolve to /WEB-INF/jsp/resetPassword.jsp page
		return "resetPassword";
	}
//	@RequestMapping(value = "/BillpaymentNotice", method = RequestMethod.GET)
//	public String getBillpaymentNoticePage(
//			@RequestParam(value = "idtest", required = true, defaultValue = "0") String idtest
//	) {
//
//		System.out.println("0==>"+idtest);
//		// This will resolve to /WEB-INF/jsp/resetPassword.jsp page
//		return "BillpaymentNotice";
//	}
	
	@RequestMapping(value = "/BillpaymentNotice", method = RequestMethod.GET)
	public ModelAndView getBillpaymentNoticePage(
			@RequestParam(value = "idtest", required = true, defaultValue = "0") String idtest
	) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("BillpaymentNotice");
	    mav.addObject("idtest", idtest);
	    
		//System.out.println("0==>"+idtest);
		// This will resolve to /WEB-INF/jsp/resetPassword.jsp page
		return mav;
	}
	
	@RequestMapping(value = "/RejectByAppno", method = RequestMethod.GET)
	public ModelAndView getRejectByAppnoPage(
			@RequestParam(value = "appNo", required = true, defaultValue = "0") String appNo,
			@RequestParam(value = "searchStatus", required = true, defaultValue = "") String searchStatus
	) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("RejectByAppno");
	    mav.addObject("appNo", appNo);
	    mav.addObject("searchStatus", searchStatus);
	    
		return mav;
	}
	
	@RequestMapping(value = "/KK_OL_Callback", method = RequestMethod.GET)
	public String getKK_OL_CallbackPage() {
		
		// This will resolve to /WEB-INF/jsp/KK_OL_Callback.jsp page
		return "KK_OL_Callback";
	}

	@RequestMapping(value = "/KK_UL_Callback", method = RequestMethod.GET)
	public String getKK_UL_CallbackPage() {
		
		// This will resolve to /WEB-INF/jsp/KK_UL_Callback.jsp page
		return "KK_UL_Callback";
	}
	@RequestMapping(value = "/exportCallbackReport", method = RequestMethod.GET)
	public String getExportCallbackReportPage() {
		
		// This will resolve to /WEB-INF/jsp/exportCallbackReport.jsp page
		return "exportCallbackReport";
	}
}
