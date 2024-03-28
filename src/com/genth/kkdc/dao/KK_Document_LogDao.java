/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.List;

import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.domain.KK_Document_Log_ForKKGENBIZ;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
public interface KK_Document_LogDao {
	
	public KK_Document_Log get(Integer id);
	public List<KK_Document_Log> getAll() 			throws CommonException;	
	public List<KK_Document_Log> getByDocId(String docId) throws CommonException;	
	public List<KK_Document_Log> getByAppNo(String appNo) throws CommonException;
	public void add(KK_Document_Log doc)		throws CommonException;	
	public void edit(KK_Document_Log doc, String mode) 	    throws CommonException;	
	public void delete(Integer id) 	 		    throws CommonException;		
	
	public KK_Document_Log_ForKKGENBIZ getKKGenBiz(Integer id);
	public void editKKGenBiz(KK_Document_Log doc, String mode) 	    throws CommonException;	
	public int insertKKDocumentLog(Integer id,String appNo) 			throws Exception;	
}
