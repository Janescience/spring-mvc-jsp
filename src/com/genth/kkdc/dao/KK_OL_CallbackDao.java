/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.Date;
import java.util.List;

import com.genth.kkdc.domain.KK_OL_Callback;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
public interface KK_OL_CallbackDao {
	
	public KK_OL_Callback get(Integer id);
	public List<KK_OL_Callback> getAll(String customerName,String searchStatus) 			throws CommonException;
	public List<KK_OL_Callback> getAll(String customerName,String searchStatus, Date start, Date end) 			throws CommonException;
	public String getUlFunSelection(String sisno) 			throws CommonException;
	public void editStatus(KK_OL_Callback cb, String status) 	    throws CommonException;	
	 
}
