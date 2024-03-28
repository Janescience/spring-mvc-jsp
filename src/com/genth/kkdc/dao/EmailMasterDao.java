/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.List;

import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
public interface EmailMasterDao {
	
	public EmailMaster get(Integer id);
	public List<EmailMaster> getAll()           throws CommonException;	
	public void add(EmailMaster email)		       throws CommonException;	
	public void edit(EmailMaster email) 	       throws CommonException;	
	public void delete(Integer id) 	 		       throws CommonException;		
	public EmailMaster getEmail(String agentNumber)  throws CommonException;	
	//public List<String> getImagePath() 	 		throws CommonException;		
	
}
