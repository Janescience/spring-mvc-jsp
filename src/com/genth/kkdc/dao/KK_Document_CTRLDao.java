/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.List;

import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_CTRL;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
public interface KK_Document_CTRLDao {
	
	public KK_Document_CTRL get(Integer id);
	public List<KK_Document_CTRL> getAll() 			throws CommonException;	
//	public List<KK_Document_CTRL> getDocCheckList(String documentId)	throws CommonException;	
//	public void add(KK_Document_CTRLDao doc)		throws CommonException;	
//	public void edit(KK_Document_CTRLDao doc) 	    throws CommonException;	
//	public void delete(Integer id) 	 		    throws CommonException;		
	
}
