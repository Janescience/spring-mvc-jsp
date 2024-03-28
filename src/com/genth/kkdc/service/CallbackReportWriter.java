package com.genth.kkdc.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genth.kkdc.common.util.ExcelUtil;
import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.common.util.StringUtil;
import com.genth.kkdc.controller.KK_UL_CallbackController;
import com.genth.kkdc.dao.KK_OL_CallbackDao;
import com.genth.kkdc.dao.KK_UL_CallbackDao;
import com.genth.kkdc.domain.KK_OL_Callback;
import com.genth.kkdc.domain.KK_UL_Callback;

/**
 * Writes the report to the output stream
 * 
 * @author Thanompong.W
 */
@Transactional
@Service("callbackReportWriter")
public class CallbackReportWriter {

	private static final String XLS_FILE_EXTENSION = ".xls";
	
	private static int SHEET_ALL     	= 0;
	private static int SHEET_SUCCESS 	= 1;
	private static int SHEET_CALLING	= 2;
	private static int SHEET_REJECT		= 3;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	@Resource(name = "KK_UL_CallbackDao")
	private KK_UL_CallbackDao kkUlCallbackDao;
	
	@Resource(name = "KK_OL_CallbackDao")
	private KK_OL_CallbackDao kk_OL_CallbackDao;
	
	public static void writeFile(HttpServletResponse response, 
								 HSSFWorkbook workbook, String fileName)
			throws Exception {
		
		System.setProperty("file.encoding", "windows-874");
		OutputStream os = null;

		try {
			response.setContentType("application/vnd.ms-excel; charset=windows-874");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Disposition", 
					"attachment; filename=\"" + fileName + XLS_FILE_EXTENSION + "\"");
			
			os = response.getOutputStream();
			workbook.write(os);
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);

			byte[] outArray = outByteStream.toByteArray();
			os.write(outArray);
			os.flush();
			
//			FileOutputStream fileOut = new FileOutputStream("C:\\Temp\\"+ fileName + XLS_FILE_EXTENSION);
//			workbook.write(fileOut);
//			fileOut.flush();
//			fileOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
	
	public void writeExcelCallbackReport(HttpServletResponse response ) {
		writeExcelCallbackReport(response, null);
	}
	
	public void writeExcelCallbackReport(HttpServletResponse response, Dictionary<String, Object> options ) {

		try {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		  
		String reportTemplate = ResourceConfig.getCommonProperty("REPORT_TEMPLATE_AUTO_ISSUE_DAILY");
		String reportFolder   = ResourceConfig.getCommonProperty("REPORT_TEMPLATE_FOLDER");
		String timeStr        = formatter.format(new Date(System.currentTimeMillis()));
		String[] saveFileName = {reportTemplate.replace("Templates.xls", timeStr+".xls")};
		String [] displayName = {saveFileName[0].replace(reportFolder, "")};
		
		//Create excel from template
		HSSFWorkbook workBook = ExcelUtil.createExcelFromTemplate(reportTemplate);
 	
		Date startDate = null;
		Date endDate = null;
		String headerRange = null;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if(options != null) {
			try {
			startDate = format.parse((String) options.get("dateFrom"));
			} catch(Exception e) {}
			try {
			endDate = format.parse((String) options.get("dateTo"));
			} catch(Exception e) {}
			if(startDate != null) {
				if(endDate == null) {
					endDate = new Date();
				}
				headerRange = "��ǧ�ѹ���͹��ѵ� �ҡ "+format.format(startDate)+" �֧ "+format.format(endDate);
			} else {
				endDate = null;
			}
		}
		List<KK_UL_Callback> cbList = kkUlCallbackDao.getAll("", "", startDate, endDate);
		 
		int startRow = 3;
		changeExcelReportHeader(workBook, SHEET_ALL, 0, 0, headerRange, false);
		populateExcelReport(startRow,workBook,SHEET_ALL,cbList);
		startRow = 3;
		cbList = kkUlCallbackDao.getAll("", "Y");
		changeExcelReportHeader(workBook, SHEET_SUCCESS, 0, 0, headerRange, false);
		populateExcelReport(startRow,workBook,SHEET_SUCCESS,cbList);
		startRow = 3;
		cbList = kkUlCallbackDao.getAll("", "W");
		changeExcelReportHeader(workBook, SHEET_CALLING, 0, 0, headerRange, false);
		populateExcelReport(startRow,workBook,SHEET_CALLING,cbList);
		startRow = 3;
		cbList = kkUlCallbackDao.getAll("", "R");
		changeExcelReportHeader(workBook, SHEET_REJECT, 0, 0, headerRange, false);
		populateExcelReport(startRow,workBook,SHEET_REJECT,cbList);
		
		writeFile(response, workBook, "KK_UL_Callback_"+timeStr);
		
		} catch (Exception e) {
			String message = messageResolver.getMessage("system.error",
									new Object[] { e.getMessage() });
			 e.printStackTrace();
		}
		
	}
	
	
	public void writeExcel_OL_CallbackReport(HttpServletResponse response ) {
		writeExcel_OL_CallbackReport(response, null);
	}
	
