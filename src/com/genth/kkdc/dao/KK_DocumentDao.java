/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.List;

import com.genth.kkdc.domain.BillpaymentNotice;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_ForKKGENBIZ;
import com.genth.kkdc.domain.RejectByAppno;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
public interface KK_DocumentDao {
	
	public KK_Document get(Integer id);
	public List<KK_Document> getAll() 			throws CommonException;
	public List<KK_Document> getByKKDocStatus(String status) 			throws CommonException;
	public List<KK_Document> getByAppNo(String appNo) 			throws CommonException;
	public List<BillpaymentNotice> getBillpaymentNotice(String appNo) 			throws CommonException;	
	public List<RejectByAppno> getRejectByAppno(String appNo,String searchStatus) 			throws CommonException;	
	public List<String[]> getAllDocument(String appNo) 			throws CommonException;	
	public void add(KK_Document doc)		throws CommonException;	
	public void edit(KK_Document doc,String mode) 	    throws CommonException;	
	public void completeDocument(KK_Document doc) 	    throws CommonException;	
	public void delete(Integer id) 	 		    throws CommonException;		
	public void popupEditWebServiceData(String appno) 		throws CommonException;
	public int updatePolicyNumber(String appNo,String policyNo) 			throws Exception;	
	
	public KK_Document_ForKKGENBIZ getKKGenBiz(Integer id);
	public List<KK_Document> getKKGenBizDocByAppNo(String appNo) 			throws CommonException;	
	public void editKKGenBiz(KK_Document doc,String mode) 	    throws CommonException;	
	public void completeKKGENBIZDocument(KK_Document doc) 	    throws CommonException;	
}
