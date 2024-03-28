/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.Date;
import java.util.List;

import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_UL_Callback;
import com.genth.kkdc.domain.KK_UL_Fund_Selection;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
public interface KK_UL_CallbackDao {
	
	public KK_UL_Callback get(Integer id);
	public List<KK_UL_Callback> getAll(String customerName,String searchStatus) 			throws CommonException;
	public List<KK_UL_Callback> getAll(String customerName,String searchStatus, Date start, Date end) 			throws CommonException;
	public String getUlFunSelection(String sisno) 			throws CommonException;
	public void editStatus(KK_UL_Callback cb, String status) 	    throws CommonException;	
	 
}