	public void writeExcel_OL_CallbackReport(HttpServletResponse response, Dictionary<String, Object> options ) {

		try {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		  
		String reportTemplate = ResourceConfig.getCommonProperty("REPORT_TEMPLATE_OL_CALLBACK");
		String reportFolder   = ResourceConfig.getCommonProperty("REPORT_TEMPLATE_FOLDER");
		String timeStr        = formatter.format(new Date(System.currentTimeMillis()));
		String[] saveFileName = {reportTemplate.replace("Templates.xls", timeStr+".xls")};
		String [] displayName = {saveFileName[0].replace(reportFolder, "")};
		
		//Create excel from template
		HSSFWorkbook workBook = ExcelUtil.createExcelFromTemplate(reportTemplate);
 	
		Date startDate = null;
		Date endDate = null;
		String headerRange = null;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if(options != null) {
			try {
			startDate = format.parse((String) options.get("dateFrom"));
			} catch(Exception e) {}
			try {
			endDate = format.parse((String) options.get("dateTo"));
			} catch(Exception e) {}
			if(startDate != null) {
				if(endDate == null) {
					endDate = new Date();
				}
				headerRange = "��ǧ�ѹ���͹��ѵ� �ҡ "+format.format(startDate)+" �֧ "+format.format(endDate);
			} else {
				endDate = null;
			}
		}
		List<KK_OL_Callback> cbList = kk_OL_CallbackDao.getAll("", "", startDate, endDate);
		
		int startRow = 3;
		changeExcelReportHeader(workBook, SHEET_ALL, 0, 0, headerRange, false);
		populateExcel_OL_Report(startRow,workBook,SHEET_ALL,cbList);
		startRow = 3;
		cbList = kk_OL_CallbackDao.getAll("", "Y");
		changeExcelReportHeader(workBook, SHEET_SUCCESS, 0, 0, headerRange, false);
		populateExcel_OL_Report(startRow,workBook,SHEET_SUCCESS,cbList);
		startRow = 3;
		cbList = kk_OL_CallbackDao.getAll("", "W");
		changeExcelReportHeader(workBook, SHEET_CALLING, 0, 0, headerRange, false);
		populateExcel_OL_Report(startRow,workBook,SHEET_CALLING,cbList);
		startRow = 3;
		cbList = kk_OL_CallbackDao.getAll("", "R");
		changeExcelReportHeader(workBook, SHEET_REJECT, 0, 0, headerRange, false);
		populateExcel_OL_Report(startRow,workBook,SHEET_REJECT,cbList);
		
		writeFile(response, workBook, "KK_OL_Callback_"+timeStr);
		
		} catch (Exception e) {
			String message = messageResolver.getMessage("system.error",
									new Object[] { e.getMessage() });
			 e.printStackTrace();
		}
		
	}
	
	public void changeExcelReportHeader(HSSFWorkbook workBook, int sheetAt, int row, int col, String text, boolean replace) {
		if(text != null) {			
			HSSFSheet workSheet = workBook.getSheetAt(sheetAt);
	        HSSFRow cellRow = workSheet.getRow(row);
			HSSFCell cellCol = cellRow.getCell(col);
			if(!replace) {
				text = cellCol.getStringCellValue() + " " + text;
			}
			cellCol.setCellValue(text);
		}
	}
	
	public void populateExcelReport(int sourceRowNum,HSSFWorkbook workBook,int sheetAt,List<KK_UL_Callback> list){
		HSSFPalette palette = workBook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index, (byte) 234, (byte) 234,(byte) 234);

		HSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Tahoma");
		font.setFontHeightInPoints((short) 12);
		 
		HashMap<Integer,HSSFCellStyle> styleTable = new HashMap<Integer, HSSFCellStyle>();		
		int destinationRowNum                     = sourceRowNum+1;
		HSSFSheet workSheet                       = workBook.getSheetAt(sheetAt);
		
		//Show Print date format dd mmm yyyy
		HSSFCell headerCell;
		 
