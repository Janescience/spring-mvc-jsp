package com.genth.kkdc.service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.domain.BillpaymentNotice;
import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.RejectByAppno;
public class SendEmailService implements Runnable {
	
	private static final String GEN_FILE = "GEN_FILE";
	private static final String UPDATE_FILE = "UPDATE_FILE";
	private static final String NOTIFICATION = "NOTIFICATION";
	
	private static final String REJECTED 			= "Rejected";
	private static final String REJECTED_COF 		= "Rejected-COF";
	private static final String REJECTED_SA_PREM 	= "Rejected-SA_Prem";

	private static final String COMPLETED = "Completed";
	
	private Logger logger = Logger.getLogger("SendEmailService");

	private KK_Document doc;
	private BillpaymentNotice bill;
	private String typeOfReject;
	
	private StringBuilder messageSend;
	
	@Resource(name = "messageService")
	private MessageResolver messageResolver;
	
	public SendEmailService() {

	}
	public SendEmailService(KK_Document doc) {
		this.doc = doc;
	}
	public SendEmailService(KK_Document doc, String typeOfReject) {
		this.doc = doc;
		this.typeOfReject = typeOfReject;
	}
	public SendEmailService(StringBuilder messageSend) {
		setMessageSend(messageSend);
	}  

	public static void sendMailBillpaymentNotify(BillpaymentNotice bill) throws Exception{
		String host = ResourceConfig.getCommonProperty("HOST");

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		
		Session session = Session.getDefaultInstance(properties);
		
		MimeMessage message = new MimeMessage(session);
		
		try {
			
			 			
			message.setFrom(new InternetAddress(ResourceConfig.getCommonProperty("EMAIL_FROM")));
			
			String[] mailToList = bill.getStaffEmail().split(",");
//			String[] mailToList = "chalermpona@generali.co.th".split(",");
			String[] mailCcList = "".split(",");
			String[] mailBccList = "".split(",");
			
			List<String> toList  = new ArrayList<String>();
			List<String> ccList  = new ArrayList<String>();
			List<String> bccList = new ArrayList<String>();
			
			for(String to : mailToList){
				toList.add(to.trim());
			}
			for(String cc : mailCcList){
				if(cc.trim().length() > 0){
					ccList.add(cc.trim());
				}
			}
			for(String bcc : mailBccList){
				if(bcc.trim().length() > 0){
					bccList.add(bcc.trim());
				}
			}
			
			Address[] to  = convertToMail(toList);
			Address[] cc  = convertToMail(ccList);
			Address[] bcc = convertToMail(bccList);
			
			message.addRecipients(Message.RecipientType.TO, to);
			 
			if(cc.length > 0){
				message.addRecipients(Message.RecipientType.CC, cc);
			}
			  
			if(bcc.length > 0){
				message.addRecipients(Message.RecipientType.BCC,bcc);
			}
			
			
			// Set the "Subject" header field.
			String subject = bill.getSubject();
			message.setSubject(subject,"UTF-8");
			
			StringBuilder str = getBillNotifyMessageToSend(bill.getBody());
				
			MimeBodyPart mbp = new MimeBodyPart();
			
			mbp.setContent(str.toString(), "text/html; charset=UTF-8");
			
			Multipart multipart = new MimeMultipart();
			
			multipart.addBodyPart(mbp);
			
			mbp = new MimeBodyPart();
			 
			// Put parts in message
			message.setContent(multipart);
			 
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		System.out.println("Message Send.....");
	}
	
public void sendRejectMail(KK_Document doc) throws Exception{
		
		String host = ResourceConfig.getCommonProperty("HOST");

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		
		MimeMessage message = new MimeMessage(session);
		
		try {
			String cc_for_reject_complete = ResourceConfig.getCommonProperty("cc_for_reject_complete");
			if( cc_for_reject_complete == null ){
				cc_for_reject_complete = "";
			}
			
			message.setFrom(new InternetAddress(ResourceConfig.getCommonProperty("EMAIL_FROM")));
			
			String[] mailToList = doc.getStaffEmail().split(",");
//			String[] mailToList = "chalermpona@generali.co.th".split(",");
			String[] mailCcList = cc_for_reject_complete.split(",");
			String[] mailBccList = "".split(",");
			
			List<String> toList  = new ArrayList<String>();
			List<String> ccList  = new ArrayList<String>();
			List<String> bccList = new ArrayList<String>();
			
			for(String to : mailToList){
				toList.add(to.trim());
			}
			for(String cc : mailCcList){
				if(cc.trim().length() > 0){
					ccList.add(cc.trim());
				}
			}
			for(String bcc : mailBccList){
				if(bcc.trim().length() > 0){
					bccList.add(bcc.trim());
				}
			}
			
			Address[] to  = convertToMail(toList);
			Address[] cc  = convertToMail(ccList);
			Address[] bcc = convertToMail(bccList);
			
			message.addRecipients(Message.RecipientType.TO, to);
			
			if(cc.length > 0){
				message.addRecipients(Message.RecipientType.CC, cc);
			}
			 
			if(bcc.length > 0){
				message.addRecipients(Message.RecipientType.BCC,bcc);
			}
			
			
			// Set the "Subject" header field.
			String subject = doc.getSubject();
			message.setSubject(subject,"UTF-8");
			
			StringBuilder str = getRejectMessageToSend(doc.getBody(),typeOfReject);
				
			MimeBodyPart mbp = new MimeBodyPart();
			
			mbp.setContent(str.toString(), "text/html; charset=UTF-8");
			
			Multipart multipart = new MimeMultipart();
			
			multipart.addBodyPart(mbp);
			
			mbp = new MimeBodyPart();
			
			// Put parts in message
			message.setContent(multipart);
			
			// Send message
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		System.out.println("Message Send.....");
	}

	public void sendMail(KK_Document doc) throws Exception{
		
		String host = ResourceConfig.getCommonProperty("HOST");

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
//		properties.setProperty("mail.debug", "true");
//		properties.setProperty("java.net.preferIPv4Stack" , "true");
//		properties.setProperty("java.net.preferIPv4Addresses" , "true");
//		System.setProperty("java.net.preferIPv4Stack" , "true");
//		System.setProperty("java.net.preferIPv4Addresses" , "true");
		Session session = Session.getDefaultInstance(properties);
		
		MimeMessage message = new MimeMessage(session);
		
		try {
			
			 			
			message.setFrom(new InternetAddress(ResourceConfig.getCommonProperty("EMAIL_FROM")));
			
			String[] mailToList = doc.getStaffEmail().split(",");
//			String[] mailToList = "chalermpona@generali.co.th".split(",");
			String[] mailCcList = "".split(",");
			String[] mailBccList = "".split(",");
			
			List<String> toList  = new ArrayList<String>();
			List<String> ccList  = new ArrayList<String>();
			List<String> bccList = new ArrayList<String>();
			
			for(String to : mailToList){
				toList.add(to.trim());
			}
			for(String cc : mailCcList){
				if(cc.trim().length() > 0){
					ccList.add(cc.trim());
				}
			}
			for(String bcc : mailBccList){
				if(bcc.trim().length() > 0){
					bccList.add(bcc.trim());
				}
			}
			
			Address[] to  = convertToMail(toList);
			Address[] cc  = convertToMail(ccList);
			Address[] bcc = convertToMail(bccList);
			
			message.addRecipients(Message.RecipientType.TO, to);
			
//			CC part
//			Address[] cc = new Address[] {new InternetAddress("thawatchaij@generali.co.th"),
//								  new InternetAddress("chalermpona@generali.co.th"),
//						  		  new InternetAddress("watcharapongs@generali.co.th"),
//						  		  new InternetAddress("praveenv@generali.co.th"),
//						  		 // new InternetAddress("kongkrita@generali.co.th"),
//						  		  new InternetAddress("sumatem@generali.co.th")};
			 
			if(cc.length > 0){
				message.addRecipients(Message.RecipientType.CC, cc);
			}
			 
			
//			BCC part
//			Address[] bcc = new Address[] {new InternetAddress("thawatchaij@generali.co.th"),
//								   new InternetAddress("chalermpona@generali.co.th"),
//						  		   new InternetAddress("watcharapongs@generali.co.th"),
//						  		   new InternetAddress("praveenv@generali.co.th"),
//						  		   new InternetAddress("kongkrita@generali.co.th"),
//						  		   new InternetAddress("sumatem@generali.co.th")};
			
			if(bcc.length > 0){
				message.addRecipients(Message.RecipientType.BCC,bcc);
			}
			
			
			// Set the "Subject" header field.
			String subject = doc.getSubject();
			message.setSubject(subject,"UTF-8");
			
			StringBuilder str = getMessageToSend(doc.getBody());
				
			MimeBodyPart mbp = new MimeBodyPart();
			
			mbp.setContent(str.toString(), "text/html; charset=UTF-8");
			
			Multipart multipart = new MimeMultipart();
			
			multipart.addBodyPart(mbp);
			
			mbp = new MimeBodyPart();
			
			//String filename = "CreateZipFileWithOutputStreams.zip";
//			if(attachFileName != null && attachFileName.length > 0){
//				
//				for(int i=0; i<attachFileName.length; i++){
//					String displayNm = displayFileName[i];					
//					if(displayNm == null) continue;
//					
//					if( !"".equals(attachFileName[i])){
//						addAttachment(multipart, attachFileName[i],displayFileName[i]);
//					}
//				}
//			}
			
//			if(! "".equals(pdfFileName)){
//				
//				DataSource source = new FileDataSource(pdfFileName);
//				mbp.setDataHandler(new DataHandler(source));
//				mbp.setFileName(displayFileName);
//				multipart.addBodyPart(mbp);
//			}
			
			// Put parts in message
			message.setContent(multipart);
			
//			System.out.println("preferIPv4Stack="+java.lang.System.getProperty("java.net.preferIPv4Stack"));
//			System.out.println("preferIPv4Addresses="+java.lang.System.getProperty("java.net.preferIPv4Addresses"));
//			System.out.println("preferIPv6Stack="+java.lang.System.getProperty("java.net.preferIPv6Stack"));
//			System.out.println("preferIPv6Addresses="+java.lang.System.getProperty("java.net.preferIPv6Addresses"));
			
			
//			RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
//			List<String> arguments = runtimeMxBean.getInputArguments();
			
			// Send message
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		System.out.println("Message Send.....");
	}
	
	public static Address[] convertToMail(List<String> list){
		Address[] arr = new Address[list.size()];
		int index = 0;
		try {
			for(int i=0; i<list.size(); i++){
				arr[index++] = new InternetAddress(list.get(i));
			}
		} catch (AddressException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public StringBuilder getRejectMessageToSend(String body,String kkDocStatus){

		StringBuilder str = new StringBuilder();
		
		kkDocStatus = kkDocStatus.replaceAll("-", "_");
		
		str.append(body);
		str.append(ResourceConfig.getCommonProperty(kkDocStatus+"_1"));
		str.append(ResourceConfig.getCommonProperty(kkDocStatus+"_2"));
		

		str.append("<BR><BR>" + ResourceConfig.getCommonProperty("EMAIL04")+"<br>");
		//str.append(ResourceConfig.getCommonProperty("EMAIL06"));
		
		str.append(ResourceConfig.getCommonProperty(kkDocStatus+"_3"));
		str.append(ResourceConfig.getCommonProperty(kkDocStatus+"_4"));
		
		return str;
	}
	
	public StringBuilder getMessageToSend(String body){

		StringBuilder str = new StringBuilder();
		
		str.append(ResourceConfig.getCommonProperty("EMAIL01"));
		str.append(ResourceConfig.getCommonProperty("EMAIL02"));
		// List of missing document here
		str.append(body);
		str.append(ResourceConfig.getCommonProperty("EMAIL03"));
		str.append(ResourceConfig.getCommonProperty("EMAIL04"));
		str.append(ResourceConfig.getCommonProperty("EMAIL05"));
		str.append(ResourceConfig.getCommonProperty("EMAIL06"));
		
		return str;
	}
	 
	public static StringBuilder getBillNotifyMessageToSend(String body){

		StringBuilder str = new StringBuilder();
		
		//str.append(ResourceConfig.getCommonProperty("Noti1"));
		// List of missing document here
		str.append(body);
		str.append(ResourceConfig.getCommonProperty("Noti2"));
		str.append(ResourceConfig.getCommonProperty("Noti3"));
		str.append(ResourceConfig.getCommonProperty("Noti4"));
		str.append(ResourceConfig.getCommonProperty("Noti5"));
		str.append(ResourceConfig.getCommonProperty("Noti6"));
		str.append(ResourceConfig.getCommonProperty("Noti7"));
		str.append(ResourceConfig.getCommonProperty("Noti8"));
		str.append(ResourceConfig.getCommonProperty("Noti9"));
		
		return str;
	}
	
	public StringBuilder getMessageERROR(){
		return messageSend;
	}
	
		
	public String format(long c){
		NumberFormat f = NumberFormat.getCurrencyInstance();
		return f.format(Double.valueOf(c)).replace("$", "");
		
	}
	
//	public EmailMaster getEmailList(){
//		EmailMaster mail = new EmailMaster();
//		 
//		try {
//			EmailMasterDaoImpl dao = new EmailMasterDaoImpl();
//			mail = dao.getEmail();
//		} catch (CommonException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return mail;
//	}
	
//	private static void addAttachment(Multipart multipart, String filename, String displayFileName){
//		
//		String targetFolder = ResourceConfig.getCommonProperty("TAGET_FOLDER"); 
//		
//		DataSource source = new FileDataSource(filename);
//	    
//	    MimeBodyPart messageBodyPart = new MimeBodyPart();        
//	     
//	    try {
//	    	
//			messageBodyPart.setDataHandler(new DataHandler(source)); 
//			messageBodyPart.setFileName(displayFileName);
//			multipart.addBodyPart(messageBodyPart);
//			
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	 
	public StringBuilder getMessageSend() {
		return messageSend;
	}
	public void setMessageSend(StringBuilder messageSend) {
		this.messageSend = messageSend;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("doc.getStatus ==>"+doc.getStatus() + ", typeOfReject ==>"+typeOfReject);
			if( REJECTED.equals(typeOfReject) ||
				REJECTED_COF.equals(typeOfReject) || 
				REJECTED_SA_PREM.equals(typeOfReject) || 
				COMPLETED.equals(typeOfReject)){
				
				sendRejectMail(doc);
			}else{
				sendMail(doc);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
