package com.genth.kkdc.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtil {

	private static Logger log = Logger.getLogger(ExcelUtil.class.getName());
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static HSSFWorkbook createExcelFromTemplate(String template){
		try {
			
			InputStream inputStream = new FileInputStream(template);
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);
			
			return new HSSFWorkbook (fileSystem);
		} catch (Exception e) {
		
			log.error("**********************!!! ERROR ExcelUtil.createExcelFromTemplate !!!**********************");
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			log.error(stack.toString());
			log.error("**********************!!! ERROR ExcelUtil.createExcelFromTemplate !!!**********************");
			
		}
		return null;
	}

	public static void saveExcel(HSSFWorkbook excel, String fileName){
		try {
			FileOutputStream fileOut1 = new FileOutputStream(fileName);
			excel.write(fileOut1);
			fileOut1.close();
		} catch (Exception e) {
		
			log.debug("**********************!!! ERROR LetterExcelReportXLSService.saveExcel !!!**********************");
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			log.debug(stack.toString());
			log.debug("**********************!!! ERROR LetterExcelReportXLSService.saveExcel !!!**********************");
			
		}
	}
	
}