		for(int rowIndex = 0; rowIndex < list.size(); rowIndex++){
			
			int running_src = sourceRowNum + rowIndex;
			int running_des = destinationRowNum + rowIndex;
			
	        HSSFRow sourceRow = workSheet.getRow(running_src);
			HSSFRow newRow    = workSheet.getRow(running_des);
			
			if (newRow != null) {
	            workSheet.shiftRows(running_des, workSheet.getLastRowNum(), 1);
	        } else {
	            newRow = workSheet.createRow(running_des);
	        }
			
			for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
				
				HSSFCell oldCell = sourceRow.getCell(i);
	            HSSFCell newCell = newRow.createCell(i);
	            
	            if (oldCell == null) {
	                newCell = null;
	                continue;
	            }
	            
	            if(styleTable.get(i) == null){
	            	HSSFCellStyle newCellStyle = workBook.createCellStyle();
	            	newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
	            	styleTable.put(new Integer(i), newCellStyle);
	            	
	            	newCell.setCellStyle(newCellStyle);
	            	
	            }else{
	            	newCell.setCellStyle(styleTable.get(i));
	            }
//	            else{
////	            	String[] totalCell = list.get(rowIndex);
//	            	KK_UL_Callback cbTmp = list.get(rowIndex);
////	            	if( "Grand Total".equals(totalCell[0]) || "Total".equals(totalCell[0]) ){
//	            	if( String.valueOf(totalCell[0]).indexOf("Total") > -1 ||
//	            	    String.valueOf(totalCell[1]).indexOf("Total") > -1 )
//	            	{
//	            		HSSFCellStyle totalStyle = workBook.createCellStyle();
//	            		totalStyle.cloneStyleFrom(styleTable.get(i));
//		            	totalStyle.setFont(font);
//		            	totalStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//		            	totalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		            	newCell.setCellStyle(totalStyle);
//	            	}else{
//	            		newCell.setCellStyle(styleTable.get(i));
//	            	}
//	            }
	            
	            copyDataToCellDailySalesReport(newCell, list.get(rowIndex), rowIndex, i);
			}
		}
		
		if( list.size() > 0 ){
			workSheet.removeRow(workSheet.getRow(sourceRowNum));
			workSheet.shiftRows(destinationRowNum, workSheet.getLastRowNum(), -1);
		}else{
			//Write no record found.
			noRecordReturn(workSheet,sourceRowNum);
		}
	} 
	
	public void populateExcel_OL_Report(int sourceRowNum,HSSFWorkbook workBook,int sheetAt,List<KK_OL_Callback> list){
		
		HSSFPalette palette = workBook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index, (byte) 234, (byte) 234,(byte) 234);

		HSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Tahoma");
		font.setFontHeightInPoints((short) 12);
		 
		HashMap<Integer,HSSFCellStyle> styleTable = new HashMap<Integer, HSSFCellStyle>();		
		int destinationRowNum                     = sourceRowNum+1;
		HSSFSheet workSheet                       = workBook.getSheetAt(sheetAt);
		
		//Show Print date format dd mmm yyyy
		HSSFCell headerCell;
		 
		for(int rowIndex = 0; rowIndex < list.size(); rowIndex++){
			
			int running_src = sourceRowNum + rowIndex;
			int running_des = destinationRowNum + rowIndex;
			
	        HSSFRow sourceRow = workSheet.getRow(running_src);
			HSSFRow newRow    = workSheet.getRow(running_des);
			
			if (newRow != null) {
	            workSheet.shiftRows(running_des, workSheet.getLastRowNum(), 1);
	        } else {
	            newRow = workSheet.createRow(running_des);
	        }
			
			for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
				
				HSSFCell oldCell = sourceRow.getCell(i);
	            HSSFCell newCell = newRow.createCell(i);
	            
	            if (oldCell == null) {
	                newCell = null;
	                continue;
	            }
	            
	            if(styleTable.get(i) == null){
	            	HSSFCellStyle newCellStyle = workBook.createCellStyle();
	            	newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
	            	styleTable.put(new Integer(i), newCellStyle);
	            	
	            	newCell.setCellStyle(newCellStyle);
	            	
	            }else{
	            	newCell.setCellStyle(styleTable.get(i));
	            } 
	            
	            copy_OL_DataToCellDailySalesReport(newCell, list.get(rowIndex), rowIndex, i);
			}
		}
		
		if( list.size() > 0 ){
			workSheet.removeRow(workSheet.getRow(sourceRowNum));
			workSheet.shiftRows(destinationRowNum, workSheet.getLastRowNum(), -1);
		}else{
			//Write no record found.
			noRecordReturn(workSheet,sourceRowNum);
		}
	} 
	
	public void noRecordReturn(HSSFSheet sheet,int startRow){
		HSSFRow row = sheet.getRow(startRow);
		HSSFCell cell = row.getCell(0);
		cell.setCellValue("No Record found");
		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, row.getLastCellNum()-1));
	}
	
	public String convertConbobox(String val){
		String ret = "";
		if( "1".equals(val) ){
			return "Yes";	
		}else if( "2".equals(val) ){
			return "No";
		}else{
			return "";
		}
	}
	
	public void copyDataToCellDailySalesReport(HSSFCell cell, KK_UL_Callback cb, int rowIndex, int cellIndex){
		
		switch (cellIndex) {
		
			case 0: cell.setCellValue(rowIndex+1); break;
//			case 0: cell.setCellValue(data[0]); break;
			case 1: cell.setCellValue(cb.getCustomerName()); break;
			case 2: cell.setCellValue(cb.getCreatedDate()); break;
			case 3: cell.setCellValue(cb.getStatus()); break;
			case 4: cell.setCellValue(cb.getMobileNo()); break;
			case 5: cell.setCellValue(cb.getAgentCode()); break;
			case 6: cell.setCellValue(cb.getAgentName()); break;
			case 7: cell.setCellValue(cb.getBranchName()); break;
			case 8: cell.setCellValue(cb.getPlanName()); break;
			case 9: cell.setCellValue(cb.getPremium()); break;
			
			case 10: cell.setCellValue(cb.getAppNo()); break;
			case 11: cell.setCellValue(cb.getKkRefNo()); break;
			
			case 12: cell.setCellValue(cb.getFundSelectList()); break;
			case 13: cell.setCellValue(cb.getPercentSelectList()); break;
			case 14: cell.setCellValue(cb.getRiskSelectList()); break;
			case 15: cell.setCellValue(cb.getFrontEndFee()); break;
			case 16: cell.setCellValue(cb.getCoiFee()); break;
			
			case 17: cell.setCellValue(cb.getCallDate()); break;
			case 18: cell.setCellValue(cb.getCallTime()); break;
			case 19: cell.setCellValue(cb.getCallBy()); break;
			case 20: cell.setCellValue(convertConbobox(cb.getQ1())); break;
			case 21: cell.setCellValue(convertConbobox(cb.getQ2())); break;
			case 22: cell.setCellValue(convertConbobox(cb.getQ3())); break;
			case 23: cell.setCellValue(convertConbobox(cb.getQ4())); break;
			case 24: cell.setCellValue(convertConbobox(cb.getQ5())); break;
			case 25: cell.setCellValue(convertConbobox(cb.getQ6())); break;
			case 26: cell.setCellValue(convertConbobox(cb.getQ7())); break;
			case 27: cell.setCellValue(cb.getIsConfirm()); break;
			case 28: cell.setCellValue(cb.getKkNote()); break;
			case 29: cell.setCellValue(cb.getNoteFromBranch()); break;
		}
		
	}
	
	public void copy_OL_DataToCellDailySalesReport(HSSFCell cell, KK_OL_Callback cb, int rowIndex, int cellIndex){
		
		switch (cellIndex) {
		
			case 0: cell.setCellValue(rowIndex+1); break;
//			case 0: cell.setCellValue(data[0]); break;
			case 1: cell.setCellValue(cb.getCustomerName()); break;
			case 2: cell.setCellValue(cb.getCreatedDate()); break;
			case 3: cell.setCellValue(cb.getStatus()); break;
			case 4: cell.setCellValue(cb.getMobileNo()); break;
			case 5: cell.setCellValue(cb.getAgentCode()); break;
			case 6: cell.setCellValue(cb.getAgentName()); break;
			case 7: cell.setCellValue(cb.getBranchName()); break;
			case 8: cell.setCellValue(cb.getPlanName()); break;
			case 9: cell.setCellValue(cb.getPremium()); break;
			
			case 10: cell.setCellValue(cb.getAppNo()); break;
			case 11: cell.setCellValue(cb.getKkRefNo()); break;
			
			case 12: cell.setCellValue(cb.getFirstReviewTimestamp()); break;
			 
			case 13: cell.setCellValue(cb.getCallDate()); break;
			case 14: cell.setCellValue(cb.getCallTime()); break;
			case 15: cell.setCellValue(cb.getCallBy()); break;
			case 16: cell.setCellValue(convertConbobox(cb.getQ1())); break;
			case 17: cell.setCellValue(convertConbobox(cb.getQ2())); break;
			case 18: cell.setCellValue(convertConbobox(cb.getQ3())); break;
			case 19: cell.setCellValue(convertConbobox(cb.getQ4())); break;
			case 20: cell.setCellValue(convertConbobox(cb.getQ5())); break;
			case 21: cell.setCellValue(convertConbobox(cb.getQ6())); break;
			case 22: cell.setCellValue(cb.getIsConfirm()); break;
			case 23: cell.setCellValue(cb.getKkNote()); break;
			case 24: cell.setCellValue(cb.getNoteFromBranch()); break;
		}
		
	}
}


