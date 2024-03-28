/**
 * 
 */
package com.genth.kkdc.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.service.CallbackReportWriter;
 

/**
 * @author Thanompong.W
 * 
 */
@Controller
@RequestMapping("exportCallbackReport")
public class ExportCallbackReportController {
	
	private static Logger logger = Logger.getLogger("controller");
	 
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	@Resource(name="callbackReportWriter")
	CallbackReportWriter reportXLSService;
	
	@RequestMapping(value = "exportCallbackReport", method = RequestMethod.GET)
	public void exportQaIncidentPercentReport(HttpServletResponse response,
			@RequestParam(value="dateFrom", required=false) String dateFrom,
			@RequestParam(value="dateTo", required=false) String dateTo) {
		try {
			
			
			Dictionary<String, Object> options = new Hashtable<String, Object>();
			if(dateFrom != null) options.put("dateFrom", dateFrom);
			if(dateTo != null) options.put("dateTo", dateTo);
			reportXLSService.writeExcelCallbackReport( response, options );
		 		
		} catch(Exception e) {
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
		}
	}
	
	@RequestMapping(value = "export_OL_CallbackReport", method = RequestMethod.GET)
	public void export_OL_CallbackReport(HttpServletResponse response, 
			@RequestParam(value="dateFrom", required=false) String dateFrom,
			@RequestParam(value="dateTo", required=false) String dateTo) {
		try {
			
			Dictionary<String, Object> options = new Hashtable<String, Object>();
			if(dateFrom != null) options.put("dateFrom", dateFrom);
			if(dateTo != null) options.put("dateTo", dateTo);
			reportXLSService.writeExcel_OL_CallbackReport( response, options );
		 		
		} catch(Exception e) {
			String message = messageResolver.getMessage("system.error", 
 				    								    new Object[] { e.getMessage() });
			
			logger.error(message, e);
			
			List<String> errors = new ArrayList<String>();
			errors.add(message);
			
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		dataBinder.registerCustomEditor(String.class, 
										new StringTrimmerEditor(
										false));
	}
}
